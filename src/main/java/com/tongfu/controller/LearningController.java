package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.MapUtil;
import com.tongfu.Util.ResultUtil;
import com.tongfu.Util.WeiXinUtils;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学术活动
 */
@Controller
@RequestMapping("/learning")
public class LearningController {

    @Value("${path-url}")
    String pathurl;

    @Autowired
    private CompanyService companyService;//公司


    @Autowired
    private LearningService learningService;//

    @Autowired
    private LearningSignupService learningSignupService;

    @Autowired
    private AdService adService;

    @Autowired
    private MemberService memberService;

    //学术活动搜索页广告
    @Value("${adpositionLearning}")
    private String adpositionLearning;

    @Value("${wxAppid}")
    private String wxAppid;

    @Value("${wxSecret}")
    private String wxSecret;

    @Value("${map-key}")
    private String mapKey;

    @Autowired
    private ArticleStatisticsService articleStatisticsService;//文章统计

    /**
     * 学术活动详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public String view(Model model,Long id) {


        Learning learning =  learningService.selectByPrimaryKey(id);
        learning.setHits(learning.getHits()+1);
        learningService.updateByPrimaryKeySelective(learning);
        Company company = companyService.selectCompanyById(learning.getCompanyId());
        ArticleStatistics articleStatistics = new ArticleStatistics();
        articleStatistics.setActicleId(id);
        articleStatistics.setCompanyId(learning.getCompanyId());
        articleStatistics.setCreateDate(new Date());
        articleStatistics.setTableType("2");
        articleStatisticsService.insertSelective(articleStatistics);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",4);
        model.addAttribute("learning",learning);
        model.addAttribute("appId",wxAppid);
        return "/learning/view";

    }

    /**
     * 学术活动报名
     * @param model
     * @return
     */
    @RequestMapping("/signUp")
    public String signUp(Model model, String code, Long learningId, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        System.out.println("学术活动报名，code:"+code+",type:"+learningId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
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
        if (member==null){
            member = new Member();
        }
        model.addAttribute("userinfojson",userinfojson.toJSONString());//微信信息
        System.out.println(userinfojson.toJSONString());
        model.addAttribute("member",member);
        model.addAttribute("learningId",learningId);
        return "/learning/signUp";

    }


    /**
     * 学术活动报名
     * @return
     */
    @PostMapping("/submit")
    @ResponseBody
    public Object submit(@RequestBody JSONObject jsonObject) {

        System.out.println("学术活动报名数据:"+jsonObject);
        Map<String,Object> map=new HashMap<>();
        map.put("openId",jsonObject.getString("openId"));
        Member member = memberService.getMember(map);
        if (member==null){
            member = new Member();
            String mobile = jsonObject.getString("phone");
            member.setUsername(mobile);
            String encodedPassword = ShiroConfig.shiroEncryption(mobile,mobile);
            member.setOpenId(jsonObject.getString("openId"));
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
				member.setRegisterIp(jsonObject.getString("ip"));
				String data= MapUtil.getLatLng(jsonObject.getString("ip"),mapKey);
				JSONObject jsonObjectMap=JSONObject.parseObject(data);
				String result = jsonObjectMap.getString("result");
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
            memberService.insertMember(member);
        }

        LearningSignup learningSignup = JSONObject.parseObject(jsonObject.toString(),LearningSignup.class);
        if (learningSignup.getId()!=null){
            int updateByPrimaryKeySelective = learningSignupService.updateByPrimaryKeySelective(learningSignup);
            if (updateByPrimaryKeySelective>0){
//            Learning learning = learningService.selectByPrimaryKey(learningSignup.getLearningId());
//            HttpUtil.post(learning.getSignupEntrance(),jsonObject.toJSONString());//给对方发送数据
                return ResultUtil.success("修改成功!",learningSignup);
            }
        }else {
            learningSignup.setCreateDate(new Date());
            int insertSelective = learningSignupService.insertSelective(learningSignup);
            if (insertSelective>0){
//            Learning learning = learningService.selectByPrimaryKey(learningSignup.getLearningId());
//            HttpUtil.post(learning.getSignupEntrance(),jsonObject.toJSONString());//给对方发送数据
                return ResultUtil.success("操作成功!",learningSignup);
            }
        }



        return ResultUtil.error("操作失败");
    }

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       @RequestParam(defaultValue = "1") Long type,Long id,String keywords) {
        Company company = companyService.selectCompanyById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",id);
//        map.put("type",type);
        map.put("status",2);
        map.put("title",keywords);
        Page<Learning> page  = PageHelper.startPage(pageNum,pageSize);
        List<Learning> all = (List<Learning>) learningService.getAll(map);
        PageInfo<Learning> pageInfo = new PageInfo<Learning>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(all);
        model.addAttribute("page",pageInfo);
        model.addAttribute("path",pathurl);
        model.addAttribute("company",company);
        model.addAttribute("active",4);
        model.addAttribute("type",type);
        model.addAttribute("appId",wxAppid);
        return "/learning/list";

    }


    /**
     * 首页学术活动搜索
     * @param model
     * @param pageNum
     * @param pageSize
     * @param keywords
     * @return
     */
    @RequestMapping("/seachList")
    public String seachList(Model model,@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String keywords) {

        Map<String,Object> map = new HashMap<>();
//        map.put("type",type);
        map.put("status",2);
        map.put("title",keywords);

        Page<Learning> page  = PageHelper.startPage(pageNum,pageSize);
        List<Learning> all = (List<Learning>) learningService.getAll(map);
        PageInfo<Learning> pageInfo = new PageInfo<Learning>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(all);
        model.addAttribute("page",pageInfo);
        model.addAttribute("path",pathurl);
        map.put("adPosition",adpositionLearning);//学术活动搜索页广告
        List<Ad> list= adService.listAd(map);
        model.addAttribute("adListHead",list);
        return "/learning/searchList";

    }


    /**
     * 实时检索学术活动
     * @param jsonObject
     * @return
     */
    @RequestMapping("/searchLearningTitle")
    @ResponseBody
    public String searchLearningTitle(@RequestBody JSONObject jsonObject){
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        List<Learning> list =  learningService.getAll(query_map);
        return JSONObject.toJSONString(list);
    }


}
