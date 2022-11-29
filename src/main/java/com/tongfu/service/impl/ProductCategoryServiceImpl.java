package com.tongfu.service.impl;

import com.tongfu.dao.ProductCategoryDao;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> selectByParents(Long parent, Long type, Long position) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("parent",parent);
        map.put("type",type);
        map.put("position",position);
        return productCategoryDao.selectByParents(map);
    }

    @Override
    public List<Map<String, Object>> selectByParents2(Long parent, Long type, Long position) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("parent",parent);
        map.put("type",type);
        map.put("position",position);
        return productCategoryDao.selectByParents2(map);
    }

    @Override
    public List<Map<String, Object>> selectParent(Long id) {
        return productCategoryDao.selectParent(id);
    }

    @Override
    public ProductCategory selectByPrimaryKey(Long id) {
        return productCategoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductCategory> getParentProductCategory(Map<String, Object> query_map) {
        return productCategoryDao.selectByParents(query_map);
    }

    @Override
    public ProductCategory getParentProductCategory(Long id) {
        ProductCategory productCategory = productCategoryDao.selectByPrimaryKey(id);
        if (productCategory.getParent()==null){
             return productCategory;
        }
        return getParentProductCategory(productCategory.getParent());
    }

    @Override
    public Map getParentProductCategoryName(Long id) {
        return productCategoryDao.getParentProductCategoryName(id);
    }

}
