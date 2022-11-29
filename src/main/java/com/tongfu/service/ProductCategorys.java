package com.tongfu.service;

import com.tongfu.dao.CartDao;
import com.tongfu.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//获取父级产品分类
@Service("productCategorys")
public class ProductCategorys {

    @Autowired
    private ProductCategoryService productCategoryService;





    public List<com.tongfu.entity.ProductCategory> getProductCategorys(){
        List<com.tongfu.entity.ProductCategory> listcate3= productCategoryService.selectByParents(0L,1l,null);
        return listcate3;
    }

}
