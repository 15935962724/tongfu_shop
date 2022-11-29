package com.tongfu.service.impl;

import com.tongfu.dao.InvoiceDao;
import com.tongfu.entity.Invoice;
import com.tongfu.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;


    @Override
    public int insertInvoice(Invoice invoice) {
        return invoiceDao.insert(invoice);
    }

    @Override
    public Invoice selectByMember(Map<String, Object> map) {
        return invoiceDao.selectByMember(map);
    }

    @Override
    public int updateByPrimaryKeySelective(Invoice invoice) {
        return invoiceDao.updateByPrimaryKeySelective(invoice);
    }
}
