package com.tongfu.service.impl;


import com.tongfu.entity.Refunds;
import com.tongfu.entity.ReturnsItem;
import com.tongfu.service.RefundsService;
import com.tongfu.service.ReturnsItemService;
import org.springframework.stereotype.Service;


@Service
public class RefundsServiceImpl implements RefundsService {

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Refunds record) {
		return 0;
	}

	@Override
	public int insertSelective(Refunds record) {
		return 0;
	}

	@Override
	public Refunds selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Refunds record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Refunds record) {
		return 0;
	}
}
