package com.tongfu.service.impl;

import com.tongfu.dao.ProductPurchaseDao;
import com.tongfu.entity.ProductPurchase;
import com.tongfu.service.ProductPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

    @Autowired
    ProductPurchaseDao productPurchaseDao;

    @Override
    public ProductPurchase selectById(Long id) {
        //根据id查询购买规格
        return productPurchaseDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> selectByProductParent(Map<String, Object> map) {
        return productPurchaseDao.selectByProductParent(map);
    }

    @Override
    public Map getProductPurchase(Map query_map) {
        return productPurchaseDao.getProductPurchase(query_map);
    }

    @Override
    public List<Map> getProductPuchases(Map query_map) {
        return productPurchaseDao.getProductPuchases(query_map);
    }


}
