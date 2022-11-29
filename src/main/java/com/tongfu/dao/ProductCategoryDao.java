package com.tongfu.dao;

import com.tongfu.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductCategoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Long id);
//@Param("parent") Long parent,@Param("type") Long type,@Param("position") Long position
    List<ProductCategory> selectByParents(Map<String,Object> map);

    List<Map<String,Object>> selectByParents2(Map<String,Object> map);

    List<Map<String,Object>> selectParent(@Param("id") Long id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    Map getParentProductCategoryName(Long id);




}