package com.tongfu.service;


import com.tongfu.entity.Admin;

import java.util.List;
import java.util.Map;

public interface AdminService {

	int deleteByPrimaryKey(Long id);

	int insert(Admin record);

	int insertSelective(Admin record);

	Admin selectByPrimaryKey(Long id);

	List<Admin> selectAdmin(Map<String,Object> map);

	int updateByPrimaryKeySelective(Admin record);

	int updateByPrimaryKey(Admin record);

	Admin getSystemCompanyAdmin(Map query_map);

	
}