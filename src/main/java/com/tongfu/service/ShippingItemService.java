package com.tongfu.service;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.entity.Order;
import com.tongfu.entity.ProductMealOrder;
import com.tongfu.entity.ShippingItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ShippingItemService {

    int deleteByPrimaryKey(Long id);

    int insert(ShippingItem record);

    int insertSelective(ShippingItem record);

    ShippingItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShippingItem record);

    int updateByPrimaryKey(ShippingItem record);

    int insertSelectiveMap(Map<String,Object> map);

}
