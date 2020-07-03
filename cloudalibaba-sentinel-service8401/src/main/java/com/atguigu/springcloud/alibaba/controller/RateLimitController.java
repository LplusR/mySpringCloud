package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.atguigu.springcloud.alibaba.myhandler.CustomBlockHandler;
import com.atguigu.springcloud.entities.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    @GetMapping(value = "/myMethod")
    @SentinelResource(value = "meMethod",blockHandlerClass = CustomBlockHandler.class,blockHandler = "CommonResult")
    public CommonResult myMethod(){
        return new CommonResult(200,"自定义方法",null);
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(444, exception.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping(value = "/getTest")
    public String TestA(){
        String name = Thread.currentThread().getName();
        System.out.println("--------------test"+name);
        return "--------------test"+name;
    }
}