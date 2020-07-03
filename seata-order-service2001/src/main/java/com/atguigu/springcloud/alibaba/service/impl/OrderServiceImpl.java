package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import com.atguigu.springcloud.entities.CommonResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    //此处添加这个注解之后，后续调用的方法如果出现问题，则会回滚，name是随便起的，rollbackFor是抛出指定异常，此处是抛出所有
    @GlobalTransactional(name = "fsp-creat-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("创建order开始");
        orderDao.create(order);

        log.info("减库存");
        CommonResult storageMes = storageService.decrease(order.getProductId(), order.getCount());
        log.info("减库存完毕，信息为："+storageMes);

        log.info("开始扣钱");
        CommonResult accountMes = accountService.decrease(order.getUserId(), order.getMoney());
        log.info("扣钱完毕，信息为："+accountMes);

        log.info("改订单状态");
        orderDao.update(order.getUserId(),0);
        log.info("改订单状态结束");
    }
}
