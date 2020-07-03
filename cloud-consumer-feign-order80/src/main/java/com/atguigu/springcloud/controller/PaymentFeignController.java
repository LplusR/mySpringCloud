package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/getPaymentById/{Id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("Id") Long id ){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/getPortTimeout")
    public String getServerPortTimeOut(){
        return paymentFeignService.getServerPortTimeOut();
    }
}
