package com.tongfu.service;

import com.tongfu.entity.ProductCategory;

import java.util.List;
import java.util.Map;

public interface ProductCategoryService {

    List<ProductCategory> selectByParents(Long parent,Long type,Long position);

    List<Map<String,Object>> selectByParents2(Long parent, Long type, Long position);

    List<Map<String,Object>> selectParent(Long id);

    ProductCategory selectByPrimaryKey(Long id);

    List<ProductCategory> getParentProductCategory(Map<String,Object>query_map);

    ProductCategory getParentProductCategory(Long id);

    Map getParentProductCategoryName(Long id);

//


}
