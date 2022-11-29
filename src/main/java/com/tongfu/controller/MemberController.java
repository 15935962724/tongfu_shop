package com.tongfu.controller;


import com.tongfu.Util.ResultUtil;
import com.tongfu.config.ShiroConfig;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.EmailSend;
import com.tongfu.entity.Area;
import com.tongfu.entity.Department;
import com.tongfu.entity.Hospital;
import com.tongfu.entity.Member;
import com.tongfu.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller
@RequestMapping("/member")
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

	@Autowired
	private OrderService orderService;

	@RequestMapping("/reg")
	public String reg(){
		return "/member/reg_gr";
	}

	@RequestMapping("/regqy")
	public String regqy(){
		return "/member/reg";
	}

	@RequestMapping("/regqy2")
	public String regqy2(){
		return "/member/reg_2";
	}

	@RequestMapping("/view")
	public String view(Model model){


		Member member = memberService.getCurrent();
		model.addAttribute("member",member);
		Area memberArea = areaService.selectById(member.getArea());
		//待付款订单数量
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("member",member.getId());


		map.put("isMemberDelete",false);//用户未删除的订单

		//查询各个订单状态的数量
		Map orderStatus = orderService.getOrderStatus(map);
		model.addAttribute("orderStatus",orderStatus);//代付款，待收货，已完成，已取消  各个数量

		Integer sum = cartService.selectCartQuantitySum(member.getId());
		model.addAttribute("cartsum",sum);//购物车数量
		model.addAttribute("memberArea",memberArea);//用户地区

		Department memberDepartment = departmentService.selectByPrimaryKey(member.getDepartment()==null?0L:Long.valueOf(member.getDepartment()));
		model.addAttribute("memberDepartment",memberDepartment);//用户科室

		model.addAttribute("activeNone","member");//打开左侧导航
		model.addAttribute("active",1);//选中字菜单
		//待收货订单数量
		return "/member/view";

	}



	/**
	 * 我的knowHow卡
	 * @param model
	 * @return
	 */
	@RequestMapping("/knowHow")
	public String knowHow(Model model){
		Member member = memberService.getCurrent();
		model.addAttribute("activeNone","knowHow");//打开左侧导航
//		model.addAttribute("active",2);//选中字菜单
		return "/member/knowHow";

	}


	/**
	 * 安全设置
	 * @param model
	 * @return
	 */
	@RequestMapping("/security")
	public String security(Model model){
		Member member = memberService.getCurrent();
		model.addAttribute("member",member);//当前用户的收货地址
		model.addAttribute("activeNone","member");//打开左侧导航
		model.addAttribute("active",3);//选中字菜单
		return "/member/security_settings";

	}


	/**
	 * 修改用户信息
	 * @param name
	 * @param hospitalId
	 * @param hospitalName
	 * @param department
	 * @param address
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editmember")
	public int reg3(String name,Long hospitalId,String hospitalName, String department, String address,Long area){

		//查询医院
		if (hospitalId==null){
			Hospital hospital = new Hospital();
			hospital.setCreateDate(new Date());
			hospital.setName(hospitalName);
			Area hospitalArea = areaService.getParentArea(area);
			hospital.setAreaId(hospitalArea.getId());
			hospital.setAddress(hospitalArea.getName());
			hospitalService.insertSelective(hospital);
			hospitalId = hospital.getId();
		}
		Member member = memberService.getCurrent();
		member.setName(name);
		member.setHospital(hospitalName);
		member.setDepartment(department);
		member.setAddress(address);
		member.setArea(area);
		int result1 = memberService.updateMember(member);
		return result1;
	}

	/**
	 * 修改用户手机号
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/updateMemberPhone")
	@ResponseBody
	public String updateMemberPhone(String phone){
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
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/updateMemberEmail")
	@ResponseBody
	public String updateMemberEmail(String email,String code){
		Member member = memberService.selectByEmail(email);
		Subject subject = SecurityUtils.getSubject();
		String sessionCode = (String) subject.getSession().getAttribute(email);
		if (!code.equals(sessionCode)){
			return ResultUtil.error("验证码输入有误");
		}
		if (member!=null){
			return ResultUtil.error("该邮箱已被注册");
		}

		member = memberService.getCurrent();
		member.setEmail(email);
		int result = memberService.updateMember(member);
		return result>0?ResultUtil.success():ResultUtil.error("绑定失败!");
	}

	/**
	 *
	 * @param email
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/updateEmail")
	@ResponseBody
	public String updateEmail(String email,String code){
		Member member = memberService.selectByEmail(email);
		if (member!=null){
			return ResultUtil.error("该邮箱已被注册");
		}
		Subject subject = SecurityUtils.getSubject();
		String sessionCode = (String) subject.getSession().getAttribute(email);
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
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/sendEmailCode")
	@ResponseBody
	public String sendEmailCode(String email){
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
	 * @param password
	 * @return
	 */
	@PostMapping("/updatePassword")
	@ResponseBody
	public String updatePassword(@RequestParam String password){

		Member member = memberService.getCurrent();

		String encodedPassword = ShiroConfig.shiroEncryption(password,member.getUsername());
		member.setPassword(encodedPassword);
		Integer updatePasswordCount = memberService.updateMember(member);


		if (updatePasswordCount==1){
			return  ResultUtil.success("修改成功");
		}else {
			return ResultUtil.error("修改密码失败");
		}

	}





}
