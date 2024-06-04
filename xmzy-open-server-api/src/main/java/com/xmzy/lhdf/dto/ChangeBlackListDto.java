package com.xmzy.lhdf.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加黑名单DTO
 *
 * @author L.D.J
 * @date 2021/09/23 15:13
 **/
@Data
public class ChangeBlackListDto extends BaseDto implements Serializable {
    @ApiModelProperty("粤通卡卡号")
    @NotBlank
    String cardNo;

    @ApiModelProperty("信用卡卡号")
    @NotBlank
    String creditCardNo;

    @ApiModelProperty("银行账号")
    @NotBlank
    String bankAccount;

    @ApiModelProperty("银行编码")
    @NotBlank
    String bankCode;
}
