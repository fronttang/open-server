package com.xmzy.bank.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Objects;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.ghb.api.DownloadFileApi;
import com.xmzy.bank.ghb.api.GetDownloadFileTokenApi;
import com.xmzy.bank.ghb.api.GetReconciliationFileTokenApi;
import com.xmzy.bank.ghb.api.resp.DownloadFileTokenVo;
import com.xmzy.bank.ghb.api.resp.DownloadFileVo;
import com.xmzy.bank.ghb.api.resp.GetReconciliationFileTokenVo;
import com.xmzy.bank.ghb.enums.GHBReconciliationFileType;
import com.xmzy.bank.ghb.util.GHBSMUtils;
import com.xmzy.bank.ghb.util.SecretKeyHolder;
import com.xmzy.base.Result;
import com.xmzy.model.base.enums.FileType;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试用
 * 
 * @author fronttang
 * @date 2021/08/31
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class BankTestController {

    @GetMapping("/file")
    public void file(HttpServletRequest request, HttpServletResponse response) {

        ServletOutputStream out = null;
        try {
            File file = new File("C:/Users/tangf/Downloads/GHB_0000001_SERVICE_20210915.txt");

            File encryptFile = new File("C:/Users/tangf/Downloads/encryptFile.txt");

            GHBSMUtils.encryptUseSM4(new FileInputStream(file), new FileOutputStream(encryptFile),
                SecretKeyHolder.getSecretKey());

            String fileSm3 = SmUtil.sm3(encryptFile);

            String sign = GHBSMUtils.sign(SmUtil.sm3(fileSm3).toUpperCase(), GHBConstants.BANK_PRIVATE_KEY);

            response.addHeader("errorCode", GHBResultCode.SUCCESS);
            response.addHeader("errorMsg", "success");
            response.addHeader("digest", Base64.encode(sign));

            String fileName =
                URLEncoder.encode("GHB_0000001_SERVICE_20210915.txt", CharsetUtil.UTF_8).replaceAll("\\+", "%20");
            final String attachmentHeader = "Attachment; Filename*=utf-8''" + fileName;

            response.setHeader("Content-Disposition", attachmentHeader);
            response.setContentType("application/octet-stream;charset=UTF-8");

            out = response.getOutputStream();
            IoUtil.copy(new FileInputStream(encryptFile), out);

        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (Objects.nonNull(out)) {
                IoUtil.close(out);
            }
        }
    }

    @GetMapping("/download/file")
    public Result<DownloadFileVo> downloadFile() {

        DownloadFileTokenVo tokenVo = GetDownloadFileTokenApi.getToken(FileType.COLLECTION, DateUtil.parse("20210917"),
            "20210904TESA00000000000000000010");

        DownloadFileVo vo = DownloadFileApi.download(FileType.COLLECTION, tokenVo.getFileName(), tokenVo.getToken());
        return Result.SUCCESS(vo);
    }

    @GetMapping("/download/file/token")
    public Result<DownloadFileTokenVo> getDownloadFileToken() {
        DownloadFileTokenVo tokenVo = GetDownloadFileTokenApi.getToken(FileType.COLLECTION, DateUtil.parse("20210917"),
            "20210904TESA00000000000000000010");
        return Result.SUCCESS(tokenVo);
    }

    @GetMapping("/reconciliation/file/token")
    public Result<GetReconciliationFileTokenVo> getReconciliationFileToken() {
        GetReconciliationFileTokenVo tokenVo =
            GetReconciliationFileTokenApi.getToken(GHBReconciliationFileType.REG_CUST, DateUtil.date());
        return Result.SUCCESS(tokenVo);
    }
}
