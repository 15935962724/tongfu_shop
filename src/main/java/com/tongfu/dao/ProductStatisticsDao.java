package com.tongfu.dao;

import com.tongfu.entity.ProductStatistics;

public interface ProductStatisticsDao {
    int insert(ProductStatistics record);

    int insertSelective(ProductStatistics record);
}