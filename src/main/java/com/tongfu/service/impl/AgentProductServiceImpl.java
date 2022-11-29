package com.tongfu.service.impl;


import com.tongfu.dao.AdminDao;
import com.tongfu.dao.AgentProductMapper;
import com.tongfu.entity.Admin;
import com.tongfu.entity.AgentProduct;
import com.tongfu.service.AdminService;
import com.tongfu.service.AgentProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AgentProductServiceImpl implements AgentProductService {

	@Autowired
	private AgentProductMapper agentProductMapper;


	@Override
	public int insert(AgentProduct record) {
		return 0;
	}

	@Override
	public int insertSelective(AgentProduct record) {
		return 0;
	}

	@Override
	public List<Map> getAgentProducts(Map query_map) {
		return agentProductMapper.getAgentProducts(query_map);
	}
}
