package com.tongfu.service.impl;


import com.tongfu.dao.ShippingMapper;
import com.tongfu.entity.Shipping;
import com.tongfu.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private ShippingMapper shippingMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Shipping record) {
		return 0;
	}

	@Override
	public int insertSelective(Shipping record) {
		return shippingMapper.insertSelective(record);
	}

	@Override
	public Shipping selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Shipping record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Shipping record) {
		return 0;
	}
}
