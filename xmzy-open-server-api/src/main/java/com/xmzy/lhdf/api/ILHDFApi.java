package com.xmzy.lhdf.api;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.xmzy.base.Result;
import com.xmzy.base.constant.ProviderType;
import com.xmzy.lhdf.dto.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 联合电服内部调用接口
 *
 * @author L.D.J
 * @date 2021/09/22 17:27
 **/
@FeignClient(name = ProviderType.PROVIDER_OPEN, contextId = ProviderType.PROVIDER_OPEN + "ILHDFApi")
@Api(tags = "联合电服接口")
public interface ILHDFApi {

    /**
     * 车辆能否办理查询接口
     *
     * @return
     */
    @ApiOperation(tags = "联合电服接口", value = "车辆能否办理查询接口")
    @ApiImplicitParam(name = "checkVehicleDto", value = "检测车辆DTO类", dataType = "CheckVehicleDto")
    @GetMapping("/lhdf/check/vehicle")
    @ResponseBody
    Result<?> checkVehicle(@Valid @RequestBody CheckVehicleDto checkVehicleDto);

    /**
     * 申请ETC开户
     *
     * @return
     */
    @ApiOperation(tags = "联合电服接口", value = "申请ETC开户")
    @ApiImplicitParam(name = "submitOrderDto", value = "申请ETC开户类", dataType = "SubmitOrderDto")
    @PostMapping("/lhdf/submit/order")
    @ResponseBody
    Result<?> submitOrder(@Valid @RequestBody SubmitOrderDto submitOrderDto);

    /**
     * ETC开户状态查询
     *
     * @return
     */
    @ApiOperation(tags = "联合电服接口", value = "ETC开户状态查询")
    @ApiImplicitParam(name = "payStatusDto", value = "ETC开户状态查询类", dataType = "GetOrderPayStatusDto")
    @GetMapping("/lhdf/order/apply/sts")
    @ResponseBody
    Result<?> getOrderApplySts(@Valid @RequestBody GetOrderPayStatusDto payStatusDto);

    /**
     * 记账卡注销
     *
     * @return
     */
    @ApiOperation(tags = "联合电服接口", value = "记账卡注销")
    @ApiImplicitParam(name = "cancelAccountDto", value = "记账卡注销类", dataType = "CancelAccountDto")
    @DeleteMapping("/lhdf/cancel/account")
    @ResponseBody
    Result<?> cancelAccount(@Valid @RequestBody CancelAccountDto cancelAccountDto);

    /**
     * 添加黑名单
     *
     * @return
     */
    @ApiOperation(tags = "联合电服接口", value = "添加黑名单")
    @ApiImplicitParam(name = "changeBlackListDto", value = "修改黑名单状态类", dataType = "ChangeBlackListDto")
    @PostMapping("/lhdf/blacklist")
    @ResponseBody
    Result<?> addBlacklist(@Valid @RequestBody ChangeBlackListDto changeBlackListDto);

    /**
     * 解除黑名单
     *
     * @return
     */
    @ApiOperation(tags = "联合电服接口", value = "解除黑名单")
    @ApiImplicitParam(name = "changeBlackListDto", value = "修改黑名单状态类", dataType = "ChangeBlackListDto")
    @DeleteMapping("/lhdf/blacklist")
    @ResponseBody
    Result<?> removeBlacklist(@Valid @RequestBody ChangeBlackListDto changeBlackListDto);

}
