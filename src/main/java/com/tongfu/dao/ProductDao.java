package com.tongfu.dao;

import com.tongfu.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);

    List<Map<String,Object>> selectByCategory(Map<String,Object> map);

    List<Map<String,Object>> selectByCompanyCategory(Map<String,Object> map);//根据公司id和父类id查询产品

    int selectCountByCompanyCategory(Map<String,Object> map);//根据公司id和父类id查询产品个数

    List<Map<String,Object>> selectByKeywords(Map<String,Object> map);//根据商品名称、关键字、品牌查询

    List<Product> selectTopByHits(@Param("topnum") Integer topnum);

    int updateProductHits(Product record);

    int updateProduct(Product product);

    List<Map> getCompanyProducts(Map map);

    List<Map> getOrderProducts(Map map);

    Integer getCountCompanys(Map query_map);

    List<Product> getProducts(Map query_map);
}