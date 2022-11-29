package com.tongfu.service;


import com.tongfu.entity.Invoice;

import java.util.Map;


public interface InvoiceService {


    int insertInvoice(Invoice invoice);

    Invoice selectByMember(Map<String,Object> map);

    int updateByPrimaryKeySelective(Invoice invoice);

}
