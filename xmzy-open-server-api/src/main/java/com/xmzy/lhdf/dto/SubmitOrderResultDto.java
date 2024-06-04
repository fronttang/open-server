package com.xmzy.lhdf.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xmzy.lhdf.gd.GDResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单申请结果DTO
 *
 * @author L.D.J
 * @date 2021/09/23 14:44
 **/
@Data
public class SubmitOrderResultDto extends GDResponse implements Serializable {
    @JsonProperty(value = "order_id")
    @ApiModelProperty("订单号")
    String orderId;
}
