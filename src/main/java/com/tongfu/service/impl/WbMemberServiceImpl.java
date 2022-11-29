package com.tongfu.service.impl;

import com.tongfu.dao.AboutusDao;
import com.tongfu.dao.WbMemberDao;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.WbMember;
import com.tongfu.service.AboutusService;
import com.tongfu.service.WbMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class WbMemberServiceImpl implements WbMemberService {

	@Autowired
	private WbMemberDao wbMemberDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(WbMember record) {
		return 0;
	}

	@Override
	public int insertSelective(WbMember record) {
		return wbMemberDao.insertSelective(record);
	}

	@Override
	public WbMember selectByPrimaryKey(Long id) {
		return wbMemberDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(WbMember record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(WbMember record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(WbMember record) {
		return 0;
	}
}
