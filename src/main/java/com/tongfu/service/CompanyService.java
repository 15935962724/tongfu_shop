package com.tongfu.service;

import com.tongfu.entity.Company;
import com.tongfu.entity.Product;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    Company selectCompanyById(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKeyWithBLOBs(Company record);

    int updateByPrimaryKey(Company record);

    List<Company> companys(Map query_map);


}
