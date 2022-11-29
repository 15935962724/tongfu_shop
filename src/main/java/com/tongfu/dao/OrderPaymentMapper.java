package com.tongfu.dao;

import com.tongfu.entity.OrderPayment;

import java.util.Map;

public interface OrderPaymentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPayment record);

    int insertSelective(OrderPayment record);

    OrderPayment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPayment record);

    int updateByPrimaryKey(OrderPayment record);

    OrderPayment getByOutTradeNo(String outTradeNo);

    OrderPayment getByOrderId(Map orderIds);

}