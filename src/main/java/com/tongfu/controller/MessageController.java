package com.tongfu.controller;

import com.tongfu.entity.Admin;
import com.tongfu.entity.Consultation;
import com.tongfu.entity.Member;
import com.tongfu.entity.Message;
import com.tongfu.service.AdminService;
import com.tongfu.service.MemberService;
import com.tongfu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/message")
public class MessageController {

	 @Autowired
	 MessageService messageService;

	 @Autowired
	 MemberService memberService;

	 @Autowired
	 AdminService adminService;

	@RequestMapping("/addMessage")
	@ResponseBody
	 public Integer insertMessage(Long pid,Long cid,String content,String email){
		//添加咨询
		Message message = new Message();
		message.setCreateDate(new Date());
		message.setContent(content);
		message.setEmail(email);
		Member member = memberService.getCurrent();
		message.setSender(member.getId());//发送人
		Map<String,Object> map = new HashMap<>();
		map.put("type",1);
		map.put("company",cid);
		List<Admin> listadmin = adminService.selectAdmin(map);
		Long receiver = 0l;
		if(listadmin.size()>0){
			Admin admin = listadmin.get(0);
			receiver = admin.getId();
		}
		message.setReceiver(receiver);//接收人
		message.setIsForwardReceiver(false);
		message.setIsForwardSender(true);
		message.setProductId(pid);
		message.setIsHandle(false);
		message.setCompanyId(cid);
		Integer result = messageService.insertSelective(message);
			return result;
	 }
	 
}
