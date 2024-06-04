package com.xmzy.lhdf.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xmzy.lhdf.gd.GDResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查车辆是否可以申请结果DTO
 *
 * @author L.D.J
 * @date 2021/09/22 18:06
 **/
@Data
public class CheckVehicleResultDto extends GDResponse implements Serializable {
    @JsonProperty(value = "return_code")
    @ApiModelProperty("响应码 0000:允许办理 0001:不允许办理,车牌已占用")
    String returnCode;

    @JsonProperty(value = "return_msg")
    @ApiModelProperty("响应信息")
    String returnMsg;

    @JsonProperty(value = "sc_hc_msg")
    @ApiModelProperty("货车单卡提示")
    String scHcMsg;
}
