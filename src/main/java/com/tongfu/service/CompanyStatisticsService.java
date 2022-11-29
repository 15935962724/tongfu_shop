package com.tongfu.service;

import com.tongfu.entity.CompanyStatistics;

public interface CompanyStatisticsService {
    int insert(CompanyStatistics record);

    int insertSelective(CompanyStatistics record);
}