package com.tongfu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Area;
import com.tongfu.entity.Member;
import com.tongfu.entity.Receiver;
import com.tongfu.service.AreaService;
import com.tongfu.service.MemberService;
import com.tongfu.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/receiver")
public class ReceiverController {


	@Autowired
	private ReceiverService receiverService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private MemberService memberService;

    @RequestMapping("/addReceiver")
    @ResponseBody

    public int addReceiver(String receiver,Long sheng,Long shi,Long area,String address,String mobile,String email,Long userId,long receiverId,boolean isdefault){
        Area area1=new Area();
        if(area!=null){
            area1=areaService.selectById(area);
        }else if(shi!=null){
            area1=areaService.selectById(shi);
        }else if(sheng!=null){
            area1=areaService.selectById(sheng);
        }
        int result=0;
        if(receiverId==0l){
            //新增
            Receiver receiver1=new Receiver();
            receiver1.setConsignee(receiver);
            receiver1.setCreateDate(new Date());
            receiver1.setModifyDate(new Date());
            receiver1.setAddress(address);
            receiver1.setAreaName(area1.getFullName());
            receiver1.setIsDefault(false);
            receiver1.setPhone(mobile);
            receiver1.setArea(area);
            receiver1.setMember(userId);
            receiver1.setIsDeleted(false);
            receiver1.setZipCode("");
            receiver1.setEmail(email);

            result = receiverService.insertReceiver(receiver1);
        }else{
            //修改
            Receiver receiver1 = receiverService.selectById(receiverId);
            receiver1.setConsignee(receiver);
            receiver1.setCreateDate(new Date());
            receiver1.setModifyDate(new Date());
            receiver1.setAddress(address);
            receiver1.setAreaName(area1.getFullName());
            receiver1.setIsDefault(false);
            receiver1.setPhone(mobile);
            receiver1.setArea(area);
            receiver1.setMember(userId);
            receiver1.setIsDeleted(false);
            receiver1.setZipCode("");
            receiver1.setEmail(email);

             result = receiverService.updateReceiver(receiver1);
        }

        return result;
    }



    /**
     * 新增收货地址
     * @param params
     * @return
     */
    @PostMapping("/saveReceiver")
    @ResponseBody
    public String saveReceiver(@RequestBody String params){
        Member member = memberService.getCurrent();

        JSONObject jsonObject = JSONObject.parseObject(params);
        Receiver receiver = JSONObject.parseObject(params,Receiver.class);
        receiver.setCreateDate(new Date());
        receiver.setModifyDate(new Date());
        receiver.setMember(member.getId());
        receiver.setIsDefault(false);
        receiver.setIsDeleted(false);

        Area area = areaService.selectById(receiver.getArea());
        receiver.setAreaName(area.getFullName());
        Integer saveReceiverCount = receiverService.insertReceiver(receiver);
        if (saveReceiverCount>0){
            return  ResultUtil.success("操作成功");
        }
        return ResultUtil.error("操作失败");
    }

    @PostMapping("/updateReceiver")
    @ResponseBody
    public String updateReceiver(@RequestBody String params){
        Member member = memberService.getCurrent();

        JSONObject jsonObject = JSONObject.parseObject(params);
        Receiver receiver = JSONObject.parseObject(params,Receiver.class);
        Area area = areaService.selectById(receiver.getArea());
        receiver.setAreaName(area.getFullName());
        Integer saveReceiverCount = receiverService.updateReceiver(receiver);
        if (saveReceiverCount>0){
            return  ResultUtil.success("操作成功");
        }
        return ResultUtil.error("操作失败");
//
    }




    @RequestMapping("/addshReceiver")
    @ResponseBody

