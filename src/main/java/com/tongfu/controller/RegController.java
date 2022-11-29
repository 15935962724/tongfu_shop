package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.FileUpload;
import com.tongfu.Util.MapUtil;
import com.tongfu.Util.ResultUtil;
import com.tongfu.config.CustomToken;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Member;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.MemberService;
import com.tongfu.service.ProductCategoryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;
import java.util.*;

@Controller
public class RegController {

	@Value("${map-key}")
	private String mapKey;

	@Value("${licenseImage}")
	private String licenseImage;

	@Value("${path-url}")
	private String pathUrl;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 注册页跳转
	 * @return
	 */
	@RequestMapping("/register")
	public String reg(Model model){
		System.out.print("123");
		List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("pathUrl",pathUrl);
		return "/register";
	}


	/**
	 * 企业用户注册第一步
	 * @return
	 */
	@RequestMapping("/enterpriseRegister1")
	public String enterpriseRegister1(Model model){
		List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("pathUrl",pathUrl);
		return "/enterpriseRegister1";
	}


	/**
	 * 企业用户注册第二步
	 * @return
	 */
	@PostMapping("/enterpriseRegister2")
	public String enterpriseRegister2( String mobile,
									  String password,Model model){
		List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("pathUrl",pathUrl);
		model.addAttribute("username",mobile);
		model.addAttribute("password",password);
		return "/enterpriseRegister2";
	}


	/**
	 * 企业注册下一步
	 * @param mobile
	 * @param password
	 * @param code
	 * @return
	 */
	@RequestMapping("/enterpriseRegister")
	@ResponseBody
	public String enterpriseRegisterCheckout(@RequestParam("mobile") String mobile,
						   @RequestParam("password") String password,
						   @RequestParam("code") String code){

		Member member = memberService.selectByUserName(mobile);
		if (member!=null){
			return  ResultUtil.error("该手机号已经被注册");
		}
		Subject subject = SecurityUtils.getSubject();
		String sessionCode = (String) subject.getSession().getAttribute(mobile);
		if (!code.equals(sessionCode)){
			return  ResultUtil.error("验证码输入有误");
		}
		return  ResultUtil.success();
	}


	/**
	 * 普通用户注册
	 * @param mobile
	 * @param password
	 * @param ip
	 * @param code
	 * @param system
	 * @return
	 */
	@RequestMapping("/regSubmit")
	@ResponseBody
	public String register(@RequestParam("mobile") String mobile,
						 @RequestParam("password") String password,
						 @RequestParam("ip") String ip,
						 @RequestParam("code") String code,
						   @RequestParam("system") String system
						){

		Member member = memberService.selectByUserName(mobile);
		if (member!=null){
			return  ResultUtil.error("该账号已经被注册");
		}

		Subject subject = SecurityUtils.getSubject();
		String sessionCode = (String) subject.getSession().getAttribute(mobile);
		if (!code.equals(sessionCode)){
			return  ResultUtil.error("验证码输入有误");
		}
		member = new Member();
		member.setUsername(mobile);
//		String mwpassword = username.equals("admin")?username:password;

		String encodedPassword = ShiroConfig.shiroEncryption(password,mobile);
		member.setPassword(encodedPassword);
		member.setMobile(mobile);
		member.setPhone(mobile);
		member.setCreateDate(new Date());
		member.setModifyDate(new Date());
		member.setAmount(new BigDecimal(0));
		member.setBalance(new BigDecimal(0));
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setPoint(Long.parseLong("0"));
		member.setSystem(system);
		member.setSource("PC端");
		member.setRegisterIp(ip);
		String data=MapUtil.getLatLng(ip,mapKey);
		JSONObject jsonObject=JSONObject.parseObject(data);
		String result = jsonObject.getString("result");
		JSONObject resultObject=JSONObject.parseObject(result);
		String location = resultObject.getString("location");
		JSONObject locationObject=JSONObject.parseObject(location);
		String lat =locationObject.getString("lat");//纬度
		String lng =locationObject.getString("lng");//经度
		member.setIplat(lat);
		member.setIplng(lng);
		String adInfo = resultObject.getString("ad_info");
		JSONObject adInfoObject=JSONObject.parseObject(adInfo);
		String nation=adInfoObject.getString("nation");
		String province=adInfoObject.getString("province");
		String city=adInfoObject.getString("city");
		String district=adInfoObject.getString("district");
		String adcode=adInfoObject.getString("adcode");
		member.setNation(nation);
		member.setProvince(province);
		member.setCity(city);
		member.setDistrict(district);
		member.setAdcode(adcode);


		member.setMemberRank(Long.parseLong("1"));
		member.setIsDeleted(false);
		member.setType(1);
		int result1=memberService.insertMember(member);
		Long mid = member.getId();
		if(result1<=0){
			return  ResultUtil.error("注册失败");
		}

		CustomToken usernamePasswordToken = new CustomToken(mobile,password);
		//进行验证，这里可以捕获异常，然后返回对应信息
		subject.login(usernamePasswordToken);
		subject.getSession().setAttribute("loginUser",mobile);
		subject.getSession().setAttribute("UserId",mid);
		return  ResultUtil.success("注册成功");


	}

