package com.tongfu.service;

import com.tongfu.entity.OrderItem;

import java.util.List;
import java.util.Map;


public interface OrderItemService {


    int insertOrderItem(OrderItem orderItem);

    List<OrderItem> selectByOrderid(Long orderId);

    List<Map<String,Object>> selectProductid(Long orderId);//根据订单编号查询产品id

    int deleteItem(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int insertList(List<OrderItem> list);



}
