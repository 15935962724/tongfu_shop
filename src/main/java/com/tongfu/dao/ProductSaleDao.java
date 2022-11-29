package com.tongfu.dao;

import com.tongfu.entity.ProductSale;

public interface ProductSaleDao {
    int insert(ProductSale record);

    int insertSelective(ProductSale record);
}