package com.tongfu.service.impl;

import com.tongfu.dao.ProductSoftwarePackageMapper;
import com.tongfu.entity.ProductSoftwarePackage;
import com.tongfu.service.ProductSoftwarePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductSoftwarePackageServiceImpl implements ProductSoftwarePackageService {


	@Autowired
	private ProductSoftwarePackageMapper productSoftwarePackageMapper;

	@Override
	public int insert(ProductSoftwarePackage record) {
		return 0;
	}

	@Override
	public int insertSelective(ProductSoftwarePackage record) {
		return 0;
	}

	@Override
	public List<ProductSoftwarePackage> getAll(Map query_map) {
		return productSoftwarePackageMapper.getAll(query_map);
	}
}
