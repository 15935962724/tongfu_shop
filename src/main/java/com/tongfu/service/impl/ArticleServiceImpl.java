package com.tongfu.service.impl;

import com.tongfu.dao.ArticleDao;
import com.tongfu.entity.Article;
import com.tongfu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleDao articleDao;

	@Override
	public List<Map> selectArticle(Map<String, Object> map) {
		return articleDao.selectArticle(map);
	}

	@Override
	public Article selectByPrimaryKey(Long id) {
		return articleDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Article record) {
		return 0;
	}

	@Override
	public int insertSelective(Article record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Article record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Article record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Article record) {
		return 0;
	}

	@Override
	public List<Map> getArticle(Map query_map) {
		return articleDao.getArticle(query_map);
	}

	@Override
	public List<Map> getSearchArticle(Map query_map) {
		return articleDao.getSearchArticle(query_map);
	}

	@Override
	public List<Map> getIndexArticle(Map query_map) {
		return articleDao.getIndexArticle(query_map);
	}
}
