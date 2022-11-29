package com.tongfu.service.impl;


import com.tongfu.dao.OrderInvoiceDao;
import com.tongfu.entity.OrderInvoice;
import com.tongfu.service.OrderInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class OrderInvoiceServiceImpl implements OrderInvoiceService {

	@Autowired
	OrderInvoiceDao orderInvoiceDao;

	@Override
	public OrderInvoice selectByOrderId(Long orderid) {
		return orderInvoiceDao.selectByOrderId(orderid);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(OrderInvoice record) {
		return 0;
	}

	@Override
	public int insertSelective(OrderInvoice record) {
		return orderInvoiceDao.insertSelective(record);
	}

	@Override
	public OrderInvoice selectByPrimaryKey(Long id) {
		return orderInvoiceDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderInvoice record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(OrderInvoice record) {
		return 0;
	}
}
