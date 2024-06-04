package com.xmzy.lhdf.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xmzy.base.annotation.EnumValue;
import com.xmzy.lhdf.enums.OrderSts;
import com.xmzy.lhdf.gd.GDResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单状态查询结果DTO
 *
 * @author L.D.J
 * @date 2021/09/23 14:56
 **/
@Data
public class GetOrderPayStatusResultDto extends GDResponse implements Serializable {
    @JsonProperty(value = "order_status")
    @ApiModelProperty("订单状态")
    @EnumValue(enumClass = OrderSts.class)
    String orderStatus;

    @JsonProperty(value = "order_time")
    @ApiModelProperty("订单时间")
    String orderTime;
}
