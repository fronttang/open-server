package com.xmzy.bank.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件生成通知
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
public class WithholdingFileNotifyDto {

    /**
     * 台账日期 YYYYMMDD
     */
    @NotNull
    @ApiModelProperty("台账日期 YYYYMMDD")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date postingDate;

    /**
     * 文件全路径
     */
    @NotNull
    @NotEmpty
    @ApiModelProperty("文件全路径")
    private String filePath;

    /**
     * 第三方批次号
     */
    @NotNull
    @NotEmpty
    @ApiModelProperty("第三方批次号")
    private String thirdBatchNo;

    /**
     * 代扣类型
     * 
     * SERVICE-服务费
     * 
     * COLLECTION-通行费
     * 
     * WALLET-历史通行费
     * 
     * MARGIN-保证金
     * 
     * INVOICE-联合电服结算清单
     */
    @NotNull
    @NotEmpty
    @ApiModelProperty("代扣类型 SERVICE-服务费 COLLECTION-通行费 WALLET-历史通行费 MARGIN-保证金 INVOICE-联合电服结算清单")
    private String withholdType;
}
