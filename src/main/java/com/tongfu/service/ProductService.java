package com.tongfu.service;

import com.tongfu.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Map<String,Object>> selectByCategory(Map<String,Object> map);

    Product selectProductById(Long id);

    int updateProductHits(Product record,String ip);

    int updateProduct(Product product);

    List<Map<String,Object>> selectByCompanyCategory(Map<String,Object> map);//根据公司id和父类id查询产品

    int selectCountByCompanyCategory(Map<String,Object> map);//根据公司id和父类id查询产品数量

    List<Map<String,Object>> selectByKeywords(Map<String,Object> map);//根据产品名称、品牌名称、产品关键字、父类别id查询

    List<Product> selectTopByHits(Integer topnum);//查询排前几个点击量的产品

    List<Map> getCompanyProducts(Map map);

    List<Map> getOrderProducts(Map map);

    Integer getCountCompanys(Map query_map);

    List<Product> getProducts(Map map);
}
