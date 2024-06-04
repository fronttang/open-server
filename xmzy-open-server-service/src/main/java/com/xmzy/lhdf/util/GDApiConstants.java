package com.xmzy.lhdf.util;

/**
 * 广东联合电服接口常量
 *
 * @author L.D.J
 * @date 2021/09/22 16:41
 **/
public interface GDApiConstants {

    /**
     * 渠道授权登录 post
     */
    String PARTNER_LOGIN = "/obu-selfinstall-api-new/services/v1/partnerLogin";

    /**
     * 车辆能否办理查询接口，用于系统判断车辆是否可以申请 post
     **/
    String CHECK_VEHICLE = "/obu-selfinstall-api-new/services/v1/onlineServices/checkVehicle";

    /**
     * 申请ETC开户订单，用于上传开户数据的信息 post
     **/
    String ETC_SUBMIT_ORDER = "/obu-selfinstall-api-new/services/v1/onlineServices/submitOrder";

    /**
     * 订单状态查询接口（根据订单号），在开户订单接口调用后，轮询请求获取数据 post
     **/
    String GET_ORDER_PAY_STATUS = "/obu-selfinstall-api-new/services/v1/onlineServices/getOrderPayStatus";

    /**
     * 记账卡注销 post
     **/
    String ONLINE_ACCOUNT_CANCEL = "/obu-selfinstall-api-new/services/v1/onlineServices/onlineAccountCancel";

    /**
     * 黑名单下发，需要等到返回调用状态为success为TRUE时才能判断黑名单下黑成功 post
     **/
    String ACCOUNT_ADD_BLACK_LIST = "/obu-selfinstall-api-new/services/v1/onlineObuServices/accountCStopBlackListIssue";

    /**
     * 解除黑名单 post
     **/
    String ACCOUNT_REMOVE_BLACK_LIST =
        "/obu-selfinstall-api-new/services/v1/onlineObuServices/accountCStopBlackListRelieve";
}
