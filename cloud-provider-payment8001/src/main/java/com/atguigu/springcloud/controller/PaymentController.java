package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.service.impl.PaymentServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/creat")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.creatPayment(payment);
        if(result>0){
            return new CommonResult(200, "插入数据库成功,port:"+serverPort,payment);
        }else{
            return new CommonResult(444,"插入数据库失败,port:"+serverPort,null);
        }
    }

    @GetMapping(value = "/payment/getPaymentById/{Id}")
    public CommonResult getPaymentById(@PathVariable("Id") Long id){
        Payment pay= paymentService.getPaymentById(id);
        if(pay!=null){
            return new CommonResult(200, "查询成功,port:"+serverPort,pay);
        }else{
            return new CommonResult(444,"查询失败,port:"+serverPort,null);
        }
    }

    @GetMapping(value = "/payment/getClientMes")
    public Object getClientMes(){
        List<String> services = discoveryClient.getServices();
        for(String service : services){
            log.info("******* service:"+ service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort());
        }
        return discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentPort(){
        return serverPort;
    }

    //该接口测试openFeign超时等待，需要修改配置文件解决问题
    @GetMapping(value = "/payment/getPortTimeOut")
    public String getServerPortTimeOut(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin()
    {
        return "hi ,i'am paymentzipkin server fall back，welcome to atguigu，O(∩_∩)O哈哈~";
    }

}
