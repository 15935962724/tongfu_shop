package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.ProductMealDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.ProductMeal;
import com.tongfu.service.AdService;
import com.tongfu.service.ProductMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductMealServiceImpl implements ProductMealService {

	@Autowired
	private ProductMealDao productMealDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(ProductMeal record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductMeal record) {
		return 0;
	}

	@Override
	public ProductMeal selectByPrimaryKey(Long id) {
		return productMealDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductMeal record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ProductMeal record) {
		return 0;
	}

	@Override
	public List<ProductMeal> getAll(Map<String, Object> query_map) {
		return productMealDao.getAll(query_map);
	}
}
