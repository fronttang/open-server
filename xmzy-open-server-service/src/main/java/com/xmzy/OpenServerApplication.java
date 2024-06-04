package com.xmzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.xmzy.api.annotation.ApiScan;
import com.xmzy.mq.annotation.EnableMQ;
import com.xmzy.scheduling.annotation.EnableScheduling;
import com.xmzy.server.base.annotation.EnableHttpClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
@EnableMQ
@EnableHttpClient
@EnableScheduling
@ApiScan({"com.xmzy.bank.ghb.api", "com.xmzy.webt.api"})
@MapperScan("com.xmzy.**.mapper")
public class OpenServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenServerApplication.class, args);
    }

}
