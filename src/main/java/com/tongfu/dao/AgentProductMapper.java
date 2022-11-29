package com.tongfu.dao;

import com.tongfu.entity.AgentProduct;

import java.util.List;
import java.util.Map;

public interface AgentProductMapper {
    int insert(AgentProduct record);

    int insertSelective(AgentProduct record);

    List<Map> getAgentProducts(Map query_map);
}