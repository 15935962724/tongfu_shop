package com.tongfu.service;

import com.tongfu.entity.ProductPurchase;
import com.tongfu.entity.Purchase;

import java.util.List;
import java.util.Map;

public interface ProductPurchaseService {

    ProductPurchase selectById(Long id);

    List<Map<String,Object>> selectByProductParent(Map<String,Object> map);

    Map getProductPurchase(Map query_map);

    List<Map> getProductPuchases(Map query_map);

}
