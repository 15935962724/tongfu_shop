package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.entity.Ad;
import com.tongfu.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdServiceImpl implements AdService {


	@Autowired
	AdDao adDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Ad record) {
		return 0;
	}

	@Override
	public int insertSelective(Ad record) {
		return 0;
	}

	@Override
	public Ad selectByPrimaryKey(Long id) {
		return adDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Ad record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Ad record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Ad record) {
		return 0;
	}

	@Override
	public List<Ad> listAd(Map<String, Object> map) {
		return adDao.selectAd(map);
	}
}
