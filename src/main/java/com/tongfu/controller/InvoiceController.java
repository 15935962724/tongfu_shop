package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
@RequestMapping("/invoice")
public class InvoiceController {


	@Autowired
	private InvoiceService invoiceService;

	@Autowired
    private MemberService memberService;


    @Value("${path-url}")
    private String pathUrl;


    //编辑我的发票信息
    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody JSONObject jsonObject){
        Member member = memberService.getCurrent();
        Invoice invoice = JSON.parseObject(jsonObject.toJSONString(), Invoice.class);
        if (invoice.getId()==null){
            invoice.setCreateDate(new Date());
            invoice.setModifyDate(new Date());
            invoice.setIsDelete(false);
            invoice.setMember(member.getId().toString());
            invoiceService.insertInvoice(invoice);
        }else {
            invoiceService.updateByPrimaryKeySelective(invoice);
        }
        return ResultUtil.success();

    }







}
