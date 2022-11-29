package com.tongfu.dao;

import com.tongfu.entity.CompanyStatistics;

public interface CompanyStatisticsMapper {
    int insert(CompanyStatistics record);

    int insertSelective(CompanyStatistics record);
}