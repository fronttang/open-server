package com.xmzy.bank.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取对账文件
 * 
 * @author fronttang
 * @date 2021/09/18
 */
@Data
public class GetReconciliationFileDto {

    /**
     * 文件类型
     * 
     * 2：客户对账文件（只取对账日期的当天新增客户信息）
     * 
     * 3：交易对账文件
     * 
     * 4：余额对账文件
     * 
     * 5：批量分账
     * 
     * 6：增量客户注册信息文件
     */
    @NotNull
    @NotEmpty
    @ApiModelProperty("文件类型, 6：增量客户注册信息文件")
    private String fileType;

    /**
     * 对账日期 YYYYMMDD，只支持最近30天内
     */
    @NotNull
    @ApiModelProperty("对账日期 YYYYMMDD，只支持最近30天内")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date fileDate;
}
