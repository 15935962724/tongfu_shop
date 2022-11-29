package com.tongfu.service;

import com.tongfu.entity.OrderInvoice;


public interface OrderInvoiceService {


	OrderInvoice selectByOrderId(Long orderid);

	int deleteByPrimaryKey(Long id);

	int insert(OrderInvoice record);

	int insertSelective(OrderInvoice record);

	OrderInvoice selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderInvoice record);

	int updateByPrimaryKey(OrderInvoice record);

	
}