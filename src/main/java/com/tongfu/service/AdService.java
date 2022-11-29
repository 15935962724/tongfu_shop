package com.tongfu.service;

import com.tongfu.entity.Ad;
import com.tongfu.entity.Dog;

import java.util.List;
import java.util.Map;

public interface AdService {

	int deleteByPrimaryKey(Long id);

	int insert(Ad record);

	int insertSelective(Ad record);

	Ad selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Ad record);

	int updateByPrimaryKeyWithBLOBs(Ad record);

	int updateByPrimaryKey(Ad record);

	List<Ad> listAd(Map<String,Object> map);
	
	
}