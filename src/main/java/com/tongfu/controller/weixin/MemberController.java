package com.tongfu.controller.weixin;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.ResultUtil;
import com.tongfu.config.ShiroConfig;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.EmailSend;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller("weixinmember")
@RequestMapping("/weixin/member")
public class MemberController {

	@Value("${map-key}")
	private String mapKey;

	@Value("${licenseImage}")
	private String licenseImage;


	//#邮箱账号
	@Value("${emailUserName}")
	private String emailUserName;

	//#发送者密码
	@Value("${emailPassword}")
	private String emailPassword;

	//#邮箱服务器地址
	@Value("${emailHost}")
	private String emailHost;

	//#主机端口
	@Value("${emailPort}")
	private String emailPort;

	//#发送者邮箱
	@Value("${emailUserName}")
	private String emailFromAddress;

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderSerice;

	@Autowired
	private CartService cartService;

	@Autowired
	private ReceiverService receiverService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private  HospitalService hospitalService;//医院

	@Autowired
	private DepartmentService departmentService;//科室


	/**
	 * 账户信息
	 * @return
	 */
	@RequestMapping("/view")
	@ResponseBody
	public String view(){
		JSONObject returnJson = new JSONObject();
		Member member = memberService.getCurrent();
		if (member.getId()!=null){
			returnJson.put("area",areaService.selectById(member.getArea()));
		}else {
			returnJson.put("area",null);
		}
		returnJson.put("member",member);

		if (member.getDepartment()!=null){
			Department memberDepartment = departmentService.selectByPrimaryKey(Long.valueOf(member.getDepartment()));
			returnJson.put("memberDepartment",memberDepartment);
		}else {
			returnJson.put("memberDepartment",null);
		}

		return  ResultUtil.success(returnJson);
	}


	/**
	 * 修改用户信息
	 * @param jsonObject
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMember")
	public String updateMember(@RequestBody JSONObject jsonObject){

		Member member = JSONObject.parseObject(jsonObject.toJSONString(),Member.class);
		int updateMember = memberService.updateMember(member);
		if (updateMember>0){
			return  ResultUtil.success();
		}
		return ResultUtil.error("操作失败!");
	}

	/**
	 * 修改用户手机号
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/updateMemberPhone")
	@ResponseBody
	public String updateMemberPhone(@RequestBody JSONObject jsonObject){
		String phone = jsonObject.getString("phone");
		Member member = memberService.getCurrent();
		member.setPhone(phone);
		member.setMobile(phone);
		int result = memberService.updateMember(member);
		return result>0?ResultUtil.success():ResultUtil.error("修改失败");
	}

	/**
	 * 修改账号
	 * @param userName
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/updateUserName")
	@ResponseBody
	public String updateUserName(String userName,String code){
		Subject subject = SecurityUtils.getSubject();
		String sessionCode = (String) subject.getSession().getAttribute(userName);
		if (!code.equals(sessionCode)){
			return ResultUtil.error("验证码输入有误");
		}

		Member member = memberService.getCurrent();
		Map query_map = new HashMap();
		query_map.put("username",userName);
		Member member1 = memberService.getMember(query_map);
		if (member1!=null){
			member1.setIsLocked(true);
			member1.setLockedDate(new Date());
			memberService.updateMember(member1);
		}
		member.setUsername(userName);
		int result = memberService.updateMember(member);
		return result>0?ResultUtil.success():ResultUtil.error("修改失败");
	}


	/**
	 * 绑定邮箱
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/updateEmail")
	@ResponseBody
	public String updateEmail(@RequestBody JSONObject jsonObject){
		String email = jsonObject.getString("email");
		String code = jsonObject.getString("code");
		Member member = memberService.selectByEmail(email);
		if (member!=null){
			return ResultUtil.error("该邮箱已被注册");
		}
		Subject subject = SecurityUtils.getSubject();
		String sessionCode = (String) subject.getSession().getAttribute(email);
		sessionCode = sessionCode==null?"111111":sessionCode;
		if (!code.equals(sessionCode)){
			return ResultUtil.error("验证码输入有误!");
		}
		member = memberService.getCurrent();
		member.setEmail(email);
		int result = memberService.updateMember(member);
		return result>0?ResultUtil.success("绑定成功",null):ResultUtil.error("绑定失败!");
	}

	/**
	 * 邮箱发送验证码
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/sendEmailCode")
	@ResponseBody
	public String sendEmailCode(@RequestBody JSONObject jsonObject){
		String email = jsonObject.getString("email");
		Member member = memberService.selectByEmail(email);
		if (member!=null){
			return ResultUtil.error("该邮箱已被绑定");
		}

		EmailEntity emailEntity = new EmailEntity();
		emailEntity.setUserName(emailUserName);
		emailEntity.setPassword(emailPassword);
		emailEntity.setHost(emailHost);
		emailEntity.setPort(Integer.valueOf(emailPort));
		emailEntity.setFromAddress(emailFromAddress);
		emailEntity.setToAddress(email);

		Random random = new Random();
		String code = String.valueOf(random.nextInt(1000000));
		emailEntity.setContext("您正在用此邮箱绑定sp平台，验证码为"+code);

		emailEntity.setSubject("sp平台邮箱绑定");
		emailEntity.setContextType("text/html;charset=utf-8");
//        email.attachFile("往邮件中添加附件");
		boolean flag = EmailSend.EmailSendTest(emailEntity);
		System.err.println("邮件发送结果=="+flag);

		if (flag){
			Subject subject = SecurityUtils.getSubject();
			subject.getSession().setAttribute(email,code);
			return  ResultUtil.success("验证码发送成功!",null);
		}else{
			return ResultUtil.error("处理失败,该邮箱可能未注册!");
		}
	}

	/**
	 * 修改密码
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/updatePassword")
	@ResponseBody
	public String updatePassword(@RequestBody JSONObject jsonObject){
		Member member = memberService.getCurrent();
		String encodedPassword = ShiroConfig.shiroEncryption(jsonObject.getString("password"),member.getUsername());
		member.setPassword(encodedPassword);
		Integer updatePasswordCount = memberService.updateMember(member);
		if (updatePasswordCount==1){
			return  ResultUtil.success("修改成功");
		}else {
			return ResultUtil.error("修改密码失败");
		}

	}





}
