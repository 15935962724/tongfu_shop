package com.tongfu.dao;

import com.tongfu.entity.ProductPurchase;

import java.util.List;
import java.util.Map;

public interface ProductPurchaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductPurchase record);

    int insertSelective(ProductPurchase record);

    ProductPurchase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductPurchase record);

    int updateByPrimaryKey(ProductPurchase record);

    List<Map<String,Object>> selectByProductParent(Map<String,Object> map);//根据产品id和购买方式查询

    Map getProductPurchase(Map query_map);

    List<Map> getProductPuchases(Map query_map);
}