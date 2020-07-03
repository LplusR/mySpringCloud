package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * 使用这种方式的时候一定要记住将yml配置文件中的  feign:
 *     hystrix:
 *       enabled: true改为true
 */
@Component
public class PaymentHystrixServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_ok(int id) {
        return "-----PaymentFallbackService fall back-paymentInfo_OK , (┬＿┬)";
    }

    @Override
    public String paymentInfo_TimeOut(int id) {
        return "-----PaymentFallbackService fall back-paymentInfo_TimeOut , (┬＿┬)";
    }
}
