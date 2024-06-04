package com.xmzy.bank.api;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmzy.bank.dto.AddWhiteListDto;
import com.xmzy.bank.dto.CustDepositNotifyDto;
import com.xmzy.bank.dto.DeleteWhiteListDto;
import com.xmzy.bank.dto.GetReconciliationFileDto;
import com.xmzy.bank.dto.PlatformTransactionDto;
import com.xmzy.bank.dto.QueryCustBalanceBatchDto;
import com.xmzy.bank.dto.WithholdingFileNotifyDto;
import com.xmzy.bank.vo.QueryCustBalanceBatchVo;
import com.xmzy.bank.vo.QueryPlatformActBalanceVo;
import com.xmzy.base.Result;
import com.xmzy.base.constant.ProviderType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 银行接口对内部使用接口
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@FeignClient(name = ProviderType.PROVIDER_OPEN, contextId = ProviderType.PROVIDER_OPEN + "IGHBBankApi")
@Api(tags = "华兴银行接口")
public interface IGHBBankApi {

    /**
     * 查询平台账户余额
     * 
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "查询平台账户余额")
    @GetMapping("/bank/platform/account/balance")
    @ResponseBody
    Result<QueryPlatformActBalanceVo> queryPlatformAccountBalance();

    /**
     * 批量查询客户余额
     * 
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "批量查询客户余额")
    @PostMapping("/bank/cust/account/batch")
    @ResponseBody
    Result<QueryCustBalanceBatchVo> queryCustBalanceBatch(@RequestBody @Valid QueryCustBalanceBatchDto data);

    /**
     * 添加白名单
     * 
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "添加白名单")
    @PostMapping("/bank/whitelist")
    @ResponseBody
    Result<?> addWhiteList(@RequestBody @Valid AddWhiteListDto data);

    /**
     * 修改白名单
     * 
     * @param data
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "修改白名单")
    @PutMapping("/bank/whitelist")
    @ResponseBody
    Result<?> editWhiteList(@RequestBody @Valid AddWhiteListDto data);

    /**
     * 删除白名单
     * 
     * @param data
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "删除白名单")
    @DeleteMapping("/bank/whitelist")
    @ResponseBody
    Result<?> deleteWhiteList(@RequestBody @Valid DeleteWhiteListDto data);

    /**
     * 补缴保证金通知
     * 
     * @param data
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "补缴保证金通知")
    @PostMapping("/bank/cust/deposit/notify")
    @ResponseBody
    Result<?> custSupplementaryDepositNotify(@RequestBody @Valid CustDepositNotifyDto data);

    /**
     * 文件生成通知
     * 
     * @param data
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "文件生成通知")
    @PostMapping("/bank/file/notify")
    @ResponseBody
    Result<?> withholdingBatchFileNotify(@RequestBody @Valid WithholdingFileNotifyDto data);

    /**
     * 平台交易
     * 
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "平台交易")
    @PostMapping("/bank/platform/transaction")
    @ResponseBody
    Result<?> platformTransaction(@RequestBody @Valid PlatformTransactionDto data);

    /**
     * 获取对账文件
     * 
     * @param data
     *            文件OSS路径
     * @return
     */
    @ApiOperation(tags = "华兴银行接口", value = "获取对账文件")
    @PostMapping("/bank/reconciliation/file")
    @ResponseBody
    Result<String> getReconciliationFile(@RequestBody @Valid GetReconciliationFileDto data);

    /**
     * 客户开放提现
     * 
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "merchantId", value = "银行客户编号", dataType = "String", required = true)})
    @ApiOperation(tags = "华兴银行接口", value = "客户开放提现")
    @PostMapping("/bank/open/withdrawal/{merchantId}")
    @ResponseBody
    Result<?> custOpenWithdrawal(@PathVariable("merchantId") String merchantId);
}
