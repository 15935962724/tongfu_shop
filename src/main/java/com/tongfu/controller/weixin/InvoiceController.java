package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller("wexininvoice")
@RequestMapping("/weixin/invoice")
public class InvoiceController {


	@Autowired
	private InvoiceService invoiceService;

	@Autowired
    private AreaService areaService;

	@Autowired
    private MemberService memberService;



    //我的发票信息
    @RequestMapping("/myInvoice")
    @ResponseBody
    public String myInvoice(){
        JSONObject returnJson = new JSONObject();
        Member member = memberService.getCurrent();
        Map query_map = new HashMap();
        query_map.put("member",member.getId());
        Invoice invoice = invoiceService.selectByMember(query_map);
        if(invoice!=null){
            Area area = areaService.selectById(invoice.getAreaId());
            returnJson.put("area",area);
        }
        returnJson.put("invoice",invoice);
        return ResultUtil.success(returnJson);
    }



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
