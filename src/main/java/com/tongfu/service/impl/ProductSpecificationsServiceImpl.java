package com.tongfu.service.impl;

import com.tongfu.dao.ProductImgDao;
import com.tongfu.dao.ProductSpecificationsDao;
import com.tongfu.entity.ProductImg;
import com.tongfu.entity.ProductSpecifications;
import com.tongfu.service.ProductImgService;
import com.tongfu.service.ProductSpecificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecificationsServiceImpl implements ProductSpecificationsService {

    @Autowired
    ProductSpecificationsDao productSpecificationsDao;

    @Override
    public List<ProductSpecifications> selectByProductId(Long productId) {
        return productSpecificationsDao.selectByProductId(productId);
    }
}
