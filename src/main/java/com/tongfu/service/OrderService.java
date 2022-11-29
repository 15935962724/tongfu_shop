package com.tongfu.service;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.entity.Member;
import com.tongfu.entity.Order;
import com.tongfu.entity.ProductMealOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface OrderService {

    int insert(Order record);

    int insertSelective(Order record);

    Map insertOrder( Long oreceiverId, String myCartItemId, Integer invoiceType,Integer orderType,Integer isInvoice,Integer buytype,Long goproductid,Long gopurchaseid,Integer gonum,Boolean isaddedvalue);

//    套餐购买
    Map<String,Object> saveProductMealOrder(ProductMealOrder productMealOrder);

    String notifyUrlService(JSONObject jsonObject);

    Order selectById(Long id);

    BigDecimal getTotalAmount(Long id);

//    BigDecimal getCountAmount(Long id);

    List<Order> selectByMember(Long member);

    List<Map<String,Object>> selectOrderList(Map<String,Object> querymap);

    List<Map<String,Object>> selectAddedOrderList(Map<String,Object> querymap);

    int selectCount(Map<String ,Object> map);//查询数量

    Integer selectAddedCount(Map<String ,Object> map);//查询增值服务订单数量

    List<Map<String,Object>> selectOrderView(Map<String,Object> querymap);//查询订单详情

    int updateOrder(Order order);

    Order selectBySn(String sn);

    int deleteByPrimaryKey(Long id);

    Map saveOrder(Long oreceiverId, String myCartItemId,Integer buytype,Long isaddedvalue,Boolean isMakeInvoice,
                  Long type,String pcontent,String title,String taxpayerNo,String pmobile,String pemail,String companyName,String registerAddress,String registerMobile,String bank,
                  String bankAccount,String memberName,Long areaId,String address);

    Map getOrder(Map query_map);

    List<Map> getOrderInvoice(Map querymap);//查询已付款开票订单

    Map getOrderStatus(Map query_map);

    Integer isProductPurchase(Map query_map);

    Map getAddServiceOrderStatus(Map query_map);

    List<Map> getOrders(Map querymap);//根据付款单查询订单

}
