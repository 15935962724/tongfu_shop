package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.ShippingItemMapper;
import com.tongfu.entity.Ad;
import com.tongfu.entity.ShippingItem;
import com.tongfu.service.AdService;
import com.tongfu.service.ShippingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ShippingItemServiceImpl implements ShippingItemService {

	@Autowired
	private ShippingItemMapper shippingItemMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(ShippingItem record) {
		return 0;
	}

	@Override
	public int insertSelective(ShippingItem record) {
		return shippingItemMapper.insertSelective(record);
	}

	@Override
	public ShippingItem selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ShippingItem record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ShippingItem record) {
		return 0;
	}

	@Override
	public int insertSelectiveMap(Map<String, Object> map) {
		return shippingItemMapper.insertSelectiveMap(map);
	}
}
