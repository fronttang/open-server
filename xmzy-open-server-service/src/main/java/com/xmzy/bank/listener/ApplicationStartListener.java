package com.xmzy.bank.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.xmzy.bank.ghb.util.SecretKeyHolder;

/**
 * 应用启动后调用逻辑
 * 
 * @author fronttang
 * @date 2021/08/31
 */
@Component
public class ApplicationStartListener implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 初始化secretkey定时任务
        SecretKeyHolder.init();
    }

}
