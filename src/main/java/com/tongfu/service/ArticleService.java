package com.tongfu.service;

import com.tongfu.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

	List<Map> selectArticle(Map<String,Object> map);

	Article selectByPrimaryKey(Long id);

	int deleteByPrimaryKey(Long id);

	int insert(Article record);

	int insertSelective(Article record);

	int updateByPrimaryKeySelective(Article record);

	int updateByPrimaryKeyWithBLOBs(Article record);

	int updateByPrimaryKey(Article record);

	List<Map> getArticle(Map query_map);

	List<Map> getSearchArticle(Map query_map);

	List<Map> getIndexArticle(Map query_map);



}