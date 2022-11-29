package com.tongfu.dao;

import com.tongfu.entity.Journalism;

import java.util.List;
import java.util.Map;

public interface JournalismDao {
    int deleteByPrimaryKey(Long id);

    int insert(Journalism record);

    int insertSelective(Journalism record);

    Journalism selectByPrimaryKey(Long id);

    List<Map<String,Object>> selectByCompanyKeyword(Map<String ,Object> map);

    //List<Map<String,Object>> selectByKeyword(Map<String,Object> map);//根据关键字和公司查询

    int updateByPrimaryKeySelective(Journalism record);

    int updateByPrimaryKeyWithBLOBs(Journalism record);

    int updateByPrimaryKey(Journalism record);

    List<Map> getJournalisms(Map query_map);

    List<Map> getJournalismsLearningMediaExhibition(Map query_map);

    Integer getTableNameCount(Map query_map);

    Map getArticleView(Map query_map);
}