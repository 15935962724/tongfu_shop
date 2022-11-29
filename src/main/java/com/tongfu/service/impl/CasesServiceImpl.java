package com.tongfu.service.impl;


import com.tongfu.dao.CasesDao;
import com.tongfu.entity.Cases;
import com.tongfu.entity.CasesWithBLOBs;
import com.tongfu.service.CasesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CasesServiceImpl implements CasesService {

	@Autowired
	CasesDao casesDao;


	@Override
	public int insertSelective(CasesWithBLOBs cases) {
		return casesDao.insertSelective(cases);
	}
}
