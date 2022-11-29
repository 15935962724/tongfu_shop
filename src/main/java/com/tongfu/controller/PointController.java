package com.tongfu.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.FileUpload;
import com.tongfu.entity.CasesWithBLOBs;
import com.tongfu.entity.Member;
import com.tongfu.entity.Order;
import com.tongfu.entity.OrderItem;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/point")
public class PointController {

    @Autowired
    MemberPointLogService memberPointLogService;//积分记录

    @Autowired
    MemberService memberService;


    @RequestMapping("/list")
    public String list(Model model,Integer seltime,Integer selshouzhi){

        Member member = memberService.getCurrent();
        seltime = seltime==null?3:seltime;

        List<Map<String,Object>> list = memberPointLogService.selectPoint(member.getId(),seltime,selshouzhi);
        model.addAttribute("list",list);
        model.addAttribute("seltime",seltime);
        model.addAttribute("selshouzhi",selshouzhi);
        return "/point/list";
    }

    /**
     * 我的积分医生申请
     * @param model
     * @return
     */
    @RequestMapping("/adddoctor")
    public String adddoctor(Model model){
        Member member = memberService.getCurrent();
        model.addAttribute("member",member);
        model.addAttribute("activeNone","point");//打开左侧导航
        model.addAttribute("active",7);//选中字菜单
        return "/point/doctorApply";
    }

    /**
     * 组委会申请
     * @param model
     * @return
     */
    @RequestMapping("/addcommittee")
    public String addcommittee(Model model){
        Member member = memberService.getCurrent();
        model.addAttribute("member",member);
        model.addAttribute("activeNone","point");//打开左侧导航
        model.addAttribute("active",8);//选中字菜单
        return "/point/committeeApply";
    }


}
