package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 临时访问地址
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFileTemporaryUrlVo implements IGHBResponseBody {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private String returnCode = GHBResultCode.SUCCESS_REQ_CODE;

    /**
     * 返回信息
     */
    private String returnMessage = "成功";

    /**
     * 临时访问地址，10分钟有效
     */
    private String filePath;

    public GetFileTemporaryUrlVo(String filePath) {
        super();
        this.filePath = filePath;
    }

}
