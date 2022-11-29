package com.tongfu.service.impl;

import com.tongfu.dao.CartDao;
import com.tongfu.dao.CartItemDao;
import com.tongfu.entity.Cart;
import com.tongfu.entity.CartItem;
import com.tongfu.entity.MyCart;
import com.tongfu.service.CartItemService;
import com.tongfu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemDao cartitemDao;


    @Override
    public int insertCartItem(CartItem cartitem) {
        return cartitemDao.insertSelective(cartitem);
    }

    @Override
    public List<CartItem> selectByCart(Long cart) {
        return cartitemDao.selectByCartId(cart);
    }

    @Override
    public CartItem selectByCartPurchase(Long cart, Long purchase) {
        Map<String,Object> map=new HashMap<>();
        map.put("cart",cart);
        map.put("purchase",purchase);
        return cartitemDao.selectByCartPurchase(map);
    }

    @Override
    public int updateCartItem(CartItem cartItem) {
        return cartitemDao.updateByPrimaryKeySelective(cartItem);
    }

    @Override
    public List<MyCart> selectMyCart(Map query_map) {
        return cartitemDao.selectMyCart(query_map);
    }

    @Override
    public int delCartItem(Long id) {
        return cartitemDao.deleteByPrimaryKey(id);
    }
}
