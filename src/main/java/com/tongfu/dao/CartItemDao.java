package com.tongfu.dao;

import com.tongfu.entity.CartItem;
import com.tongfu.entity.MyCart;

import java.util.List;
import java.util.Map;

public interface CartItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(CartItem record);

    int insertSelective(CartItem record);

    CartItem selectByPrimaryKey(Long id);

    List<CartItem> selectByCartId(Long cart);//根据购物车id查询

    CartItem selectByCartPurchase(Map<String,Object> map);//根据购物车id和规格查询

    List<MyCart> selectMyCart(Map query_map);//根据用户查询我的购物车

    int updateByPrimaryKeySelective(CartItem record);

    int updateByPrimaryKey(CartItem record);
}