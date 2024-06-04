package com.xmzy.bank.ghb.api;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.enums.GHBResultEnum;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.GHBDownloadResponseHeader;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBRequestHeader;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.api.req.DownloadFileParam;
import com.xmzy.bank.ghb.api.resp.DownloadFileVo;
import com.xmzy.bank.ghb.event.GHBResponseEvent;
import com.xmzy.bank.ghb.util.GHBSMUtils;
import com.xmzy.bank.ghb.util.SecretKeyHolder;
import com.xmzy.base.Result;
import com.xmzy.base.constant.SymbolConstant;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.model.base.enums.FileType;
import com.xmzy.server.base.util.RestTemplateUtils;
import com.xmzy.util.api.IAliyunOSSApi;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件下载
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Slf4j
@GHBApi(path = GHBApiConstants.DOWNLOAD_FILE_PATH, name = "文件下载")
public class DownloadFileApi extends AbstractGHBApi<DownloadFileParam, DownloadFileVo> {

    @Autowired
    private IAliyunOSSApi ossService;

    private FileItemFactory fileItemFactory;

    @Override
    public DownloadFileParam beforeExecute(DownloadFileParam request) throws BusinessException {
        if (StrUtil.isBlank(request.getMerchantId())) {
            request.setMerchantId(properties.getParentMerchantId());
        }
        return super.beforeExecute(request);
    }

    @Override
    public DownloadFileVo sendRequest(DownloadFileParam param) {

        GHBRequest request = getGHBRequest(param);
        String body = JSON.toJSONString(request);

        return RestTemplateUtils.execute(getRequestUri(param), method, getRequestHeader(), body,
            new DownloadCallback(param, request));
    }

    /**
     * 下载回调处理
     * 
     * @author fronttang
     * @date 2021/10/11
     */
    private class DownloadCallback implements Function<ClientHttpResponse, DownloadFileVo> {

        private DownloadFileParam param;

        private GHBRequest request;

        public DownloadCallback(DownloadFileParam param, GHBRequest request) {
            this.param = param;
            this.request = request;
        }

        @Override
        public DownloadFileVo apply(ClientHttpResponse response) {

            FileItem sourceFileItem = null;
            FileItem decryptFileItem = null;

            try {
                HttpHeaders headers = response.getHeaders();
                GHBDownloadResponseHeader header =
                    BeanUtil.toBean(headers.toSingleValueMap(), GHBDownloadResponseHeader.class);

                if (Objects.isNull(header)) {
                    throw new BusinessException("文件下载失败");
                }

                header.setErrorMsg(URLDecoder.decode(header.getErrorMsg(), CharsetUtil.UTF_8));

                log.info("--->>响应头为:{}", JSON.toJSONString(header));

                // 发布事件用于记录日志
                publishEvent(header, request);

                if (!header.isSuccess()) {
                    throw new BusinessException(header);
                }

                // 创建源fileItem
                sourceFileItem = createFileItem(param.getFileName());

                IoUtil.copy(response.getBody(), sourceFileItem.getOutputStream());

                String originData = SmUtil.sm3(sourceFileItem.getInputStream());

                // 文件验签
                if (!verify(originData, header.getDigest())) {
                    throw new BusinessException(GHBResultEnum.SIGN_DATA_ERROR);
                }

                // 创建解密文件fileItem
                decryptFileItem = createFileItem(param.getFileName());

                String secretKey = SecretKeyHolder.getSecretKey();
                if (StrUtil.isBlank(secretKey)) {
                    throw new BusinessException(GHBResultEnum.SECRET_KEY_ERROR);
                }

                try {
                    // 解密文件
                    GHBSMUtils.decryptUseSM4(sourceFileItem.getInputStream(), decryptFileItem.getOutputStream(),
                        secretKey);
                } catch (Exception e) {
                    log.error("", e);
                    throw new BusinessException(GHBResultEnum.DECRYPT_ERROR);
                }

                // 上传文件到OSS
                String fileUrl = uploadFile(decryptFileItem, param.getFileType());

                return new DownloadFileVo(fileUrl);

            } catch (GHBBusinessException e) {
                throw e;
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            } finally {

                if (Objects.nonNull(sourceFileItem)) {
                    sourceFileItem.delete();
                }
                if (Objects.nonNull(decryptFileItem)) {
                    decryptFileItem.delete();
                }
            }
        }

