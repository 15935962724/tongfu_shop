package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.ExhibitionDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Exhibition;
import com.tongfu.service.AdService;
import com.tongfu.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ExhibitionServiceServiceImpl implements ExhibitionService {

	@Autowired
	private ExhibitionDao exhibitionDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Exhibition record) {
		return 0;
	}

	@Override
	public int insertSelective(Exhibition record) {
		return 0;
	}

	@Override
	public Exhibition selectByPrimaryKey(Long id) {
		return exhibitionDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Exhibition record) {
		return exhibitionDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Exhibition record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Exhibition record) {
		return 0;
	}

	@Override
	public List<Exhibition> getAll(Map<String, Object> query_map) {
		return exhibitionDao.getAll(query_map);
	}
}
