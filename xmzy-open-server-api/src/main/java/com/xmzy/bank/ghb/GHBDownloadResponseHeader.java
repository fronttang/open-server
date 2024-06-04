package com.xmzy.bank.ghb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 文件下载返回头
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GHBDownloadResponseHeader extends GHBResponseHeader {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 文件摘要，用于检验文件的完整性，防止文件被篡改
     */
    private String digest;

}
