package com.tongfu.service.impl;


import com.tongfu.dao.ReturnsMapper;
import com.tongfu.entity.Refunds;
import com.tongfu.entity.Returns;
import com.tongfu.service.RefundsService;
import com.tongfu.service.ReturnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReturnsServiceImpl implements ReturnsService {

	@Autowired
	private ReturnsMapper returnsMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Returns record) {
		return 0;
	}

	@Override
	public int insertSelective(Returns record) {
		return returnsMapper.insertSelective(record);
	}

	@Override
	public Returns selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Returns record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Returns record) {
		return 0;
	}
}