        private void publishEvent(GHBDownloadResponseHeader header, GHBRequest request) {

            GHBResponse response = new GHBResponse();
            response.setBody(JSON.toJSONString(header));

            GHBRequestHeader requestHeader = request.getHeader();

            LocalDateTime now = LocalDateTime.now();
            String responseTime = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);

            header.setRequestId(requestHeader.getRequestId());
            header.setResponseTime(responseTime);

            response.setHeader(header);

            publisher.publishEvent(new GHBResponseEvent(response));
        }
    }

    /**
     * 创建fileItem
     * 
     * @param fileName
     * @return
     */
    private FileItem createFileItem(String fileName) {
        return fileItemFactory.createItem("file", MediaType.APPLICATION_OCTET_STREAM_VALUE, false, fileName);
    }

    /**
     * 上传文件到OSS
     * 
     * @param decryptFileItem
     * @return
     * @throws IOException
     */
    private String uploadFile(FileItem decryptFileItem, FileType fileType) throws IOException {

        // String fileMd5 = SecureUtil.md5(decryptFileItem.getInputStream());

        String fileName = decryptFileItem.getName();

        // String fileSuffix = SymbolConstant.PERIOD + FileUtil.getSuffix(fileName);

        // 保存解密文件
        LocalDate localDate = LocalDate.now();
        StringBuilder sb = new StringBuilder();

        // String decryptFileName = sb.append(GHBConstants.BANK_ID).append(SymbolConstant.LEFT_DIVIDE)
        // .append(localDate.getYear()).append(SymbolConstant.LEFT_DIVIDE).append(localDate.getMonth())
        // .append(SymbolConstant.LEFT_DIVIDE).append(fileMd5).append(fileSuffix).toString();

        String decryptFileName = sb.append("batch").append(SymbolConstant.LEFT_DIVIDE).append("return")
            .append(SymbolConstant.LEFT_DIVIDE).append(fileType.name()).append(SymbolConstant.LEFT_DIVIDE)
            .append(localDate.getYear()).append(SymbolConstant.LEFT_DIVIDE).append(localDate.getMonth())
            .append(SymbolConstant.LEFT_DIVIDE).append(fileName).toString();

        MultipartFile decryptMultipartFile = new CommonsMultipartFile(decryptFileItem);

        Result<String> result =
            ossService.uploadFileName(properties.getBucket(), decryptFileName, decryptMultipartFile);
        if (result == null || !result.isSuccess() || StrUtil.isBlank(result.getData())) {
            throw new BusinessException("文件上传OSS失败");
        }
        return result.getData();
    }

    /**
     * 验证签名
     * 
     * @param signStr
     * @param signDataBase64
     * @param publicKey
     * @return
     */
    private boolean verify(String signStr, String signDataBase64) {
        // 签名数据摘要
        String digestSignStr = SmUtil.sm3(signStr).toUpperCase();

        // 签名数据base64解码
        String signData = Base64.decodeStr(signDataBase64);
        String publicKeyHex = this.publicKey;
        boolean verify = GHBSMUtils.verify(digestSignStr, signData, publicKeyHex);
        return verify;
    }

    @Override
    protected Map<String, String> getRequestHeader() {
        Map<String, String> requestHeader = super.getRequestHeader();
        requestHeader.put(HttpHeaders.ACCEPT,
            MediaType.toString(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL)));
        return requestHeader;
    }

    @Override
    protected void init() {
        super.init();
        this.fileItemFactory = new DiskFileItemFactory(10240, new File("tmp/ghb/returnfile/"));
        // 测试用
        // this.path = "/test/file";
        // this.host = "http://127.0.0.1:8091";
        // this.method = HttpMethod.GET;
        // this.publicKey = GHBConstants.BANK_PUBLIC_KEY;
    }

    public static DownloadFileVo download(FileType fileType, String fileName, String token) {

        DownloadFileParam param = new DownloadFileParam();
        param.setFileName(fileName);
        param.setToken(token);
        param.setFileType(fileType);

        return param.execute();
    }
}
