package com.tongfu.service.impl;

import com.tongfu.dao.CartDao;
import com.tongfu.entity.Cart;
import com.tongfu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cartDao;


    @Override
    public int insertCart(Cart cart) {
        return cartDao.insertSelective(cart);
    }

    @Override
    public Cart selectByMember(Map query_map) {
        return cartDao.selectByMember(query_map);
    }

    @Override
    public int delCart(Long id) {
        return cartDao.deleteByPrimaryKey(id);
    }

    @Override
    public Cart selectById(Long id) {
        return cartDao.selectByPrimaryKey(id);
    }

    @Override
    public Integer selectCartQuantitySum(Long member) {

        return cartDao.selectCartSum(member);
    }

    @Override
    public List<Map> getCarts(Map query_map) {
        return cartDao.getCarts(query_map);
    }

    @Override
    public List<Map> getByCartItem(Map query_map) {
        return cartDao.getByCartItem(query_map);
    }


}
