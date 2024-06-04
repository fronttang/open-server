package com.xmzy.lhdf.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.xmzy.base.annotation.EnumValue;
import com.xmzy.lhdf.enums.CancelType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记账卡取消
 *
 * @author L.D.J
 * @date 2021/09/23 15:00
 **/
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CancelAccountDto extends BaseDto implements Serializable {
    @ApiModelProperty("机构编码")
    @NotBlank
    String appkey;

    @ApiModelProperty("粤通卡卡号")
    @NotBlank
    String cardNo;

    @ApiModelProperty("银行账号")
    @NotBlank
    String bankAccount;

    @EnumValue(enumClass = CancelType.class)
    @ApiModelProperty("注销方式 1：无卡注销（默认值）2：有卡注销")
    @NotBlank
    String type;
}
