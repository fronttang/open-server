package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件下载返回
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileVo implements IGHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 文件 OSS 地址
     */
    private String url;
}
