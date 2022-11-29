package com.tongfu.service;

import com.tongfu.entity.ArticleStatistics;

public interface ArticleStatisticsService {

    int insert(ArticleStatistics record);

    int insertSelective(ArticleStatistics record);

}