package com.xmzy.lhdf.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dto基类
 *
 * @author L.D.J
 * @date 2021/09/22 17:44
 **/
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseDto implements Serializable {
    @ApiModelProperty("请求渠道")
    String reqFrom;

    @ApiModelProperty("机构编码")
    @NotBlank
    String appKey;
}
