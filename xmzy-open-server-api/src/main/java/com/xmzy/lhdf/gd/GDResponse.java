package com.xmzy.lhdf.gd;

import javax.validation.constraints.NotBlank;

import com.xmzy.api.IApiResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 广东联合电服响应信息
 *
 * @author L.D.J
 * @date 2021/09/22 17:51
 **/
@Data
public class GDResponse implements IApiResponse {
    @ApiModelProperty("调用状态 true：成功，false：失败")
    @NotBlank
    boolean success;

    @ApiModelProperty("响应描述")
    String msg;
}
