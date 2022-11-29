package com.tongfu.service;

import com.tongfu.entity.ProductImg;
import com.tongfu.entity.ProductSpecifications;

import java.util.List;

public interface ProductSpecificationsService {

    List<ProductSpecifications> selectByProductId(Long productId);


}
