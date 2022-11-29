package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.*;
import com.tongfu.config.CustomToken;
import com.tongfu.config.ShiroConfig;
import com.tongfu.email.aliyun.MailInfo;
import com.tongfu.email.aliyun.MailSendUtils;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class LoginController {


	@Autowired
	private MemberService memberService;

	@Value("${map-key}")
	private String mapKey;

	@Value("${wbAppey}")
	private String wbAppey;

	@Value("${wbAppSecret}")
	private String wbAppSecret;

	@Autowired
	private CartService cartService;

	@Value("${adpositionlogin}")
	private Long adpositionlogin;

	@Autowired
	private AdService adService;


	@Value("${wxAppid}")
	private String wxAppid;

	@Value("${wxSecret}")
	private String wxSecret;

	@Value("${wxApplicationAppId}")
	private String wxApplicationAppId;

	@Value("${wxApplicationAppSecret}")
	private String wxApplicationAppSecret;

	@Value("${QQAppId}")
	private String QQAppId;

	@Value("${QQAppKey}")
	private String QQAppKey;

	@Value("${path-url}")
	private String pathUrl;

	//阿里云发件人账户
	@Value("${aliyunSendEmailAccount}")
	private String aliyunSendEmailAccount;

	//阿里云发件人密码
	@Value("${aliyunSendEmailPassword}")
	private String aliyunSendEmailPassword;

	@Autowired
	private ProductCategoryService pcService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private WbMemberService wbMemberService;

	@Autowired
	private ReceiverService receiverService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ProductPurchaseService productPurchaseService;


	/**
	 * 账号密码登录
	 * @param username
	 * @param password
	 * @param redirectUrl
	 * @param ip
	 * @param map
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping(value = "/loginSubmit")
	public String loginSubmit(@RequestParam("username")String username,
					   @RequestParam("password") String password,
					   @RequestParam("redirectUrl") String redirectUrl,
					   @RequestParam("ip") String ip,
					   Map<String,Object> map, Model model,HttpSession session) {

		Subject subject = SecurityUtils.getSubject();

//		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);

		map.put("adPosition",adpositionlogin);//登录页广告位
		List<Ad> list= adService.listAd(map);
		model.addAttribute("redirectUrl",redirectUrl);
		List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("list",list);
		model.addAttribute("pathUrl",pathUrl);
		model.addAttribute("wbMember",new WbMember());
		map.put("username",username);
		map.put("isLocked",true);
		try {
			Member member = memberService.getMember(map);
			if (member!=null){
				map.put("msg"  ,"您的账号已被锁定，请使用验证码登录!");
				return "/login";
			}
			map.put("isLocked",false);
			member = memberService.getMember(map);
			if (member==null){
				map.put("msg"  ,"账号输入有误，请重新输入!");
				return "/login";
			}
			/*String mwpassword = username.equals("admin")?username:password;

			String encodedPassword = ShiroConfig.shiroEncryption(mwpassword,username);
			System.out.println("密码："+encodedPassword);*/
			CustomToken usernamePasswordToken = new CustomToken(username,password);
			//进行验证，这里可以捕获异常，然后返回对应信息
			subject.login(usernamePasswordToken);
			subject.getSession().setAttribute("loginUser",username);

			if(redirectUrl!=null&&!redirectUrl.equals("")){
				return "redirect:"+ redirectUrl;
			}
			return "redirect:/" ;
		}
		catch ( UnknownAccountException uae ) {
			//用户名未知...
//			log.info("用户不存在");
			map.put("msg"  ,"用户不存在");
		} catch ( IncorrectCredentialsException ice ) {
			//凭据不正确，例如密码不正确 ...
//			log.info("密码不正确");
			map.put("msg"  ,"密码错误");
		} catch ( LockedAccountException lae ) {
			//用户被锁定，例如管理员把某个用户禁用...
//			log.info("用户被禁用");
			map.put("msg"  ,"用户被禁用");
		} catch ( ExcessiveAttemptsException eae ) {
			//尝试认证次数多余系统指定次数 ...
//			log.info("请求次数过多，用户被锁定");
			map.put("msg"  ,"请求次数过多,用户被锁定");
		} catch ( UnauthorizedException uae ) {
			//用户名未知...
			map.put("msg"  ,"用户不存在1");
		}
		return "/login";
	}


	/**
	 * 退出登陆
	 * @return
	 */
	@RequestMapping(value = "/lognout")
	public String lognout() {
		System.out.print("登出");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/";
	}


	//登录页
	@RequestMapping("/denglu")
	public String denglu(Model model,String redirectUrl){
		System.out.print("进入登录页");
		Map<String,Object> map=new HashMap<>();
		map.put("adPosition",adpositionlogin);//登录页广告位
		List<Ad> list= adService.listAd(map);
		model.addAttribute("redirectUrl",redirectUrl);
		List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("list",list);
		model.addAttribute("pathUrl",pathUrl);
		model.addAttribute("wbMember",new WbMember());
		return "/login";
	}

    /**
     * 微信扫码登录回调
     * @param model
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/showBindWeChatAccount")
    public String showBindWeChatAccount(Model model,String code,String state){
        System.out.print("微信回调Code："+code);

		String body ="appid="+wxApplicationAppId+"&secret="+wxApplicationAppSecret+"&code="+code+"&grant_type=authorization_code";
        String getAccessTokeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String data = HttpUtil.post(getAccessTokeUrl, body);
        System.out.println("微信应用accessToke结果:"+data);
        JSONObject jsonObject = (JSONObject) JSON.parse(data);
        String access_token = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");
		String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid;
		data = HttpUtil.getInvoke(userInfoUrl);
		jsonObject = (JSONObject) JSON.parse(data);
		String nickname = jsonObject.getString("nickname");
		String logo = jsonObject.getString("headimgurl");


		Map map = new HashMap();
		map.put("openId",openid);
		map.put("isLocked",false);
		Member member = memberService.getMember(map);
		if (member == null) {
			member = new Member();
			member.setUsername(openid);
			member.setCreateDate(new Date());
			member.setModifyDate(new Date());
			member.setArea(1l);
			member.setDepartment("1");
			member.setAmount(new BigDecimal(0));
			member.setBalance(new BigDecimal(0));
			member.setIsEnabled(true);
			member.setIsLocked(false);
			member.setLoginFailureCount(0);
			member.setPoint(Long.parseLong("0"));
			member.setSystem("window 10");
			member.setSource("PC端");
			member.setRegisterIp("106.13.123.1");
			member.setOpenId(openid);
			member.setNickName(nickname);
			member.setLogo(logo);
			data = MapUtil.getLatLng("106.13.123.1", mapKey);
			jsonObject = JSONObject.parseObject(data);
			String result = jsonObject.getString("result");
			JSONObject resultObject = JSONObject.parseObject(result);
			String location = resultObject.getString("location");
			JSONObject locationObject = JSONObject.parseObject(location);
			String lat = locationObject.getString("lat");//纬度
			String lng = locationObject.getString("lng");//经度
			member.setIplat(lat);
			member.setIplng(lng);
			String adInfo = resultObject.getString("ad_info");
			JSONObject adInfoObject = JSONObject.parseObject(adInfo);
			String nation = adInfoObject.getString("nation");
			String province = adInfoObject.getString("province");
			String city = adInfoObject.getString("city");
			String district = adInfoObject.getString("district");
			String adcode = adInfoObject.getString("adcode");
			member.setNation(nation);
			member.setProvince(province);
			member.setCity(city);
			member.setDistrict(district);
			member.setAdcode(adcode);
			member.setMemberRank(Long.parseLong("1"));
			member.setIsDeleted(false);
			member.setType(1);
			int result1 = memberService.insertMember(member);
		}

		CustomToken token = new CustomToken(member.getUsername());
		Subject subject = SecurityUtils.getSubject();
		//进行验证，这里可以捕获异常，然后返回对应信息
		subject.login(token);
		System.out.println("state:"+state==null);
		if (state != null && !state.equals("")&& !state.equals("null")) {
			return "redirect:" + state;
		}

		map.put("adPosition",adpositionlogin);//登录页广告位
		List<Ad> list= adService.listAd(map);
		List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("list",list);
		model.addAttribute("pathUrl",pathUrl);
		return "redirect:/";
    }


	/**
	 * QQ登录回调
	 * @param model
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping("/showQQAccount")
	public String showQQAccount(Model model,String code,String state){
		System.out.print("QQ回调Code："+code);
//		String body ="appid="+wxApplicationAppId+"&secret="+wxApplicationAppSecret+"&code=CODE&grant_type=authorization_code";
		String body ="grant_type=authorization_code&client_id="+QQAppId+"&client_secret="+QQAppKey+"&code="+code+"&redirect_uri=http://www.surgi-plan.com/showQQAccount&fmt=json";
		String getAccessTokeUrl = "https://graph.qq.com/oauth2.0/token";
		String data = HttpUtil.post(getAccessTokeUrl, body);
		System.out.println("getToken:"+data);
		JSONObject jsonObject = (JSONObject) JSON.parse(data);
		String access_token = jsonObject.getString("access_token");

		String getUserOpenIdUrl = "https://graph.qq.com/oauth2.0/me";
		body ="access_token="+access_token+"&fmt=json";
		 data = HttpUtil.post(getUserOpenIdUrl, body);
		System.out.println("getOpenId:"+data);
		jsonObject = (JSONObject) JSON.parse(data);
		String QQopenId = jsonObject.getString("openid");


		Map map = new HashMap();
		map.put("qqId",QQopenId);
		map.put("isLocked",false);
		Member member = memberService.getMember(map);
		if (member == null) {
			member = new Member();
			member.setUsername(QQopenId);
			member.setArea(1l);
			member.setDepartment("1");
			member.setCreateDate(new Date());
			member.setModifyDate(new Date());
			member.setAmount(new BigDecimal(0));
			member.setBalance(new BigDecimal(0));
			member.setIsEnabled(true);
			member.setIsLocked(false);
			member.setLoginFailureCount(0);
			member.setPoint(Long.parseLong("0"));
			member.setSystem("window 10");
			member.setSource("PC端");
			member.setRegisterIp("106.13.123.1");
			member.setQqId(QQopenId);
			String getNiceNameUrl = "https://graph.qq.com/user/get_user_info?access_token=" + access_token + "&oauth_consumer_key=" + QQAppKey + "&openid=" + QQopenId;
			String niceNameJson = HttpUtil.getInvoke(getNiceNameUrl);
			jsonObject = (JSONObject) JSON.parse(niceNameJson);
			member.setNickName(jsonObject.getString("nickname"));
			data = MapUtil.getLatLng("106.13.123.1", mapKey);
			jsonObject = JSONObject.parseObject(data);
			String result = jsonObject.getString("result");
			JSONObject resultObject = JSONObject.parseObject(result);
			String location = resultObject.getString("location");
			JSONObject locationObject = JSONObject.parseObject(location);
			String lat = locationObject.getString("lat");//纬度
			String lng = locationObject.getString("lng");//经度
			member.setIplat(lat);
			member.setIplng(lng);
			String adInfo = resultObject.getString("ad_info");
			JSONObject adInfoObject = JSONObject.parseObject(adInfo);
			String nation = adInfoObject.getString("nation");
			String province = adInfoObject.getString("province");
			String city = adInfoObject.getString("city");
			String district = adInfoObject.getString("district");
			String adcode = adInfoObject.getString("adcode");
			member.setNation(nation);
			member.setProvince(province);
			member.setCity(city);
			member.setDistrict(district);
			member.setAdcode(adcode);
			member.setMemberRank(Long.parseLong("1"));
			member.setIsDeleted(false);
			member.setType(1);
			int result1 = memberService.insertMember(member);
		}

		CustomToken token = new CustomToken(member.getUsername());
		Subject subject = SecurityUtils.getSubject();
		//进行验证，这里可以捕获异常，然后返回对应信息
		subject.login(token);
		System.out.println("state:"+state==null);
		if (state != null && !state.equals("")&& !state.equals("null")) {
			return "redirect:" + state;
		}

		map.put("adPosition",adpositionlogin);//登录页广告位
		List<Ad> list= adService.listAd(map);
		List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("list",list);
		model.addAttribute("pathUrl",pathUrl);

		return "redirect:/";

	}


	/**
	 * 微博回调
	 * @param model
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping("/showBindWbAccount")
	public String showBindAccount(Model model,String code,String state){
		System.out.println("微博回调Code："+code);
		Subject subject = SecurityUtils.getSubject();


		String body = "client_id="+wbAppey+"&client_secret="+wbAppSecret+"&grant_type=authorization_code&code="+code+"&redirect_uri=http://www.surgi-plan.com/showBindWbAccount";
		String getAccessTokeUrl = "https://api.weibo.com/oauth2/access_token";
		String data = HttpUtil.post(getAccessTokeUrl, body);
//		String data = HttpUtil.getInvoke(getAccessTokeUrl);
		System.out.println("accessToke结果:"+data);
//		{"access_token":"2.00vqqh4DNDeoBE186520f12b07KpaU","remind_in":"157679998","expires_in":157679998,"uid":"3098705497","isRealName":"true"}
		JSONObject jsonObject = (JSONObject) JSON.parse(data);
		String access_token = jsonObject.getString("access_token");
		String getUidUrl = "https://api.weibo.com/2/account/get_uid.json?access_token="+access_token;
		data = HttpUtil.getInvoke(getUidUrl);
		System.out.println("获取uid结果:"+data);
		JSONObject jsonObject1 = (JSONObject) JSON.parse(data);
		Long uid = jsonObject1.getLong("uid");
		Map query_map = new HashMap();
		query_map.put("wbId",uid);
		query_map.put("isLocked",false);
		Member member = memberService.getMember(query_map);
		if (member==null){
			member = new Member();
			member.setUsername(uid.toString());
			String encodedPassword = ShiroConfig.shiroEncryption(uid.toString(),uid.toString());
			member.setPassword(encodedPassword);
			member.setCreateDate(new Date());
			member.setModifyDate(new Date());
			member.setArea(1l);
			member.setDepartment("1");
			member.setAmount(new BigDecimal(0));
			member.setBalance(new BigDecimal(0));
			member.setIsEnabled(true);
			member.setIsLocked(false);
			member.setLoginFailureCount(0);
			member.setPoint(Long.parseLong("0"));
			member.setSystem("window10");
			member.setSource("微博登录");
			member.setRegisterIp("106.13.123.1");
			data= MapUtil.getLatLng("106.13.123.1",mapKey);
			jsonObject=JSONObject.parseObject(data);
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
			int result1=memberService.insertMember(member);
		}


		// 创建Subject实例
//			Subject currentUser = SecurityUtils.getSubject();
		// 将用户名及密码封装到UsernamePasswordToken
		CustomToken token = new CustomToken(member.getUsername());
		subject.login(token);
		System.out.println(subject.getSession().getId());
		subject.getSession().setAttribute("pathUrl",pathUrl);
		if(state!=null&&!state.equals("")){
			return "redirect:"+ state;
		}

		return "redirect:/" ;

	}


	/**
	 * 微博验证完之后登录
	 * @param model
	 * @param wbId
	 * @param bindPhone
	 * @param bindCode
	 * @param state
	 * @param wbIp
	 * @param map
	 * @return
	 */
	/*@RequestMapping("/bindWb")
	public String bindWb(Model model,Long wbId,String bindPhone,String bindCode,String state,String wbIp,Map<String,Object> map){

		Subject subject = SecurityUtils.getSubject();
		Map query_map = new HashMap();
		query_map.put("wbId",wbId);
		WbMember wbMember = wbMemberService.selectByPrimaryKey(wbId);
//		Member member = memberService.selectByUserName(bindPhone);
//		Map query_map = new HashMap();
		query_map.remove("wbId");
		query_map.put("username",bindPhone);
		query_map.put("isLocked",false);
		Member member = memberService.getMember(query_map);
		model.addAttribute("wbMember",wbMember);
		String sessionCode = (String) subject.getSession().getAttribute(bindPhone);
		if (!bindCode.equals(sessionCode)){
			model.addAttribute("uid",wbId);
			map.put("msg"  ,"验证码输入有误请重新输入!");
			return "/login";
		}
		// 将用户名及密码封装到UsernamePasswordToken
		CustomToken token = new CustomToken(member.getUsername());
		subject.login(token);
		System.out.println(subject.getSession().getId());
		subject.getSession().setAttribute("pathUrl",pathUrl);
		if(state!=null&&!state.equals("")){
			return "redirect:"+ state;
		}

		return "redirect:/" ;

	}*/


	/**
	 * 微博验证完之后登录
	 * @param model
	 * @param bindPhone
	 * @param bindCode
	 * @param state
	 * @param map
	 * @return
	 */
	@RequestMapping("/bindWx")
	public String bindWx(Model model,String openId,String bindPhone,String bindCode,String state,String wxIp,Map<String,Object> map){

		Subject subject = SecurityUtils.getSubject();
		Map query_map = new HashMap();

		query_map.put("username",bindPhone);
		query_map.put("isLocked",false);
		Member member = memberService.getMember(query_map);
		String sessionCode = (String) subject.getSession().getAttribute(bindPhone);
		if (!bindCode.equals(sessionCode)){
			model.addAttribute("openId",openId);
			model.addAttribute("state",state);
			map.put("msg"  ,"验证码输入有误请重新输入!");
			return "/login";
		}
		if (member==null){
			model.addAttribute("openId",openId);
			member = new Member();
			member.setUsername(bindPhone);
//		String mwpassword = username.equals("admin")?username:password;

			String encodedPassword = ShiroConfig.shiroEncryption(bindPhone,bindPhone);
			member.setPassword(encodedPassword);
			member.setMobile(bindPhone);
			member.setPhone(bindPhone);
			member.setCreateDate(new Date());
			member.setModifyDate(new Date());
			member.setAmount(new BigDecimal(0));
			member.setBalance(new BigDecimal(0));
			member.setIsEnabled(true);
			member.setIsLocked(false);
			member.setLoginFailureCount(0);
			member.setPoint(Long.parseLong("0"));

			member.setRegisterIp(wxIp);
			String data= MapUtil.getLatLng(wxIp,mapKey);
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
			member.setOpenId(openId);
			int result1=memberService.insertMember(member);
		}else{
			member.setOpenId(openId);
			memberService.updateMember(member);

		}

		// 将用户名及密码封装到UsernamePasswordToken
		CustomToken token = new CustomToken(member.getUsername());
		subject.login(token);
		System.out.println(subject.getSession().getId());
		subject.getSession().setAttribute("pathUrl",pathUrl);
		if(state!=null&&!state.equals("")){
			return "redirect:"+ state;
		}

		return "redirect:/" ;

	}





	/**
	 * 验证码登录
	 * @param phone
	 * @param code
	 * @param redirectUrl
	 * @param ip
	 * @param map
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping(value = "/codeSubmit")
	public String codeSubmit(String phone,
					    String code,
					    String redirectUrl,
					    String ip,
					   Map<String,Object> map, Model model,HttpSession session) {

		Subject subject = SecurityUtils.getSubject();
		map.put("username",phone);
		map.put("isLocked",false);
		try {
			Member member = memberService.getMember(map);
			map.put("adPosition",adpositionlogin);//登录页广告位
			List<Ad> list= adService.listAd(map);
			model.addAttribute("redirectUrl",redirectUrl);
			List<ProductCategory> categoryList = productCategoryService.selectByParents(0L,1l,null);
			model.addAttribute("categoryList",categoryList);
			model.addAttribute("list",list);
			model.addAttribute("pathUrl",pathUrl);
			model.addAttribute("wbMember",new WbMember());
			if (member==null){
				map.put("msg"  ,"用户不存在!");
				return "/login";
			}
			if (member.getIsLocked()){
				map.put("msg"  ,"该用户已被锁定请联系管理员!");
				return "/login";
			}

			String sessionCode = (String) subject.getSession().getAttribute(phone);
			if (!code.equals(sessionCode)){
				map.put("msg"  ,"输入有误请重新输入!");
				return "/login";
			}

			// 创建Subject实例
//			Subject currentUser = SecurityUtils.getSubject();
			// 将用户名及密码封装到UsernamePasswordToken
			CustomToken token = new CustomToken(member.getUsername());
			subject.login(token);
			System.out.println(subject.getSession().getId());

			if(redirectUrl!=null&&!redirectUrl.equals("")){
				return "redirect:"+ redirectUrl;
			}

			return "redirect:/" ;
		} catch (IncorrectCredentialsException e) {
			//logger.info("密码错误");
			System.out.println(e.getMessage());
			map.put("msg"  ,"密码错误");
		}  catch (AuthenticationException e) {
			//logger.info("该用户不存在");
			System.out.println(e.getMessage());
			map.put("msg"  ,"该用户不存在");
		} catch (Exception e) {
			e.printStackTrace();
			//logger.info("登录错误");
			System.out.println(e.getMessage());
			map.put("msg"  ,"登录错误");
		}

		return "/login";
	}


	/**
	 * 找回密码下一步
	 * @param phone
	 * @param code
	 * @param map
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/forgetPasswordComplete")
	public String codeSubmit(String phone,
							 String code,
							 Map<String,Object> map , Model model) {

			Subject subject = SecurityUtils.getSubject();

			Member member = memberService.selectByUserName(phone);
			if (member==null){
				map.put("msg"  ,"用户不存在!");
				return "/forgetPassword";
			}

			String sessionCode = (String) subject.getSession().getAttribute(phone);
			if (!code.equals(sessionCode)){
				map.put("msg"  ,"输入有误请重新输入!");
				return "/forgetPassword";
			}

			Map<String,String> querymap = new HashMap<>();
			Map<String,Object> set_map =SettingUtils.getReadXml(pathUrl) ;
			querymap.put("url",String.valueOf(set_map.get("shortMessageUrl")));
			querymap.put("accountSid",String.valueOf(set_map.get("accountSid")));
			querymap.put("authToken",String.valueOf(set_map.get("authToken")));
			querymap.put("phone",phone);
			Random rd = new Random();
			char[] strRandomList = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
					 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
					 'x', 'y', 'z' };
			String pwd = "";
			for (int i = 0; i < 6; i++)
				 {
				 pwd += strRandomList[rd.nextInt(strRandomList.length)];//随机取strRandomList 的项值
				 }

			querymap.put("param",pwd);

			querymap.put("templateid","254979");
			System.out.println("新密码为:"+pwd);
			String data = SendCode.getSendCodeMessage(querymap);
			System.out.println(data);
			JSONObject json = JSON.parseObject(data);
			if (json.getString("respCode").equals("0000")){
				System.out.println("发送成功");

				String encodedPassword = ShiroConfig.shiroEncryption(pwd,phone);
				member.setPassword(encodedPassword);
				Integer updatePassword = memberService.updateMember(member);
                model.addAttribute("phone",phone);
				return "/forgetPasswordComplete" ;
			}else {
				System.out.println("验证码发送失败，失败原因为:" +json.getString("respDesc"));
				map.put("msg"  ,"重置密码失败请联系管理员!");
				return "/forgetPassword";
			}


	}




	/**
	 * 忘记密码页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/forgetPassword")
	public String forgetPassword(Model model){
		return "/forgetPassword";
	}


	/**
	 * 发送验证码
	 * @param jsonText
	 * @return
	 */
	@PostMapping("/sendCode")
	@ResponseBody
	public String sendCode(@RequestBody String jsonText){

		JSONObject jsonObject = (JSONObject) JSON.parse(jsonText);
		System.out.println("111111");
		String phone = jsonObject.getString("phone");
		Map<String,String> map = new HashMap<>();
		Map<String,Object> set_map =SettingUtils.getReadXml(pathUrl) ;
		map.put("url",String.valueOf(set_map.get("shortMessageUrl")));
		map.put("accountSid",String.valueOf(set_map.get("accountSid")));
		map.put("authToken",String.valueOf(set_map.get("authToken")));
		map.put("phone",phone);
		Random random = new Random();
		String code = String.valueOf(random.nextInt(1000000));
		code = String.format("%0"+6+"d",Integer.parseInt(code));
		map.put("param",code);

		map.put("templateid",jsonObject.getString("templateid"));
		System.out.println("验证码为:"+code);
		String data = SendCode.getSendCodeMessage(map);
		System.out.println(data);
		JSONObject json = JSON.parseObject(data);
		if (json.getString("respCode").equals("0000")){
			System.out.println("发送成功");
			Subject subject = SecurityUtils.getSubject();
			subject.getSession().setAttribute(phone,code);
			return  ResultUtil.success("验证码发送成功",null);
		}else {
			System.out.println("验证码发送失败，失败原因为:" +json.getString("respDesc"));
			return ResultUtil.error("验证码发送失败");
		}

	}


	/**
	 * 发送验证码邮箱
	 * @return
	 */
	@PostMapping("/sendCodeEmail")
	@ResponseBody
	public String sendCodeEmail(@RequestBody String jsonText){

		JSONObject jsonObject = (JSONObject) JSON.parse(jsonText);
		String email = jsonObject.getString("email");


		Random random = new Random();
		String code = String.valueOf(random.nextInt(1000000));
		System.out.println("您正在用此邮箱注册sp平台账号，验证码为"+code);

      String content = "您正在用此邮箱注册sp平台账号，验证码为"+code;
		MailInfo mailInfo = new MailInfo(aliyunSendEmailAccount,
				aliyunSendEmailPassword,
				email, "surgi-plan",
				email, "sp平台邮箱注册", content,null);
		boolean flags = MailSendUtils.sendEmail(mailInfo);
		System.err.println("邮件发送结果=="+flags);
		if (flags){
			Subject subject = SecurityUtils.getSubject();
			subject.getSession().setAttribute(email,code);
			return  ResultUtil.success("验证码发送成功!",null);
		}else{
			return ResultUtil.error("验证码发送失败!");
		}
	}




	//动态插入订单
	@RequestMapping("/ordersave")
	public String ordersave(Model model){
		List<Member> members = memberService.getMemberList(null);
		for (Member member : members) {
			List<Map> receivers = receiverService.getReceiver(member.getId());
			if (receivers.size()>0){
				Map receiger = receivers.get(0);
				Order order = new Order();
				order.setCreateDate(new Date());
				order.setModifyDate(new Date());
				order.setAddress(receiger.get("address").toString());
				order.setAmountPaid(new BigDecimal(0));
				Long areaId = Long.valueOf(receiger.get("area").toString());
				Area area = areaService.selectById(areaId);
				order.setAreaName(area.getFullName());
				order.setConsignee(member.getName());
				order.setCouponDiscount(new BigDecimal(0));
				Date expire = new Date();
				Calendar   calendar = new GregorianCalendar();
				calendar.setTime(expire);
				calendar.add(calendar.DATE,7); //把日期往后增加一天,整数  往后推,负数往前移动
				expire=calendar.getTime(); //这个时间
				order.setExpire(expire);
				order.setFee(new BigDecimal(0));
				order.setFreight(new BigDecimal(0));
				order.setInvoiceTitle("");
				order.setIsAllocatedStock(false);
				order.setMemo("测试");
				order.setOffsetAmount(new BigDecimal(0));
				Random random = new Random();
				int orderStatus = random.nextInt(5)-1;//订单状态:0未确认1确认2已完成3已取消
				order.setOrderStatus(orderStatus);
				order.setPhone(receiger.get("phone").toString());
				order.setPoint(0L);
				order.setPromotion("");
				order.setPromotionDiscount(new BigDecimal(0));
				order.setShippingMethodName("");
				int shippingStatus = 0;
				order.setPaymentStatus(0);
				order.setEvaluate(0);
				if (orderStatus==1){
					int suijishu = random.nextInt(10);
					int qumo = suijishu%2;
					if (qumo!=0){
						qumo = 2;
					}
					shippingStatus = qumo;
				}
				if (orderStatus==2){
					shippingStatus = 5;
				}
				order.setShippingStatus(shippingStatus);
				String sn = "SP"+System.currentTimeMillis();
				order.setSn(sn);
				order.setTax(new BigDecimal(0));
				order.setArea(areaId);
				order.setMember(member.getId());

				order.setPaymentMethodName("");
				if (shippingStatus==2||shippingStatus==5){
					order.setPaymentStatus(2);
					int a = random.nextInt(4)+1;
					order.setPaymentMethod(Long.valueOf(a));
					String [] paymengMethodName = {"支付宝","微信","银联","余额"};
					order.setPaymentMethodName(paymengMethodName[a-1]);
					order.setShippingMethod(1l);
					order.setPaidDate(new Date());

				}
				order.setIsDeleted(false);
				order.setHospital(receiger.get("hospital").toString());
				order.setIsMemberDelete(false);
				order.setIsMakeInvoice(false);
				Map query_map = new HashMap();
				query_map.put("isAddService",1);
				query_map.put("status",2);
				query_map.put("isMarketable",true);
				List<Product> productList = productService.getProducts(query_map);
				int productRandom =  random.nextInt(productList.size());
				Product product = productList.get(productRandom);
				order.setType(product.getIsAddService().longValue());
				order.setEmail(receiger.get("email").toString());
				order.setCompanyId(product.getCompanyId());
				Company company = companyService.selectByPrimaryKey(product.getCompanyId());
				order.setCompanyName(company.getName());
				order.setReceiverId(Long.valueOf(receiger.get("id").toString()));
				Map queryMap = new HashMap();
				queryMap.put("companyId",company.getId());
				queryMap.put("isSystem",true);
				Admin admin = adminService.getSystemCompanyAdmin(queryMap);
				order.setOperator(admin.getId());
				orderService.insertSelective(order);
				OrderItem orderItem = new OrderItem();
				orderItem.setCreateDate(new Date());
				orderItem.setModifyDate(new Date());
				orderItem.setFullName(product.getFullName());
				orderItem.setIsGift(false);
				orderItem.setName(product.getName());
				Map productMap = new HashMap();
				productMap.put("product",product.getId());
				List<Map> productPurchases = productPurchaseService.selectByProductParent(productMap);
				Map productPurchase = productPurchases.get(random.nextInt(productPurchases.size()));
				BigDecimal price =  new BigDecimal(productPurchase.get("price").toString());
				orderItem.setPrice(price);
				orderItem.setProductPurchaseId(Long.valueOf(productPurchase.get("purchase_id").toString()));
				orderItem.setPurchaseName(productPurchase.get("name").toString());
				Integer quantity = random.nextInt(10)+1;
				orderItem.setQuantity(quantity);
				orderItem.setReturnQuantity(0);
				if (order.getShippingStatus()==2||order.getShippingStatus()==7){
					orderItem.setReturnQuantity(quantity);
					BigDecimal amountPaid = price.multiply(new BigDecimal(quantity));
					order.setAmountPaid(amountPaid);
					orderService.updateOrder(order);
				}
				orderItem.setSn(product.getSn());
				orderItem.setThumbnail(product.getImage());
				orderItem.setWeight(0);
				orderItem.setOrders(order.getId());
				orderItem.setIsDeleted(false);
				orderItem.setCompany(product.getCompanyId());
				orderItemService.insert(orderItem);






			}
		}

		return "/forgetPassword";
	}




}
