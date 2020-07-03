package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IMessageProvider;
import com.atguigu.springcloud.service.impl.MessageProviderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider provide;

    @GetMapping(value = "/sendMessage")
    public String SendMessage(){
        return provide.send();
    }
}
