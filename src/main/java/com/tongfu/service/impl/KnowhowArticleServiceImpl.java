package com.tongfu.service.impl;

import com.tongfu.dao.KnowhowArticleDao;
import com.tongfu.entity.Aboutus;
import com.tongfu.entity.KnowhowArticle;
import com.tongfu.service.AboutusService;
import com.tongfu.service.KnowhowArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class KnowhowArticleServiceImpl implements KnowhowArticleService {

	@Autowired
	private KnowhowArticleDao knowhowArticleDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(KnowhowArticle record) {
		return 0;
	}

	@Override
	public int insertSelective(KnowhowArticle record) {
		return 0;
	}

	@Override
	public KnowhowArticle selectByPrimaryKey(Long id) {
		return knowhowArticleDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(KnowhowArticle record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(KnowhowArticle record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(KnowhowArticle record) {
		return 0;
	}

	@Override
	public List<KnowhowArticle> getAll(Map query_map) {
		return knowhowArticleDao.getAll(query_map);
	}
}
