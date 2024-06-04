package com.xmzy.bank.ghb;

import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.ExponentialBackOff;

/**
 * @author fronttang
 * @date 2021/09/03
 */
public class BackOffTester {

    public static void main(String[] args) {
        ExponentialBackOff backOff = new ExponentialBackOff();
        backOff.setMaxElapsedTime(30000);
        BackOffExecution start = backOff.start();
        System.out.println(backOff.start().nextBackOff());
        System.out.println(backOff.start().nextBackOff());
        System.out.println(backOff.start().nextBackOff());
        System.out.println(start.nextBackOff());
        System.out.println(start.nextBackOff());
        System.out.println(start.nextBackOff());
        System.out.println(start.nextBackOff());
        System.out.println(start.nextBackOff());
    }
}