	/**
	 * 企业注册提交
	 * @param ip
	 * @param username
	 * @param password
	 * @param regtype
	 * @param creditCode
	 * @param company
	 * @param area
	 * @param address
	 * @param productImage
	 * @return
	 */
	@PostMapping(value = "/regSubmitQy")
	public String regSubmitQy(String ip,String username,String password,Integer regtype,
					String creditCode,String company,Long areaId,String address,
					MultipartFile productImage,String system){

		String lisense_image= FileUpload.upload(productImage,licenseImage);
		Member member=new Member();
		String encodedPassword = ShiroConfig.shiroEncryption(password,username);
		member.setPassword(encodedPassword);
		member.setUsername(username);
		member.setMobile(username);
		member.setRegtype(regtype);
		member.setCreditCode(creditCode);
		member.setCompany(company);
		member.setArea(areaId);
		member.setAddress(address);
		member.setLicense(lisense_image);
		member.setCreateDate(new Date());
		member.setModifyDate(new Date());
		member.setAmount(BigDecimal.valueOf(0l));
		member.setBalance(BigDecimal.valueOf(0l));
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setMemberRank(1l);
		member.setIsDeleted(false);
		member.setPoint(0l);
		member.setRegisterIp(ip);
		member.setSystem(system);
		member.setSource("PC端");
		String data=MapUtil.getLatLng(ip,mapKey);
		JSONObject jsonObject=JSONObject.parseObject(data);
		String result = jsonObject.getString("result");
		JSONObject resultObject=JSONObject.parseObject(result);
		String location = resultObject.getString("location");
		JSONObject locationObject=JSONObject.parseObject(location);
		String lat =locationObject.getString("lat");//纬度
		String lng =locationObject.getString("lng");//经度
		member.setIplat(lat);
		member.setIplng(lng);
		String adInfo = resultObject.getString("ad_info");
		JSONObject adInfoObject=JSONObject.parseObject(adInfo);
		String nation=adInfoObject.getString("nation");
		String province=adInfoObject.getString("province");
		String city=adInfoObject.getString("city");
		String district=adInfoObject.getString("district");
		String adcode=adInfoObject.getString("adcode");
		member.setNation(nation);
		member.setProvince(province);
		member.setCity(city);
		member.setDistrict(district);
		member.setAdcode(adcode);
		member.setType(2);
		int result1 = memberService.insertMember(member);
		Subject subject = SecurityUtils.getSubject();
		CustomToken usernamePasswordToken = new CustomToken(username,password);
		subject.login(usernamePasswordToken);
		return "/enterpriseRegister3";

	}


	/**
	 * 校验该用户是否被注册
	 * @param username
	 * @return
	 */
	@RequestMapping("/checkUserName")
	@ResponseBody
	public Boolean checkUserName(@RequestParam("username") String username){
		Map map = new HashMap();
		map.put("username",username);
		map.put("isLocked",false);
//		Member member = memberService.selectByUserName(username);
		Member member = memberService.getMember(map);
		return member!=null;
	}







}
