package com.xmzy.webt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 扣费成功通知
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Data
public class BillNotifyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户号
     */
    @ApiModelProperty("客户号")
    private String custNo;

    /**
     * 账单ID
     */
    @ApiModelProperty("账单ID")
    private String billNo;

    /**
     * 扣款金额
     */
    @ApiModelProperty("扣款金额")
    private BigDecimal amt;

    /**
     * 扣款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("扣款时间,格式yyyy-MM-dd")
    private Date date;

    /**
     * 1 通行费扣款成功，2服务费扣款成功
     */
    @ApiModelProperty("1 通行费扣款成功，2服务费扣款成功")
    private String type;

}
