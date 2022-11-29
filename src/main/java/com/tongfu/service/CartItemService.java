package com.tongfu.service;

import com.tongfu.entity.CartItem;
import com.tongfu.entity.MyCart;

import java.util.List;
import java.util.Map;


public interface CartItemService {


    int insertCartItem(CartItem cartitem);

    List<CartItem> selectByCart(Long cart);//根据购物车id查询

    CartItem selectByCartPurchase(Long cart,Long purchase);//根据购物车id和规格id查询

    int updateCartItem(CartItem cartItem);//修改购物车项

    List<MyCart> selectMyCart(Map query_map);//根据用户查询我的购物车

    int delCartItem(Long id);

}
