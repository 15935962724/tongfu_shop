package com.tongfu.dao;

import com.tongfu.entity.Cart;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

public interface CartDao {
    int deleteByPrimaryKey(Long id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Long id);

    Cart selectByMember(Map query_map);

    Integer selectCartSum(Long member);//根据用户查询购物车商品总数量

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Map> getCarts(Map query_map);

    List<Map> getByCartItem(Map query_map);
}