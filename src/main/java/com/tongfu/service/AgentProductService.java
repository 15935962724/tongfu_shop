package com.tongfu.service;

import com.tongfu.entity.AgentProduct;
import com.tongfu.entity.Area;

import java.util.List;
import java.util.Map;

public interface AgentProductService {

    int insert(AgentProduct record);

    int insertSelective(AgentProduct record);

    List<Map> getAgentProducts(Map query_map);

}
