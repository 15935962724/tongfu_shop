package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.LearningDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Learning;
import com.tongfu.service.AdService;
import com.tongfu.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class LearningServiceImpl implements LearningService {

	@Autowired
	private LearningDao learningDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Learning record) {
		return 0;
	}

	@Override
	public int insertSelective(Learning record) {
		return 0;
	}

	@Override
	public Learning selectByPrimaryKey(Long id) {
		return learningDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Learning record) {
		return learningDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Learning record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Learning record) {
		return 0;
	}

	@Override
	public List<Learning> getAll(Map<String, Object> query_map) {
		return learningDao.getAll(query_map);
	}
}
