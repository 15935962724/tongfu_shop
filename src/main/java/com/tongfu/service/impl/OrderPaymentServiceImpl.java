package com.tongfu.service.impl;


import com.tongfu.dao.OrderPaymentMapper;
import com.tongfu.dao.WorkshopDao;
import com.tongfu.entity.OrderPayment;
import com.tongfu.entity.Workshop;
import com.tongfu.service.OrderPaymentService;
import com.tongfu.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {


    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(OrderPayment record) {
        return 0;
    }

    @Override
    public int insertSelective(OrderPayment record) {
        return orderPaymentMapper.insertSelective(record);
    }

    @Override
    public OrderPayment selectByPrimaryKey(Long id) {
        return orderPaymentMapper.selectByPrimaryKey(id);
    }

    @Override
    public OrderPayment getByOutTradeNo(String outTradeNo) {
        return orderPaymentMapper.getByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderPayment record) {
        return orderPaymentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderPayment record) {
        return 0;
    }

    @Override
    public OrderPayment getByOrderId(Map orderIds) {
        return orderPaymentMapper.getByOrderId(orderIds);
    }
}
