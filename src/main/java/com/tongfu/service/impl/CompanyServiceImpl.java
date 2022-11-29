package com.tongfu.service.impl;

import com.tongfu.dao.CompanyDao;
import com.tongfu.entity.Company;
import com.tongfu.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDao companyDao;


    @Override
    public Company selectCompanyById(Long id) {
        return companyDao.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Company record) {
        return 0;
    }

    @Override
    public int insertSelective(Company record) {
        return 0;
    }

    @Override
    public Company selectByPrimaryKey(Long id) {
        return companyDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Company record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Company record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Company record) {
        return 0;
    }

    @Override
    public List<Company> companys(Map query_map) {
        return companyDao.companys(query_map);
    }
}
