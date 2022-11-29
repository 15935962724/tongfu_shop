package com.tongfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
//订单列表计算价格
@Service("orderSer")
public class OrderSer {
    @Autowired
    OrderService orderService;

    public BigDecimal getAmount(Long orderId){
        BigDecimal result = orderService.getTotalAmount(orderId);
        return  result;
    }

}
