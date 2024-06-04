package com.xmzy.lhdf.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.xmzy.base.annotation.EnumValue;
import com.xmzy.lhdf.enums.TrxProcFlag;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件通知Dto
 *
 * @author L.D.J
 * @date 2021/09/23 15:07
 **/
@Data
public class EventNotificationDto implements Serializable {

    @ApiModelProperty("订单号")
    @NotBlank
    String orderid;

    @EnumValue(enumClass = TrxProcFlag.class)
    @ApiModelProperty("变更模式 2-发货（邮寄）3-激活 4-产品更换")
    @NotBlank
    String trxProcFlag;

    @ApiModelProperty("领取方式 1-邮寄 2-现场领取")
    @NotBlank
    String trxMailFlag;

    @ApiModelProperty("粤通卡号")
    @NotBlank
    String trxYbkcardno;

    @ApiModelProperty("标签号")
    @NotBlank
    String trxTagno;

}
