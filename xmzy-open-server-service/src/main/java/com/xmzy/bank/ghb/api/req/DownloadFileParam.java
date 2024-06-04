package com.xmzy.bank.ghb.api.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.DownloadFileApi;
import com.xmzy.bank.ghb.api.resp.DownloadFileVo;
import com.xmzy.model.base.enums.FileType;

import lombok.Data;

/**
 * @Description:文件下载请求报文
 */
@Data
@ApiFactory(DownloadFileApi.class)
public class DownloadFileParam implements IGHBRequestBody, IExecutableApiRequest<DownloadFileVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件token
     */
    private String token;

    @JsonIgnore
    @JSONField(serialize = false)
    private FileType fileType;
}
