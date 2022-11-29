package com.tongfu.service;

import com.tongfu.entity.Cart;

import java.util.List;
import java.util.Map;


public interface CartService {


    int insertCart(Cart cart);

    Cart selectByMember(Map query_map);

    int delCart(Long id);

    Cart selectById(Long id);

    Integer selectCartQuantitySum(Long member);

    List<Map> getCarts(Map query_map);

    List<Map> getByCartItem(Map query_map);


}
