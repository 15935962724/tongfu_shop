package com.tongfu.service.impl;


import com.tongfu.dao.AdminDao;
import com.tongfu.dao.ReturnsItemMapper;
import com.tongfu.entity.Admin;
import com.tongfu.entity.ReturnsItem;
import com.tongfu.service.AdminService;
import com.tongfu.service.ReturnsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ReturnsItemServiceImpl implements ReturnsItemService {

	@Autowired
	private ReturnsItemMapper returnsItemMapper;

	@Override
	public int insert(ReturnsItem record) {
		return 0;
	}

	@Override
	public int insertSelective(ReturnsItem record) {
		return returnsItemMapper.insertSelective(record);
	}
}
