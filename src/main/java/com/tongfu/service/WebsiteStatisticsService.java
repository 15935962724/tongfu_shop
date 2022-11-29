package com.tongfu.service;

import com.tongfu.entity.WebsiteStatistics;

public interface WebsiteStatisticsService {
    int insert(WebsiteStatistics record);

    int insertSelective(WebsiteStatistics record);
}