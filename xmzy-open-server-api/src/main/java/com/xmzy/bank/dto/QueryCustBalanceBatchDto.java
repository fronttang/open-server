package com.xmzy.bank.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 批量查询客户余额
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
public class QueryCustBalanceBatchDto {

    /**
     * 客户在银行的客户编号
     */
    @ApiModelProperty("银行客户编号")
    private List<String> acts;

    /**
     * 每页条数
     */
    @ApiModelProperty("每页条数")
    private Integer pageSize;

    /**
     * 查询页码
     */
    @ApiModelProperty("查询页码")
    private Integer page;
}
