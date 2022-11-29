package com.tongfu.service.impl;

import com.tongfu.dao.ProductImgDao;
import com.tongfu.dao.PurchaseDao;
import com.tongfu.entity.ProductImg;
import com.tongfu.entity.Purchase;
import com.tongfu.service.ProductImgService;
import com.tongfu.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductImgServiceImpl implements ProductImgService {

    @Autowired
    ProductImgDao productImgDao;

    @Override
    public List<ProductImg> selectByProductId(Long productId) {
        return productImgDao.selectByProductId(productId);
    }
}
