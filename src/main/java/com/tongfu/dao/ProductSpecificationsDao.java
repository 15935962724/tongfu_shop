package com.tongfu.dao;

import com.tongfu.entity.ProductSpecifications;

import java.util.List;

public interface ProductSpecificationsDao {
    int insert(ProductSpecifications record);

    int insertSelective(ProductSpecifications record);

    List<ProductSpecifications> selectByProductId(Long productId);
}