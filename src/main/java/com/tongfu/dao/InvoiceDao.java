package com.tongfu.dao;

import com.tongfu.entity.Invoice;

import java.util.Map;

public interface InvoiceDao {
    int deleteByPrimaryKey(Long id);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Long id);

    Invoice selectByMember(Map<String,Object> map);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);
}