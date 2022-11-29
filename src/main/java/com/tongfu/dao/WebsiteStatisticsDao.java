package com.tongfu.dao;

import com.tongfu.entity.WebsiteStatistics;

public interface WebsiteStatisticsDao {
    int insert(WebsiteStatistics record);

    int insertSelective(WebsiteStatistics record);
}