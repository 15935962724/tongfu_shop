package com.tongfu.service;

import com.tongfu.entity.OrderPayment;
import com.tongfu.entity.ProductMeal;

import java.util.List;
import java.util.Map;

public interface OrderPaymentService {

    int deleteByPrimaryKey(Long id);

    int insert(OrderPayment record);

    int insertSelective(OrderPayment record);

    OrderPayment selectByPrimaryKey(Long id);

    OrderPayment getByOutTradeNo(String outTradeNo);

    int updateByPrimaryKeySelective(OrderPayment record);

    int updateByPrimaryKey(OrderPayment record);

    OrderPayment getByOrderId(Map orderIds);
}