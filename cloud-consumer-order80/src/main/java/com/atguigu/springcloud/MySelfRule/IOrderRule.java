package com.atguigu.springcloud.MySelfRule;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface IOrderRule {
    ServiceInstance getService(List<ServiceInstance> instances);
}
