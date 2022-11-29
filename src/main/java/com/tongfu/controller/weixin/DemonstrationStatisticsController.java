package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.MapUtil;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Controller("weixinDemonstrationStatistics")
@RequestMapping("/weixin/demonstrationStatistics")
public class DemonstrationStatisticsController {

    @Value("${map-key}")
    private String mapKey;

    @Autowired
    private ProductService productService;

    @Autowired
    private DemonstrationStatisticsService demonstrationStatisticsService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AreaService areaService;


    /**
     * 申请演示，申请试用，免费下载
     * @param jsonObject
     * @return
     */


        @RequestMapping("/save")
        @ResponseBody
        public String save(@RequestBody JSONObject jsonObject) {
            String demonName = jsonObject.getString("demonName");
            Long demonType = jsonObject.getLong("demonType");
            Long hospitalId = jsonObject.getLong("hospitalId");
            String hospitalName = jsonObject.getString("hospitalName");
            String department = jsonObject.getString("department");
            Long areaId = jsonObject.getLong("areaId");
            String address = jsonObject.getString("address");
            String mobile = jsonObject.getString("mobile");
            String email = jsonObject.getString("email");
            String content = jsonObject.getString("content");
            Long productId = jsonObject.getLong("prpductId");
            String ip = jsonObject.getString("ip");

        Member member = memberService.getCurrent();
        member.setName(demonName);
        //查询医院
        if (hospitalId==null){
            Hospital hospital = new Hospital();
            hospital.setCreateDate(new Date());
            hospital.setName(hospitalName);
            Area area = areaService.getParentArea(areaId);
            hospital.setAreaId(area.getId());
            hospital.setAddress(area.getName());
            hospitalService.insertSelective(hospital);
            hospitalId = hospital.getId();
        }
        member.setHospital(hospitalName);
        member.setDepartment(department);
        member.setArea(areaId);
//        member.setGrdistrict(areaService.selectById(areaId).getName());
        member.setEmail(email);
        member.setPhone(mobile);
        member.setMobile(mobile);
        member.setAddress(address);
        memberService.updateMember(member);

        DemonstrationStatistics demon = new DemonstrationStatistics();
        demon.setProduct(productId);
        demon.setMember(member.getId());
        demon.setCreateDate(new Date());
        demon.setIp(ip);
        String data= MapUtil.getLatLng(ip,mapKey);
        jsonObject=JSONObject.parseObject(data);
        String result = jsonObject.getString("result");
        JSONObject resultObject=JSONObject.parseObject(result);
        String location = resultObject.getString("location");
        JSONObject locationObject=JSONObject.parseObject(location);
        String lat =locationObject.getString("lat");//纬度
        String lng =locationObject.getString("lng");//经度
        demon.setIplat(lat);
        demon.setIplng(lng);
        String adInfo = resultObject.getString("ad_info");
        JSONObject adInfoObject=JSONObject.parseObject(adInfo);
        String nation=adInfoObject.getString("nation");
        String province=adInfoObject.getString("province");
        String city=adInfoObject.getString("city");
        String district=adInfoObject.getString("district");
        String adcode=adInfoObject.getString("adcode");
        demon.setNation(nation);
        demon.setProvince(province);
        demon.setCity(city);
        demon.setDistrict(district);
        demon.setAdcode(adcode);
        Product product = productService.selectProductById(productId);
        demon.setCompanyId(product.getCompanyId());
        demon.setArea(areaId);
        demon.setAddress(address);
        demon.setName(demonName);
        demon.setHospital(hospitalId);
        demon.setDepartment(department);
        demon.setMobile(mobile);
        demon.setContent(content);
        demon.setType(demonType);
        demon.setEmail(email);
        demon.setIsDelete(false);
        demon.setStatus(0l);
        int result1 = demonstrationStatisticsService.insertDemonstration(demon);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result1);
        if(demonType==2){
            String productPackage = product.getProductPackage();
            if(productPackage!=null){
                //下载包
                map.put("isPackage",1);
                map.put("package",product.getProductPackage());
            }else{
                //下载包地址
                map.put("isPackage",0);
                map.put("packageUrl",product.getProductPackageUrl());
            }
        }
        return ResultUtil.success();
    }


}
