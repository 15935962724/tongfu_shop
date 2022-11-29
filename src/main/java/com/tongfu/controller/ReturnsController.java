package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/returns")
public class ReturnsController {

    @Autowired
    private OrderService orderService;//订单

    @Autowired
    private MemberService memberService;//用户

    @Autowired
    private OrderLogService orderLogService;//订单日志

    @Autowired
    private ReturnsService returnsService;

    @Autowired
    private ReturnsItemService returnsItemService;

    @Autowired
    private PlatformTransactionManager txManager;


    /**
     * 取消订单
     * @param
     * @return
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public String updateOrder(@RequestBody JSONObject jsonObject ){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            Member member = memberService.getCurrent();
            Order order = orderService.selectById(jsonObject.getLong("id"));
            order.setOrderStatus(3);
            orderService.updateOrder(order);
            OrderLog orderLog = new OrderLog();
            orderLog.setCreateDate(new Date());
            orderLog.setModifyDate(new Date());
            orderLog.setContent("取消订单"+jsonObject.getString("message"));//操作内容
            orderLog.setOperator(member.getUsername());//前台用户名称(账号）
            orderLog.setType(1);//用户类型1前台用户
            orderLog.setOrders(order.getId());//订单表id
            orderLog.setIsDeleted(false);
            orderLog.setOperatorName(member.getName());//操作人姓名
            orderLogService.insertOrderLog(orderLog);//插入订单日志
            txManager.commit(status);
            return ResultUtil.success();
        }catch (Exception e){
            System.out.println("事务回滚了");
            //强制手动事务回滚
            txManager.rollback(status);
            return ResultUtil.error("取消失败");
        }
    }

    /**
     * 取消订单
     * @param
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody JSONObject jsonObject ){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            System.out.println(jsonObject);
            Member member = memberService.getCurrent();
            Returns returns = JSON.parseObject(jsonObject.toString(),Returns.class);
            String sn = "THD"+System.currentTimeMillis();
            returns.setSn(sn);
            returns.setCreateDate(new Date());
            returns.setModifyDate(new Date());
            returns.setOrders(jsonObject.getLong("orders"));
            returnsService.insertSelective(returns);
            JSONArray returns_items = jsonObject.getJSONArray("returns_item");
            for (Object returns_item : returns_items) {
                JSONObject json = JSONObject.parseObject(returns_item.toString());
                ReturnsItem returnsItem = JSON.parseObject(json.toString(),ReturnsItem.class);
                returnsItem.setReturns(returns.getId());
                returnsItemService.insertSelective(returnsItem);
            }
            OrderLog orderLog = new OrderLog();
            orderLog.setCreateDate(new Date());
            orderLog.setModifyDate(new Date());
            orderLog.setContent("申请退货");//操作内容
            orderLog.setOperator(member.getUsername());//前台用户名称(账号）
            orderLog.setType(1);//用户类型1前台用户
            orderLog.setOrders(jsonObject.getLong("orders"));//订单表id
            orderLog.setIsDeleted(false);
            orderLog.setOperatorName(member.getName());//操作人姓名
            orderLogService.insertOrderLog(orderLog);//插入订单日志
            txManager.commit(status);
            return ResultUtil.success();
        }catch (Exception e){
            System.out.println("事务回滚了");
            //强制手动事务回滚
            txManager.rollback(status);
            return ResultUtil.error("取消失败");
        }
    }




}
