package com.tongfu.service;

import com.tongfu.entity.ProductImg;
import com.tongfu.entity.Purchase;

import java.util.List;
import java.util.Map;

public interface ProductImgService {

    List<ProductImg> selectByProductId(Long productId);


}
