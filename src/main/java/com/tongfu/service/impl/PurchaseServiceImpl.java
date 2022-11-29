package com.tongfu.service.impl;

import com.tongfu.dao.PurchaseDao;
import com.tongfu.entity.Purchase;
import com.tongfu.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    public List<Purchase> selectPurchase(Map query_map) {
        return purchaseDao.selectPurchase( query_map);
    }


    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Purchase record) {
        return 0;
    }

    @Override
    public int insertSelective(Purchase record) {
        return 0;
    }

    @Override
    public List<Map<String,Object>> selectGuige(Map<String, Object> map) {
        //查询购买方式下的购买规格
        return purchaseDao.selectGuige(map);
    }

    @Override
    public Purchase selectByPrimaryKey(Long id) {
        return purchaseDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Purchase record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Purchase record) {
        return 0;
    }

    @Override
    public List<Purchase> selectFangshi(Map query_map) {
        return purchaseDao.selectFangshi(query_map);
    }

    @Override
    public List<Map> getPurchases(Map query_map) {
        return purchaseDao.getPurchases(query_map);
    }
}
