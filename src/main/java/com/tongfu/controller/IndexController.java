package com.tongfu.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.MapUtil;
import com.tongfu.dao.WebsiteStatisticsDao;
import com.tongfu.entity.*;
import com.tongfu.entity.ProductCategory;
import com.tongfu.service.*;
import javafx.beans.binding.ObjectExpression;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
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

    @RequestMapping("/")
    public String index(Model model) throws UnsupportedEncodingException {
        System.out.print("123");
        //??????????????????
        Map<String,Object> map=new HashMap<>();
        map.put("adPosition",adpositiontop);//????????????

        List<Ad> list= adService.listAd(map);
        model.addAttribute("adlistHead",list);
        //??????????????????
        map.put("adPosition",adpositionmiddle);
        List<Ad> list2= adService.listAd(map);
        model.addAttribute("adlistMiddle",list2);
        //??????????????????
        map.put("adPosition",adpositionfoot);
        List<Ad> list3= adService.listAd(map);
        model.addAttribute("adlistFoot",list3);

        model.addAttribute("pathUrl",pathUrl);

//        Subject subject = SecurityUtils.getSubject();
//        subject.getSession().setAttribute("pathUrl",pathUrl);
        List<ProductCategory> listcate3= pcService.selectByParents(0L,1l,null);
        model.addAttribute("categoryList",listcate3);

        List<Company> companies = companyService.companys(null);
        model.addAttribute("companies",companies);
        map.put("isTop",2);
        map.put("isPayment",2);
        map.put("status",2);
//        map.put("type",1);//?????????
//        List<Learning> learnings1 = learningService.getAll(map);
//        model.addAttribute("learnings1",learnings1);
//        map.put("type",3);//?????????
        map.put("isExpire",true);//?????????
        List<Learning> learnings3 = learningService.getAll(map);
        model.addAttribute("learnings3",learnings3);

        List<KnowhowArticle> knowhowArticleList = knowhowArticleService.getAll(null);
        model.addAttribute("knowhowArticleList",knowhowArticleList);
        List<Aboutus> aboutusList = aboutusService.getAll(null);
        model.addAttribute("aboutusList",aboutusList);
        map.put("category",5);//????????????
        List<Map> selectList = articleService.selectArticle(map);
        if (selectList.size()>8){
            selectList = selectList.subList(0,8);
        }
        model.addAttribute("selectList",selectList);
        return "/index";
    }



    //    ???????????????
    @ResponseBody
    @RequestMapping(value = "/saveWebsiteHits",method = RequestMethod.POST)
    public int updateProductHits(String ip){
        WebsiteStatistics websiteStatistics = new WebsiteStatistics();
        String data= MapUtil.getLatLng(ip,mapKey);
        JSONObject jsonObject=JSONObject.parseObject(data);
        String result = jsonObject.getString("result");
        JSONObject resultObject=JSONObject.parseObject(result);
        String location = resultObject.getString("location");
        JSONObject locationObject=JSONObject.parseObject(location);
        String lat =locationObject.getString("lat");//??????
        String lng =locationObject.getString("lng");//??????
        websiteStatistics.setIp(ip);
        websiteStatistics.setIplat(lat);
        websiteStatistics.setIplng(lng);
        String adInfo = resultObject.getString("ad_info");
        JSONObject adInfoObject=JSONObject.parseObject(adInfo);
        String nation=adInfoObject.getString("nation");
        String province=adInfoObject.getString("province");
        String city=adInfoObject.getString("city");
        String district=adInfoObject.getString("district");
        String adcode=adInfoObject.getString("adcode");
        websiteStatistics.setNation(nation);
        websiteStatistics.setProvince(province);
        websiteStatistics.setCity(city);
        websiteStatistics.setDistrict(district);
        websiteStatistics.setAdcode(adcode);
        websiteStatistics.setHits(1l);
        websiteStatistics.setCreateDate(new Date());


        return websiteStatisticsService.insertSelective(websiteStatistics);

    }

    //????????????
    @RequestMapping(value = "/gotoproblem")
    public String gotoproblem(Model model){
        model.addAttribute("type","gotoproblem");
        return "/foot/problem";

    }

    //????????????-????????????
    @RequestMapping(value = "/gotoproblemsqff")
    public String gotoproblemsqff(Model model){
        model.addAttribute("type","gotoproblemsqff");
        return "/foot/pro_sqff";

    }

    //????????????-????????????
    @RequestMapping(value = "/gotoproblemsqhs")
    public String gotoproblemsqhs(Model model){
        model.addAttribute("type","gotoproblemsqhs");
        return "/foot/pro_sqhs";

    }

    //????????????-????????????
    @RequestMapping(value = "/gotoproblemhqsq")
    public String gotoproblemhqsq(Model model){
        model.addAttribute("type","gotoproblemhqsq");
        return "/foot/pro_hqsq";

    }

    //????????????-????????????
    @RequestMapping(value = "/gotoproblemsdsq")
    public String gotoproblemsdsq(Model model){
        model.addAttribute("type","gotoproblemsdsq");
        return "/foot/pro_sdsq";

    }

    //????????????
    @RequestMapping(value = "/gotocontact")
    public String gotocontact(Model model){
        model.addAttribute("type","gotocontact");
        return "/foot/contact";

    }

    //????????????
    @RequestMapping(value = "/gotoaboutus")
    public String gotoaboutus(Model model,Long id){
        model.addAttribute("type","gotoaboutus");
        Map query_map = new HashMap();
        query_map.put("id",id);
        List<Aboutus> aboutusList = aboutusService.getAll(query_map);
        model.addAttribute("aboutusList",aboutusList);
        return "/foot/aboutus";

    }



    //???????????????????????????
    @RequestMapping(value = "/gotostatement")
    public String gotostatement(Model model,Long type){
        model.addAttribute("type","gotostatement");
        model.addAttribute("type",type);
        return "/foot/statement";

    }

    //????????????
    @RequestMapping(value = "/shengming")
    public String shengming(Model model){
        model.addAttribute("type","gotostatement");
        return "/foot/shengming";

    }

    //????????????
    @RequestMapping(value = "/gotoproblemfkfs")
    public String gotoproblemfkfs(Model model){
        model.addAttribute("type","gotoproblemfkfs");
        return "/foot/pro_fkfs";

    }

    //????????????
    @RequestMapping(value = "/gotoanquan")
    public String gotoanquan(Model model){
        model.addAttribute("type","gotoanquan");
        return "/foot/pro_anquan";

    }

    //??????
    @RequestMapping(value = "/gotowangyin")
    public String gotowangyin(Model model){
        model.addAttribute("type","gotowangyin");
        return "/foot/pro_wangyin";

    }

    //??????
    @RequestMapping(value = "/gotoyinhang")
    public String gotoyinhang(Model model){
        model.addAttribute("type","gotoyinhang");
        return "/foot/pro_yinhang";

    }

    //??????
    @RequestMapping(value = "/gotohuikuan")
    public String gotohuikuan(Model model){
        model.addAttribute("type","gotohuikuan");
        return "/foot/pro_huikuan";

    }

    //??????
    @RequestMapping(value = "/gotofapiao")
    public String gotofapiao(Model model){
        model.addAttribute("type","gotofapiao");
        return "/foot/pro_fapiao";

    }

    //????????????
    @RequestMapping(value = "/gotopsfs")
    public String gotopsfs(Model model){
        model.addAttribute("type","gotopsfs");
        return "/foot/pro_psfs";

    }

    //??????
    @RequestMapping(value = "/gotoshouhou")
    public String gotoshouhou(Model model){
        model.addAttribute("type","gotoshouhou");
        return "/foot/pro_shouhou";

    }

    //????????????
    @RequestMapping(value = "/gotognpy")
    public String gotognpy(Model model){
        model.addAttribute("type","gotognpy");
        return "/foot/pro_gnpy";

    }

    //EMS??????
    @RequestMapping(value = "/gotoems")
    public String gotoems(Model model){
        model.addAttribute("type","gotoems");
        return "/foot/pro_ems";

    }

    //????????????
    @RequestMapping(value = "/gotoyanhuo")
    public String gotoyanhuo(Model model){
        model.addAttribute("type","gotoyanhuo");
        return "/foot/pro_yanhuo";

    }

    //????????????
    @RequestMapping(value = "/gotoptkd")
    public String gotoptkd(Model model){
        model.addAttribute("type","gotoptkd");
        return "/foot/pro_ptkd";

    }

    //?????????????????????
    @RequestMapping(value = "/gotothzc")
    public String gotothzc(Model model){
        model.addAttribute("type","gotothzc");
        return "/foot/pro_thzc";

    }

    //?????????????????????
    @RequestMapping(value = "/gotosqth")
    public String gotosqth(Model model){
        model.addAttribute("type","gotosqth");
        return "/foot/pro_sqth";

    }

    //?????????????????????
    @RequestMapping(value = "/gotothjd")
    public String gotothjd(Model model){
        model.addAttribute("type","gotothjd");
        return "/foot/pro_thjd";

    }

    //?????????????????????
    @RequestMapping(value = "/gototkfs")
    public String gototkfs(Model model){
        model.addAttribute("type","gototkfs");
        return "/foot/pro_tkfs";

    }



    //????????????
    @RequestMapping(value = "/sellYourProduct")
    public String gotoContactUs(Model model){
        return "/sellYourProduct/contactUs";

    }


    //????????????
    @RequestMapping(value = "/sellYourProduct/subscription")
    public String gotoSubscription(Model model){
        return "/sellYourProduct/subscription";

    }

}
