package com.tongfu.dao;

import com.tongfu.entity.OrderInvoice;

public interface OrderInvoiceDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInvoice record);

    int insertSelective(OrderInvoice record);

    OrderInvoice selectByPrimaryKey(Long id);

    OrderInvoice selectByOrderId(Long orderid);

    int updateByPrimaryKeySelective(OrderInvoice record);

    int updateByPrimaryKey(OrderInvoice record);
}