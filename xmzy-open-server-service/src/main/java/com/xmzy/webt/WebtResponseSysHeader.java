package com.xmzy.webt;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.base.IResult;
import com.xmzy.base.constant.ResultCodeConstants;
import com.xmzy.webt.api.WebtApiConstants;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * webt接口返回 sysheader
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WebtResponseSysHeader extends WebtSysHeader implements IResult {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String retInf;

    private String retCd;

    /**
     * 是否成功
     * 
     * @return
     */
    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return WebtApiConstants.SUCCESS_CODE.equals(getRetCd());
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public Integer getCode() {
        return ResultCodeConstants.BUSINESS_ERROR_CODE;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getMsg() {
        return StrUtil.format("{}|{}", getRetCd(), getRetInf());
    }
}
