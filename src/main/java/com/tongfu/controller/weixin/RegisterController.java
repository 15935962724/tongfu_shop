package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.*;
import com.tongfu.config.CustomToken;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.Member;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.MemberService;
import com.tongfu.service.ProductCategoryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("wexinregister")
@RequestMapping("/wexin/register")
public class RegisterController {

	@Value("${map-key}")
	private String mapKey;

	@Value("${licenseImage}")
	private String licenseImage;

	@Value("${path-url}")
	private String pathUrl;

	@Autowired
	private MemberService memberService;

	@Value("${wxAppid}")
	private String wxAppid;

	@Value("${wxSecret}")
	private String wxSecret;


		@RequestMapping("/submit")
		@ResponseBody
		public String submit(@RequestBody JSONObject jsonObject){
			String mobile = jsonObject.getString("mobile");
			Subject subject = SecurityUtils.getSubject();
			String sessionCode = (String) subject.getSession().getAttribute(mobile);
			sessionCode = sessionCode==null?"111111":sessionCode;
			if (!jsonObject.getString("code").equals(sessionCode)){
				return  ResultUtil.error("验证码输入有误");
			}
			Member member = memberService.selectByUserName(jsonObject.getString("mobile"));
			int result1 = 0;
			if (member==null){
				member = new Member();
				member.setUsername(jsonObject.getString("mobile"));
				String encodedPassword = ShiroConfig.shiroEncryption(mobile,mobile);
				member.setOpenId(jsonObject.getString("openid"));
				member.setNickName(jsonObject.getString("nickName"));
				member.setLogo(jsonObject.getString("logo"));
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
				member.setSource("公众号");
				member.setSystem(jsonObject.getString("system"));
				member.setType(1);
//				member.setRegisterIp(jsonObject.getString("ip"));
//				String data=MapUtil.getLatLng(jsonObject.getString("ip"),mapKey);
//				JSONObject jsonObjectMap=JSONObject.parseObject(data);
//				String result = jsonObjectMap.getString("result");
//				JSONObject resultObject=JSONObject.parseObject(result);
//				String location = resultObject.getString("location");
//				JSONObject locationObject=JSONObject.parseObject(location);
//				String lat =locationObject.getString("lat");//纬度
//				String lng =locationObject.getString("lng");//经度
//				member.setIplat(lat);
//				member.setIplng(lng);
//				String adInfo = resultObject.getString("ad_info");
//				JSONObject adInfoObject=JSONObject.parseObject(adInfo);
//				String nation=adInfoObject.getString("nation");
//				String province=adInfoObject.getString("province");
//				String city=adInfoObject.getString("city");
//				String district=adInfoObject.getString("district");
//				String adcode=adInfoObject.getString("adcode");
//				member.setNation(nation);
//				member.setProvince(province);
//				member.setCity(city);
//				member.setDistrict(district);
//				member.setAdcode(adcode);
				member.setMemberRank(Long.parseLong("1"));
				member.setIsDeleted(false);
				result1=memberService.insertMember(member);
			}else {
				member.setOpenId(jsonObject.getString("openid"));
				member.setNickName(jsonObject.getString("nickName"));
				member.setLogo(jsonObject.getString("logo"));
				result1=memberService.updateMember(member);
			}
		if(result1<=0){
			return  ResultUtil.error("注册失败");
		}
		CustomToken usernamePasswordToken = new CustomToken(mobile);
		//进行验证，这里可以捕获异常，然后返回对应信息
		subject.login(usernamePasswordToken);
		return  ResultUtil.success("注册成功");

	}


	/**
	 * 校验根据code获取微信用户判断是否注册
	 * @return
	 */
	@RequestMapping("/checkMember")
	@ResponseBody
	public String checkUserName(@RequestBody JSONObject jsonObject){
		Subject subject = SecurityUtils.getSubject();
//		System.out.println("code:"+code+",data:"+data);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("code",jsonObject.getString("code"));
		jsonObject.put("appId",wxAppid);
		jsonObject.put("secret",wxSecret);
		System.out.println("微信参数"+jsonObject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WeiXinUtils wxu = new WeiXinUtils();
		JSONObject userinfojson = null;
		try {
			userinfojson = JSON.parseObject(wxu.getUserInfo(jsonObject));
		} catch (IOException e) {
			System.out.println("获取微信信息出错!");
			e.printStackTrace();
		}
		System.out.println(userinfojson);
		String openid = userinfojson.getString("openid");
		System.out.println("获取登录人--nickName>>>"+userinfojson.get("nickname").toString()+"---访问时间>>>" + sdf.format(new Date()));
		Map<String,Object> map=new HashMap<>();
		map.put("openId",openid);
		Member member = memberService.getMember(map);
		if (member!=null){
			CustomToken token = new CustomToken(member.getUsername());
			subject.login(token);
			return ResultUtil.success();
		}else {
			Result result = new Result ();
			result.setStatus (400);
			result.setMsg ("该用户尚未注册");
			result.setData (userinfojson);
			return JSON.toJSON(result).toString();
		}

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
