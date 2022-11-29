package com.tongfu.service.impl;


import com.tongfu.dao.AdminDao;
import com.tongfu.dao.LearningSignupMapper;
import com.tongfu.entity.Admin;
import com.tongfu.entity.LearningSignup;
import com.tongfu.service.AdminService;
import com.tongfu.service.LearningSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class LearningSignupServiceImpl implements LearningSignupService {

	@Autowired
	private LearningSignupMapper learningSignupMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(LearningSignup record) {
		return 0;
	}

	@Override
	public int insertSelective(LearningSignup record) {
		return learningSignupMapper.insertSelective(record);
	}

	@Override
	public LearningSignup selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(LearningSignup record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(LearningSignup record) {
		return 0;
	}
}
