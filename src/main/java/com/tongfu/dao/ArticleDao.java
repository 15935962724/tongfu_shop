package com.tongfu.dao;

import com.tongfu.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    List<Map> selectArticle(Map<String,Object> map);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<Map> getArticle(Map query_map);

    List<Map> getSearchArticle(Map query_map);

    List<Map> getIndexArticle(Map query_map);


}