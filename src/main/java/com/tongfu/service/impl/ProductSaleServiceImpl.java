package com.tongfu.service.impl;

import com.tongfu.dao.ProductSaleDao;
import com.tongfu.entity.ProductSale;
import com.tongfu.service.ProductSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSaleServiceImpl implements ProductSaleService {

    @Autowired
    ProductSaleDao productSaleDao;


    @Override
    public int insertProductSale(ProductSale productSale) {
        return productSaleDao.insertSelective(productSale);
    }
}
