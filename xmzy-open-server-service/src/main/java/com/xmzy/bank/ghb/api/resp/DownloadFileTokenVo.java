package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.GHBResponseBody;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description:文件下载token申请应答报文
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DownloadFileTokenVo extends GHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 业务流水号
     */
    private String bizNo;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件日期
     */
    private String fileDate;

    /**
     * 交易状态 1、文件不存在 2、文件处理中 3、文件下载成功
     */
    private String transactionStatus;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件token
     */
    private String token;
}
