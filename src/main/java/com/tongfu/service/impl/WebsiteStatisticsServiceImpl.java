package com.tongfu.service.impl;


import com.tongfu.dao.AdminDao;
import com.tongfu.dao.WebsiteStatisticsDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.WebsiteStatistics;
import com.tongfu.service.AdminService;
import com.tongfu.service.WebsiteStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class WebsiteStatisticsServiceImpl implements WebsiteStatisticsService {

	@Autowired
	WebsiteStatisticsDao websiteStatisticsDao;

	@Override
	public int insert(WebsiteStatistics record) {
		return 0;
	}

	@Override
	public int insertSelective(WebsiteStatistics record) {
		return websiteStatisticsDao.insertSelective(record);
	}
}
