package com.xmzy.bank.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.xmzy.bank.ghb.enums.GHBCustIdType;
import com.xmzy.base.annotation.EnumValue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加白名单入参
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
public class DeleteWhiteListDto {

    /**
     * 客户号
     */
    @NotEmpty
    @NotBlank
    @ApiModelProperty("客户号")
    private String custNo;

    /**
     * 客户姓名
     */
    @NotEmpty
    @NotBlank
    @ApiModelProperty("客户姓名")
    private String customerName;

    /**
     * 证件类型 1010-居民身份证
     */
    @EnumValue(enumClass = GHBCustIdType.class)
    @ApiModelProperty("证件类型 1010-居民身份证")
    private String customerIdType;

    /**
     * 证件号码
     */
    @NotEmpty
    @NotBlank
    @ApiModelProperty("证件号码")
    private String customerIdNo;

}
