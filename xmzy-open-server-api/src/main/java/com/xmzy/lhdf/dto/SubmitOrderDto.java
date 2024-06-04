package com.xmzy.lhdf.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单申请接口DTO
 *
 * @author L.D.J
 * @date 2021/09/23 10:00
 **/
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubmitOrderDto extends BaseDto implements Serializable {
    @ApiModelProperty("客户标识")
    String userid;

    @ApiModelProperty("手机号")
    @NotBlank
    String bindTel;

    @ApiModelProperty("产品类型 01:  储值卡 02：免保型记账卡（套装）03：普通记账卡 04：单标签 05: 免保型记账卡(单卡)")
    @NotBlank
    String productId;

    @ApiModelProperty("签约渠道 机构签约必填")
    @NotBlank
    String signType;

    @ApiModelProperty("签约时间 “yyyy-MM-dd HH:mm:ss”,机构签约必填")
    String signTime;

    @ApiModelProperty("签约状态 (0未签约 1正常 2 签约失败，3 已解约）,机构签约必填")
    String signStatus;

    @ApiModelProperty("是否套装 1 套装（默认）2 单卡")
    @NotBlank
    String speFlag;

    @ApiModelProperty("是否充值型 01: 充值型 02：代扣型（默认）")
    @NotBlank
    String recharegeFlag;

    @ApiModelProperty("价格 默认为0")
    Integer price = 0;

    @ApiModelProperty("客户号")
    String userno;

    @ApiModelProperty("地址ID")
    String addressId;

    @ApiModelProperty("收货地址省")
    @NotBlank
    String addrProvince;

    @ApiModelProperty("收货地址市")
    @NotBlank
    String addrCity;

    @ApiModelProperty("收货地址区")
    @NotBlank
    String addrDistrict;

    @ApiModelProperty("收货详细地址")
    @NotBlank
    String addrAddress;

    @ApiModelProperty("收货人")
    @NotBlank
    String addrConsigneeName;

    @ApiModelProperty("收货手机号")
    @NotBlank
    String addrConMobilephone;

    @ApiModelProperty("收货电话")
    @NotBlank
    String addrTel;

    @ApiModelProperty("收货电子邮箱")
    String addrEmail;

    @ApiModelProperty("信用审核标志位 0:通过;1:不通过;（货车ETC填0）")
    String creditStatus;

    @ApiModelProperty("银行卡号")
    String creditCardNo;

    @ApiModelProperty("银行账号")
    String bankAccount;

    @ApiModelProperty("银行账户户名")
    String accName;

    @ApiModelProperty("账户联系人")
    String contactName;

    @ApiModelProperty("账户联系人手机")
    String contactPhone;

    @ApiModelProperty("客户名称")
    @NotBlank
    String organ;

    @ApiModelProperty("用户类型")
    @NotBlank
    String userType;

    @ApiModelProperty("证件类型")
    @NotBlank
    String idtype;

    @ApiModelProperty("证件号码")
    @NotBlank
    String idnum;

    @ApiModelProperty("手机号码")
    @NotBlank
    String mobilephone;

    @ApiModelProperty("地址 个人：身份证地址 单位：经营场所")
    @NotBlank
    String userInfoAddress;

    @ApiModelProperty("车牌号码")
    @NotBlank
    String vehiclePlate;

    @ApiModelProperty("车牌号码")
    @NotBlank
    String vehicleColor;

    @ApiModelProperty("车辆类型")
    @NotBlank
    String vehicleType;

    @ApiModelProperty("核定载人数")
    @NotBlank
    Integer approvedCount;

    @ApiModelProperty("核定载质量")
    @NotBlank
    Integer vehicle_capacity;

    @ApiModelProperty("轴数")
    @NotBlank
    Integer vehicleAxles;

    @ApiModelProperty("轮数")
    @NotBlank
    Integer vehicleWheels;

    @ApiModelProperty("车型")
    @NotBlank
    String vehicleTxtclass;

    @ApiModelProperty("车主名称")
    @NotBlank
    String owner;

    @ApiModelProperty("使用性质 营运/非营运")
    @NotBlank
    String funcType;

    @ApiModelProperty("厂牌型号")
    @NotBlank
    String factoryCode;

    @ApiModelProperty("发动机号")
    @NotBlank
    String engineCode;

    @ApiModelProperty("车辆识别号")
    @NotBlank
    String vehicleCode;

    @ApiModelProperty("车辆行驶证地址")
    @NotBlank
    String vehicleInfoAddress;

    @ApiModelProperty("注册日期 默认格式YYYYMMDD")
    @NotBlank
    String registerDate;

    @ApiModelProperty("发证日期 默认格式YYYYMMDD")
    @NotBlank
    String issueDate;

    @ApiModelProperty("车辆长 单位毫米")
    @NotBlank
    Integer vehicleLength;

    @ApiModelProperty("车辆宽 单位毫米")
    @NotBlank
    Integer vehicleWidth;

    @ApiModelProperty("车辆高 单位毫米")
    @NotBlank
    Integer vehicleHeight;

    @ApiModelProperty("车辆行驶证正面图片url")
    @NotBlank
    String vehLicensePicUrl1;

    @ApiModelProperty("车辆行驶证反面图片url")
    @NotBlank
    String vehLicensePicUrl2;

    @ApiModelProperty("是否邮寄 01:现场领取;02: 后台邮寄")
    @NotBlank
    String whetherMail;

    @ApiModelProperty("用户联系地址")
    @NotBlank
    String address;

    @ApiModelProperty("总质量")
    @NotBlank
    Integer totalMass;

    @ApiModelProperty("整备质量")
    @NotBlank
    Integer maintenanceMass;

    @ApiModelProperty("准牵引总质量")
    Integer permittedTowWeight;

    @ApiModelProperty("道路运输证图片url")
    String roadTransportCertificateurl;
}
