package com.atguigu.springcloud.MySelfRule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OrderRuleImpl implements IOrderRule{

    private static final AtomicInteger atomicInteger=new AtomicInteger(0);

    private final int incrementNumer(){
        int current;
        int next ;
       do {
           current=atomicInteger.get();
           next =current+1;
       }while(!this.atomicInteger.compareAndSet(current, next));
        return next;
    }

    @Override
    public ServiceInstance getService(List<ServiceInstance> instances) {
        int next = incrementNumer();
        int index=next % instances.size();

        return instances.get(index);
    }
}
