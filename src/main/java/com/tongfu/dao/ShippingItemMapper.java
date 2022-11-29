package com.tongfu.dao;

import com.tongfu.entity.ShippingItem;

import java.util.Map;

public interface ShippingItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShippingItem record);

    int insertSelective(ShippingItem record);

    ShippingItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShippingItem record);

    int updateByPrimaryKey(ShippingItem record);

    int insertSelectiveMap(Map<String,Object> map);
}