package com.xmzy.bank.ghb;

import cn.hutool.core.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 银行header公共字段
 * 
 * @author fronttang
 * @date 2021/08/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GHBHeader {

    /**
     * 应用编号，由合作方申请，银行生成。
     */
    private String appId;

    /**
     * 请求流水号，由合作方生成，每次唯一。生成规则：yyyyMMddHHmmssSSS+6位随机数，如20181111121230788912891。
     */
    private String requestId;

    /**
     * 字符集：目前支持UTF-8
     */
    private String charset = CharsetUtil.UTF_8;

    /**
     * 签名数据，签名后的报文体
     */
    private String signData;

    /**
     * 摘要数据，明文数据body的SM3摘要
     */
    private String reserve;

}
