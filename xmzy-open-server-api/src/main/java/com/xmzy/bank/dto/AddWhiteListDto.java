package com.xmzy.bank.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.xmzy.bank.ghb.enums.GHBAcademic;
import com.xmzy.bank.ghb.enums.GHBCustIdType;
import com.xmzy.bank.ghb.enums.GHBOccupation;
import com.xmzy.bank.ghb.enums.GHBSex;
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
public class AddWhiteListDto {

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

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 性别 女0 男1
     */
    @EnumValue(enumClass = GHBSex.class)
    @ApiModelProperty("性别女0  男1")
    private String sex;

    /**
     * 证件到期日 YYYYMMDD
     */
    @ApiModelProperty("证件到期日 YYYYMMDD")
    private String certificateDueDate;

    /**
     * 签证机关
     */
    @ApiModelProperty("签证机关")
    private String issueOrg;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String detailAddress;

    /**
     * 邮政编码
     */
    @ApiModelProperty("邮政编码")
    private String postalCode;

    /**
     * 联系地址
     */
    @ApiModelProperty("联系地址")
    private String contactAddress;

    /**
     * 传真
     */
    @ApiModelProperty("传真")
    private String fax;

    /**
     * 保证金金额
     */
    @NotEmpty
    @NotBlank
    @ApiModelProperty("保证金金额")
    private String marginAmt;

    /**
     * 学历
     */
    @EnumValue(enumClass = GHBAcademic.class)
    @ApiModelProperty("学历")
    private String academic;

    /**
     * 职业
     */
    @EnumValue(enumClass = GHBOccupation.class)
    @ApiModelProperty("职业")
    private String occupation;

}
