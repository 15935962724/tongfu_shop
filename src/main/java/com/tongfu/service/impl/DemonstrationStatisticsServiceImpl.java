package com.tongfu.service.impl;

import com.tongfu.dao.DemonstrationStatisticsDao;
import com.tongfu.entity.DemonstrationStatistics;
import com.tongfu.service.DemonstrationStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemonstrationStatisticsServiceImpl implements DemonstrationStatisticsService {

    @Autowired
    DemonstrationStatisticsDao demonstrationStatisticsDao;


    @Override
    public int insertDemonstration(DemonstrationStatistics demonstrationStatistics) {
        return demonstrationStatisticsDao.insertSelective(demonstrationStatistics);
    }
}
