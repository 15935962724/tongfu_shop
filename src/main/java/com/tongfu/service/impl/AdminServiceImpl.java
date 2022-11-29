package com.tongfu.service.impl;


import com.tongfu.dao.AdminDao;
import com.tongfu.entity.Admin;
import com.tongfu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Admin record) {
		return 0;
	}

	@Override
	public int insertSelective(Admin record) {
		return 0;
	}

	@Override
	public Admin selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public List<Admin> selectAdmin(Map<String, Object> map) {
		return adminDao.selectAdmin(map);
	}

	@Override
	public int updateByPrimaryKeySelective(Admin record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Admin record) {
		return 0;
	}

	@Override
	public Admin getSystemCompanyAdmin(Map query_map) {
		return adminDao.getSystemCompanyAdmin(query_map);
	}
}
