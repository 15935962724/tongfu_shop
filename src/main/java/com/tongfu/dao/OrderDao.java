package com.tongfu.dao;

import com.tongfu.entity.Order;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface OrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    Order selectBySn(String sn);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByMember(Long member);

    List<Map<String,Object>> selectOrderList(Map<String,Object> map);

    List<Map<String,Object>> selectAddedOrderList(Map<String,Object> map);

    List<Map<String,Object>> selectOrderView(Map<String,Object> map);

    int selectCount(Map<String,Object> map);

    Integer selectAddedCount(Map<String,Object> map);

    Map getOrder(Map query_map);

    List<Map> getOrderInvoice(Map querymap);

    Map getOrderStatus(Map query_map);

    Integer isProductPurchase(Map query_map);

    Map getAddServiceOrderStatus(Map query_map);

    List<Map> getOrders(Map query_map);

}