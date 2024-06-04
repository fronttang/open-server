package com.xmzy.bank;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.GHBController;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.base.util.LoggerHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 华兴银行返回的特殊处理
 * 
 * @author FrontTang
 * @date 2021/08/29
 */
@Slf4j
@RestControllerAdvice
@GHBController
public class GHBRestControllerAdvice {

    /**
     * 华兴银行业务异常
     * 
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = GHBBusinessException.class)
    public GHBResponse ghbBusinessException(HttpServletRequest request, GHBBusinessException e) {
        log.error(LoggerHelper.printTop10StackTrace(e));
        GHBResponse response = e.toGHBResponse();
        response.setBody("{}");
        return response;
    }
}
