package com.tongfu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Article;
import com.tongfu.entity.ProductCategory;
import com.tongfu.entity.Workshop;
import com.tongfu.service.AdService;
import com.tongfu.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
@RequestMapping("/workshop")
public class WorkshopController {

    @Value("${path-url}")
    String pathurl;

    @Autowired
    WorkshopService workshopService;

    @Value("${adpositionythhead}")
    Long adpositionythhead;

    @Autowired
    AdService adService;

    @RequestMapping("/list")
    public String list(Model model, Integer type,String keywords,Integer years,Integer months,Integer iscarefully,Integer holddate,Integer letter,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "6")Integer pageSize){
        if(type==null){
            type = 1;//研讨会
        }
        //头部广告
        Map<String,Object> mapad=new HashMap<>();
        mapad.put("adPosition",adpositionythhead);
        List<Ad> listad= adService.listAd(mapad);
        model.addAttribute("adlistythhead",listad);
        //广告结束
        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        if(keywords!=null&&keywords.length()>0){
            map.put("keywords",keywords);
        }
        if(years!=null&&years!=0){
            map.put("years",years);
        }
        if(months!=null&&months!=0){
            map.put("months",months);

        }
        if(iscarefully!=null&&iscarefully!=0){
            map.put("iscarefully",iscarefully);

        }
        if(holddate!=null&&holddate!=0){
            map.put("holddate",holddate);

        }
        if(letter!=null&&letter!=0){
            map.put("letter",letter);

        }
        if(iscarefully!=null&&iscarefully!=0){
            map.put("iscarefully",iscarefully);

        }
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = workshopService.selectByType(map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(list);
        //model.addAttribute("list",list);
        model.addAttribute("path",pathurl);
        model.addAttribute("type",type);
        model.addAttribute("page",pageInfo);
        model.addAttribute("keywords",keywords);
        if(years!=null&&years!=0){
            model.addAttribute("ymstr",years+"年"+months+"月");
            model.addAttribute("years",years);
            model.addAttribute("months",months);
        }else{
            model.addAttribute("ymstr",0);
        }
        model.addAttribute("iscarefully",iscarefully);
        model.addAttribute("holddate",holddate);
        model.addAttribute("letter",letter);
        //循环最近6个月的年份月份
        List<String> listYM = getYearMonth(6);
        model.addAttribute("listYM",listYM);
        return "/workshop/list";
    }

    public List<String> getYearMonth(int num){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//当前年份
        int month = cal.get(Calendar.MONTH) + 1;//当前月份
        List<String> list = new ArrayList<>();
        for(int i=0;i<num;i++){

            if(month>=1){
                list.add(year+"年"+month+"月");
            }else if(month==0){
                month=12;
                year-=1;
                list.add(year+"年"+month+"月");
            }
            month-=1;
        }
        return list;
    }

    @RequestMapping("/view")
    public String view(Model model, Long workshopid){
        Workshop workshop = workshopService.selectById(workshopid);

        model.addAttribute("workshop",workshop);
        return "/workshop/view";
    }
}
