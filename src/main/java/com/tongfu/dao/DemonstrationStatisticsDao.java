package com.tongfu.dao;

import com.tongfu.entity.DemonstrationStatistics;

public interface DemonstrationStatisticsDao {

    int deleteByPrimaryKey(Long id);

    int insert(DemonstrationStatistics record);

    int insertSelective(DemonstrationStatistics record);

    DemonstrationStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DemonstrationStatistics record);

    int updateByPrimaryKey(DemonstrationStatistics record);
}