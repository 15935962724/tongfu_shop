package com.tongfu.dao;

import com.tongfu.entity.Purchase;

import javax.print.attribute.standard.PrinterURI;
import java.util.List;
import java.util.Map;

public interface PurchaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

    List<Purchase> selectPurchase(Map query_map);

    List<Purchase> selectFangshi(Map query_map);

    List<Map<String,Object>> selectGuige(Map<String,Object> map);

    List<Map> getPurchases(Map query_map);
}