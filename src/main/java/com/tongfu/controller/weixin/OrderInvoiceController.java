package com.tongfu.controller.weixin;

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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller("weixinOrderInvoice")
@RequestMapping("/weixin/orderInvoice")
public class OrderInvoiceController {

    @Autowired
    private PlatformTransactionManager txManager;

	@Autowired
    private OrderInvoiceService orderInvoiceService;

	@Autowired
    private OrderService orderService;

	@Autowired
    private MemberService memberService;

	@Autowired
    private OrderItemService orderItemService;

    @Value("${path-url}")
    private String pathUrl;




    //提交发票
    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody JSONObject jsonObject){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            Member member = memberService.getCurrent();
            Order order = orderService.selectById(jsonObject.getLong("orderId"));
            OrderInvoice orderInvoice = JSON.parseObject(jsonObject.toJSONString(), OrderInvoice.class);
            orderInvoice.setModifyDate(new Date());
            orderInvoice.setCreateDate(new Date());
            orderInvoice.setIsDelete(false);
            orderInvoice.setIsMakeInvoice(1L);
            orderInvoice.setMember(member.getId().toString());
            orderInvoice.setAmount(order.getAmountPaid());
            int i = orderInvoiceService.insertSelective(orderInvoice);
            if (i>0){
                order.setIsMakeInvoice(true);
                orderService.updateOrder(order);
            }
            txManager.commit(status);
            return  ResultUtil.success();
        }catch (Exception e){
            System.out.println("事务回滚了");
            //强制手动事务回滚
            txManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ResultUtil.error("操作失败");
        }




    }



}
