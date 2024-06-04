package com.xmzy.bank.ghb.api;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.dto.AddWhiteListDto;
import com.xmzy.bank.dto.DeleteWhiteListDto;
import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.api.req.WhiteListEntryParam;
import com.xmzy.bank.ghb.api.req.WhiteListEntryParam.body1;
import com.xmzy.bank.ghb.api.resp.WhiteListEntryVo;
import com.xmzy.bank.ghb.enums.GHBCustType;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.base.util.XmzyBeanUtils;
import com.xmzy.server.base.util.HttpRequestUtil;

import cn.hutool.core.util.StrUtil;

/**
 * 白名单录入
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.WHITE_LIST_ADD_PATH, name = "白名单录入")
public class WhiteListEntryApi extends AbstractReqResponseApi<WhiteListEntryParam, WhiteListEntryVo> {

    /**
     * 添加白名单的请求类型
     */
    public static final String ADD_REQ_TYPE = "1000000000";

    /**
     * 修改白名单的请求类型
     */
    public static final String EDIT_REQ_TYPE = "2000000000";

    /**
     * 删除白名单请求类型
     */
    public static final String DELETE_REQ_TYPE = "3000000000";

    @Override
    public WhiteListEntryParam beforeExecute(WhiteListEntryParam request) throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }
        return super.beforeExecute(request);
    }

    private static String getReqSeqNo() {
        return HttpRequestUtil.getRequestNo();
    }

    /**
     * 添加白名单
     * 
     * @param data
     * @return
     */
    public static WhiteListEntryVo addWhiteList(AddWhiteListDto data) {

        WhiteListEntryParam param = buildRequestParam(data, ADD_REQ_TYPE);

        WhiteListEntryParam.body1 body1 = new body1();

        XmzyBeanUtils.copyProperties(data, body1);
        body1.setReqSeqNo(getReqSeqNo());
        param.setBody1(JSON.toJSONString(body1));

        return param.execute();
    }

    private static WhiteListEntryParam buildRequestParam(AddWhiteListDto data, String reqType) {
        WhiteListEntryParam param = new WhiteListEntryParam();

        param.setTradeNo(getReqSeqNo());
        param.setReqType(reqType);
        param.setCustomerType(GHBCustType.QSTP.code());

        param.setUserId(data.getCustNo());

        param.setCustomerIdNo(data.getCustomerIdNo());
        param.setCustomerIdType(data.getCustomerIdType());
        param.setCustomerName(data.getCustomerName());
        return param;
    }

    /**
     * 修改白名单
     * 
     * @param data
     * @return
     */
    public static WhiteListEntryVo editWhiteList(AddWhiteListDto data) {

        WhiteListEntryParam param = buildRequestParam(data, EDIT_REQ_TYPE);

        WhiteListEntryParam.body1 body1 = new body1();

        XmzyBeanUtils.copyProperties(data, body1);
        body1.setReqSeqNo(getReqSeqNo());
        param.setBody1(JSON.toJSONString(body1));

        return param.execute();
    }

    /**
     * 删除白名单
     * 
     * @param data
     * @return
     */
    public static WhiteListEntryVo deleteWhiteList(DeleteWhiteListDto data) {

        WhiteListEntryParam param = new WhiteListEntryParam();

        param.setTradeNo(getReqSeqNo());
        param.setReqType(DELETE_REQ_TYPE);
        param.setCustomerType(GHBCustType.QSTP.code());

        param.setUserId(data.getCustNo());
        param.setCustomerIdNo(data.getCustomerIdNo());
        param.setCustomerIdType(data.getCustomerIdType());
        param.setCustomerName(data.getCustomerName());
        param.setBody1("");

        return param.execute();
    }

}
