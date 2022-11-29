package com.tongfu.dao;

import com.tongfu.entity.ArticleStatistics;

public interface ArticleStatisticsMapper {
    int insert(ArticleStatistics record);

    int insertSelective(ArticleStatistics record);
}