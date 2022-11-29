package com.tongfu.dao;

import com.tongfu.entity.ProductSoftwarePackage;

import java.util.List;
import java.util.Map;

public interface ProductSoftwarePackageMapper {
    int insert(ProductSoftwarePackage record);

    int insertSelective(ProductSoftwarePackage record);

    List<ProductSoftwarePackage> getAll(Map query_map);

}