    public int addshReceiver(String consignee,Long sheng,Long shi,Long area,String address,String phone,String email,Long receiverId){
        Area area1=new Area();
        if(area!=null){
            area1=areaService.selectById(area);
        }else if(shi!=null){
            area1=areaService.selectById(shi);
        }else if(sheng!=null){
            area1=areaService.selectById(sheng);
        }
        int result=0;
        Member member = memberService.getCurrent();
        if(receiverId.longValue()==0l){
            //新增
            Receiver receiver1=new Receiver();
            receiver1.setConsignee(consignee);
            receiver1.setCreateDate(new Date());
            receiver1.setModifyDate(new Date());
            receiver1.setAddress(address);
            receiver1.setAreaName(area1.getFullName());
            receiver1.setIsDefault(false);
            receiver1.setPhone(phone);
            receiver1.setArea(area);

            receiver1.setMember(member.getId());
            receiver1.setIsDeleted(false);
            receiver1.setZipCode("");
            receiver1.setEmail(email);

            result = receiverService.insertReceiver(receiver1);
        }else{
            //修改
            Receiver receiver1 = receiverService.selectById(receiverId);
            receiver1.setConsignee(consignee);
            receiver1.setCreateDate(new Date());
            receiver1.setModifyDate(new Date());
            receiver1.setAddress(address);
            receiver1.setAreaName(area1.getFullName());
            receiver1.setIsDefault(false);
            receiver1.setPhone(phone);
            receiver1.setArea(area);

            receiver1.setMember(member.getId());
            receiver1.setIsDeleted(false);
            receiver1.setZipCode("");
            receiver1.setEmail(email);
            result = receiverService.updateReceiver(receiver1);
        }
        return result;
    }


    //根据主键查询收货人
    @RequestMapping("/selReceiver")
    @ResponseBody
    public Map<String,Object> selReceiver(Long receiverid){
        Map receiver = receiverService.getReceiverById(receiverid);
        return receiver;
    }

    //设为默认收货地址
    @RequestMapping("/defaultReceiver")
    @ResponseBody
    public int defReceiver(Long receiverId){
        Member member = memberService.getCurrent();
        Receiver receiver2 = receiverService.selectIsDefault(member.getId());
       if(receiver2!=null){
           receiver2.setIsDefault(false);
           receiverService.updateReceiver(receiver2);
       }
        Receiver receiver = receiverService.selectById(receiverId);
        receiver.setIsDefault(true);
        int result = receiverService.updateReceiver(receiver);
        return result;
    }


    //删除收货人
    @RequestMapping("/delReceiver")
    @ResponseBody
    public int delReceiver(Long receiverId){

        return receiverService.deleteReceiver(receiverId);
    }


    /**
     * 收货地址
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String receiverview(Model model){
        Member member = memberService.getCurrent();
        List<Receiver> listreceiver = receiverService.selectByMember(member.getId());
        model.addAttribute("list",listreceiver);//当前用户的收货地址
        model.addAttribute("activeNone","member");//打开左侧导航
        model.addAttribute("active",2);//选中字菜单
        return "/receiver/list";

    }


    /**
     * 新增收货地址
     * @param receiver
     * @return
     */
    @PostMapping("/save")
    public String save(Receiver receiver ){
        Member member = memberService.getCurrent();

        receiver.setCreateDate(new Date());
        receiver.setModifyDate(new Date());
        receiver.setMember(member.getId());
        receiver.setIsDefault(false);
        receiver.setIsDeleted(false);
        Area area = areaService.selectById(receiver.getArea());
        receiver.setAreaName(area.getFullName());
        Integer saveReceiverCount = receiverService.insertReceiver(receiver);
        System.out.println("新增收货地址结果:"+saveReceiverCount);
        return "redirect:list";

    }

    /**
     * 修改收货地址结果
     * @param receiver
     * @return
     */
    @PostMapping("/update")
    public String update(Receiver receiver ){
        Member member = memberService.getCurrent();

        Area area = areaService.selectById(receiver.getArea());
        receiver.setAreaName(area.getFullName());
        Integer saveReceiverCount = receiverService.updateReceiver(receiver);
        System.out.println("新增收货地址结果:"+saveReceiverCount);
        return "redirect:list";

    }




}
