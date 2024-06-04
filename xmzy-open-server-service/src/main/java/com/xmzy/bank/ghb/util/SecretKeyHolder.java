package com.xmzy.bank.ghb.util;

import java.util.Date;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.xmzy.bank.constant.BankCacheConstants;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.dto.SecretKeyCache;
import com.xmzy.bank.ghb.api.GetSecretKeyApi;
import com.xmzy.bank.ghb.api.resp.GetSecretKeyVo;
import com.xmzy.base.util.RedisUtil;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 密钥缓存<br/>
 * SM4加密密钥有效时效为6小时，从银行生成开始6小时内有效，超过6小时则无法再使用<br/>
 * 新旧密钥交替时间窗口为5分钟，5分内，使用了新密钥，则旧密钥过期，未使用则旧密钥5分钟内仍可使用，过期之后仅可使用新密钥。<br/>
 * 新旧密钥无法在这段时间内随意切换使用。<br/>
 * <br/>
 * 注：对于有效期内获取的秘钥不会生成新密钥，只有在过期或者窗口期内获取才会生成新密钥<br/>
 * （新旧密钥窗口期：例如12:00获取了一次密钥，则在17:57.30-18:02:30这5分钟内为窗口期，超过18:02:30为过期）。
 * 
 * @author fronttang
 * @date 2021/09/10
 */
@Slf4j
public class SecretKeyHolder {

    /**
     * 过期时间6小时
     */
    private static final Long EXPIRE = 1000L * 60 * 60 * 6;

    private static final TimerTask applySecretKey = new ApplySecretKeyTimeTask();

    private static final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

    private static ScheduledFuture<?> scheduledFuture = null;

    public static String getSecretKey() {

        SecretKeyCache cache = RedisUtil.get(BankCacheConstants.getSecretKey(GHBConstants.BANK_ID));

        return Objects.nonNull(cache) ? cache.getAppSecret() : null;
    }

    public static String getOldSecretKey() {

        SecretKeyCache cache = RedisUtil.get(BankCacheConstants.getSecretKeyOld(GHBConstants.BANK_ID));

        return Objects.nonNull(cache) ? cache.getAppSecret() : null;
    }

    public static void setSecretKey(GetSecretKeyVo secretKey) {

        if (Objects.isNull(secretKey)) {
            return;
        }

        SecretKeyCache existKey = RedisUtil.get(BankCacheConstants.getSecretKey(GHBConstants.BANK_ID));

        Date applyTime = DateUtil.parse(secretKey.getApplyTime());

        boolean addNewCache = true;

        // 存在key,新Key和旧key一样
        if (Objects.nonNull(existKey) && secretKey.getAppSecret().equals(existKey.getAppSecret())) {
            // 一样时，说明没有生成新的key
            applyTime = existKey.getApplyTime();
            addNewCache = false;
        }

        if (addNewCache) {

            if (Objects.nonNull(existKey)) {
                // 旧key放到旧key 缓存，
                RedisUtil.set(BankCacheConstants.getSecretKeyOld(GHBConstants.BANK_ID), existKey, (60 * 5));
            }

            // 更新新key
            SecretKeyCache newCache = new SecretKeyCache();
            newCache.setAppSecret(secretKey.getAppSecret());
            newCache.setApplyTime(applyTime);

            // 6小时+5分钟失效
            RedisUtil.set(BankCacheConstants.getSecretKey(GHBConstants.BANK_ID), newCache, (60 * 60 * 6) + (60 * 5));
        }

        // 重置计时器
        cancelScheduledIfNecessary();
        DateTime expireDate = DateUtil.offsetMillisecond(applyTime, EXPIRE.intValue());
        log.info("下次申请密钥时间是:{}", DateUtil.formatDateTime(expireDate));
        scheduledFuture = scheduler.scheduleWithFixedDelay(applySecretKey, expireDate, EXPIRE);
    }

    public static void init() {

        // 程序启动就去申请一次key
        scheduler.setThreadNamePrefix("bank-secret-Key-");
        scheduler.initialize();

        cancelScheduledIfNecessary();

        scheduledFuture = scheduler.scheduleWithFixedDelay(applySecretKey, new Date(), 1000 * 60 * 5);
    }

    private static void cancelScheduledIfNecessary() {
        if (Objects.nonNull(scheduledFuture) && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
    }

    private static class ApplySecretKeyTimeTask extends TimerTask {

        @Override
        public void run() {
            try {
                log.info("开始向银行申请加密密钥:{}", DateUtil.formatDateTime(new Date()));
                GetSecretKeyApi.exec();
            } catch (Exception e) {
                log.error("", e);
                DateTime startTime = DateUtil.offsetMillisecond(new Date(), 1000 * 60 * 5);
                cancelScheduledIfNecessary();
                scheduledFuture = scheduler.scheduleWithFixedDelay(applySecretKey, startTime, 1000 * 60 * 5);
            }
        }
    }
}
