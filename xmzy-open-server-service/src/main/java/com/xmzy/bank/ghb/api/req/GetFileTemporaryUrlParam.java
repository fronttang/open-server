package com.xmzy.bank.ghb.api.req;

import com.xmzy.bank.ghb.IGHBRequestBody;

import lombok.Data;

/**
 * 获取文件临时访问地址
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
public class GetFileTemporaryUrlParam implements IGHBRequestBody {

    private static final long serialVersionUID = 1L;

    /**
     * 文件全路径
     */
    private String filePath;
}
