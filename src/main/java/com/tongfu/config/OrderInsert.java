package com.tongfu.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.entity.Order;
import com.tongfu.entity.OrderItem;
import com.tongfu.entity.OrderLog;
import com.tongfu.service.MemberService;
import com.tongfu.service.OrderItemService;
import com.tongfu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Configuration
public class OrderInsert {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;


    @Autowired
    private MemberService memberService;


    @Scheduled(cron = "0 0 10,14,16 * * ?")
    private void process(){


    }

}