package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.*;
import com.tongfu.config.CustomToken;
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
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("wexin")
@RequestMapping("/weixin")
public class IndexController {

    @Autowired
    private     AdService adService;
    @Autowired
    private ProductCategoryService pcService;

    @Autowired
    private WebsiteStatisticsService websiteStatisticsService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LearningService learningService;

    @Autowired
    private AboutusService aboutusService;

    @Autowired
    private KnowhowArticleService knowhowArticleService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;


    @Value("${path-url}")
    private String pathUrl;

    @Value("${adpositiontop}")
    private Long adpositiontop;

    @Value("${adpositionmiddle}")
    private Long adpositionmiddle;

    @Value("${adpositionfoot}")
    private Long adpositionfoot;

    @Value("${map-key}")
    private String mapKey;

    @Value("${wxAppid}")
    private String wxAppid;

    @Value("${wxSecret}")
    private String wxSecret;

    /**
     * 微信登录
     * @param model
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/wxLogin")
    public String index(Model model, String code,Integer type,HttpServletRequest request) {
        System.out.println("当前请求路径:"+request.getRequestURI());

        Subject subject = SecurityUtils.getSubject();
        System.out.println("code:"+code+",type:"+type);
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
        if (member!=null){
            CustomToken token = new CustomToken(member.getUsername());
            subject.login(token);
        }
        model.addAttribute("userinfojson",userinfojson);//微信信息
        model.addAttribute("isRegister",member!=null);
        List<String> urls = new ArrayList<>();
        urls.add("/pages/index/index");
        urls.add("/pages/shop/classification&id=24&name=分析软件");
        urls.add("/pages/shop/classification&id=25&name=计划软件");
        urls.add("/pages/shop/classification&id=27&name=人工智能");
        urls.add("/pages/service/list&id=4&name=精英团队");
        urls.add("/pages/service/list&id=5&name=高端访谈");
        urls.add("/pages/service/list&id=6&name=学术活动");
        urls.add("/pages/service/list&id=7&name=最新新闻");
        urls.add("/pages/service/list&id=8&name=国际前沿");
        urls.add("/pages/service/list&id=9&name=精品文章");
        urls.add("/pages/my/index");
//        return request.getRequestURI()+url;
        model.addAttribute("urls",urls.get(type-1));
        System.out.println("跳转的地址"+urls.get(type-1));
        return "/weixin/wxLogin";
    }


    /**
     * 微信端首页接口
     * @return
     * @throws UnsupportedEncodingException
     */

    @RequestMapping("/indexData")
    @ResponseBody
    public String indexData(@RequestBody JSONObject jsonObject){
        long start = System.currentTimeMillis();
        JSONObject returnJson = new JSONObject();
        Map<String,Object> map=new HashMap<>();
        if (jsonObject.getInteger("index")==1){
            //首页头部广告
            map.put("adPosition",adpositiontop);//首页头部
            List<Ad> list= adService.listAd(map);
            returnJson.put("adlistHead",list);
        }
        if (jsonObject.getInteger("index")==2){
            List<ProductCategory> listcate3= pcService.selectByParents(0L,1l,null);
            returnJson.put("categoryList",listcate3);

        }

        if (jsonObject.getInteger("index")==3){
            map.put("isTop",2);
            map.put("isPayment",2);
            map.put("status",2);
//        map.put("type",1);//研讨会
//        List<Learning> learnings1 = learningService.getAll(map);
//        model.addAttribute("learnings1",learnings1);
//        map.put("type",3);//培训班
            map.put("isExpire",true);//未过期
            List<Learning> learnings3 = learningService.getAll(map);
            returnJson.put("learnings3",learnings3);
        }

        if (jsonObject.getInteger("index")==4){
            //首页脚部广告
            map.put("adPosition",adpositionfoot);
            List<Ad> list3= adService.listAd(map);
            returnJson.put("adlistFoot",list3);

        }

        if (jsonObject.getInteger("index")==5){
            List<Company> companies = companyService.companys(null);
            returnJson.put("companies",companies);


        }
        if (jsonObject.getInteger("index")==6){
            List<Aboutus> aboutusList = aboutusService.getAll(null);
            returnJson.put("aboutusList",aboutusList);
        }

        long end = System.currentTimeMillis();
        System.err.println("总耗时:"+(end-start)+"毫秒");
        return  ResultUtil.success(returnJson);
    }


    /**
     * 首页搜索
     * @return
     */
    @RequestMapping("/indexSearch")
    @ResponseBody
    public String indexSearch(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Map<String,Object> query_map = (Map<String, Object>) JSON.parse(jsonObject.toJSONString());
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List listData = new ArrayList<>();
        if (jsonObject.getInteger("type")==1){//产品
            listData =  productService.selectByCategory(query_map);
        }
        if (jsonObject.getInteger("type")==2){//新闻
            listData = articleService.getArticle(query_map);
        }
        if (jsonObject.getInteger("type")==3){//学术活动
            listData =  learningService.getAll(query_map);
        }
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(listData);
        returnJson.put("page",pageInfo);
        return  ResultUtil.success(returnJson);
    }



    /**
     * 跳转微信index首页
     * @param model
     * @param code
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/indexs")
    public String indexs(Model model, String code,String data) throws UnsupportedEncodingException {
        return "weixin/index";
    }





}
