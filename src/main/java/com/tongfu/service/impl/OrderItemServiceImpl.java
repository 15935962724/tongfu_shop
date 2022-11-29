package com.tongfu.service.impl;

import com.tongfu.dao.OrderItemDao;
import com.tongfu.entity.OrderItem;
import com.tongfu.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemDao orderItemDao;


    @Override
    public int insertOrderItem(OrderItem orderItem) {
        return orderItemDao.insertSelective(orderItem);
    }

    @Override
    public List<OrderItem> selectByOrderid(Long orderId) {
        return orderItemDao.selectByOrderId(orderId);
    }

    @Override
    public List<Map<String,Object>> selectProductid(Long orderId) {
        return orderItemDao.selectProductid(orderId);
    }

    @Override
    public int deleteItem(Long id) {
        return orderItemDao.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(OrderItem record) {
        return 0;
    }

    @Override
    public int insertSelective(OrderItem record) {
        return 0;
    }

    @Override
    public OrderItem selectByPrimaryKey(Long id) {
        return orderItemDao.selectByPrimaryKey(id);
    }


    @Override
    public int updateByPrimaryKeySelective(OrderItem record) {
        return orderItemDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderItem record) {
        return orderItemDao.updateByPrimaryKey(record);
    }

    @Override
    public int insertList(List<OrderItem> list) {
        return 0;
    }
}
