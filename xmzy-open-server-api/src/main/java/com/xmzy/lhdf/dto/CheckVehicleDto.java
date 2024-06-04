package com.xmzy.lhdf.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.xmzy.base.annotation.EnumValue;
import com.xmzy.lhdf.enums.VehicleColor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查车辆是否可以申请Dto
 *
 * @author L.D.J
 * @date 2021/09/22 17:46
 **/
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CheckVehicleDto extends BaseDto implements Serializable {
    @ApiModelProperty("车牌号码")
    @NotBlank
    String vehiclePlate;

    @EnumValue(enumClass = VehicleColor.class)
    @ApiModelProperty("车牌颜色")
    @NotBlank
    String vehicleColor;
}
