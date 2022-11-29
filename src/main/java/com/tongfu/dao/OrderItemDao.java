package com.tongfu.dao;

import com.tongfu.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    List<OrderItem> selectByOrderId(Long orderId);

    List<Map<String,Object>> selectProductid(Long orderId);//查询产品id和产品的总数量

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int insertList(List<OrderItem> list);


}