package com.xmzy.lhdf.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单状态查询接口DTO
 *
 * @author L.D.J
 * @date 2021/09/23 14:47
 **/
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetOrderPayStatusDto extends BaseDto implements Serializable {
    @ApiModelProperty("客户标识")
    String userid;

    @ApiModelProperty("手机号")
    String bindTel;

    @ApiModelProperty("订单号")
    String orderId;
}
