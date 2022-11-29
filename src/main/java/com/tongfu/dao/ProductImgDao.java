package com.tongfu.dao;

import com.tongfu.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    int insert(ProductImg record);

    int insertSelective(ProductImg record);

    List<ProductImg> selectByProductId(Long productId);
}