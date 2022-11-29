package com.tongfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.MapUtil;
import com.tongfu.dao.MemberDao;
import com.tongfu.dao.ProductDao;
import com.tongfu.dao.ProductStatisticsDao;
import com.tongfu.entity.Member;
import com.tongfu.entity.Product;
import com.tongfu.entity.ProductStatistics;
import com.tongfu.service.ProductService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${map-key}")
    private String mapKey;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductStatisticsDao productStatisticsDao;

    @Autowired
    MemberDao memberDao;

    @Override
    public List<Map<String,Object>> selectByCategory(Map<String,Object> map) {

        return productDao.selectByCategory(map);
    }

    @Override
    public Product selectProductById(Long id) {
        return productDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateProductHits(Product record,String ip) {
        ProductStatistics productStatistics=new ProductStatistics();
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
//            Member member = memberDao.selectByUsername(String.valueOf(subject.getPrincipal()));
            Member member = (Member) SecurityUtils.getSubject().getPrincipal();
            productStatistics.setMember(member.getId());
        }
        productStatistics.setProduct(record.getId());
        productStatistics.setIp(ip);

        String data= MapUtil.getLatLng(ip,mapKey);
        JSONObject jsonObject=JSONObject.parseObject(data);
        String result = jsonObject.getString("result");
        JSONObject resultObject=JSONObject.parseObject(result);
        String location = resultObject.getString("location");
        JSONObject locationObject=JSONObject.parseObject(location);
        String lat =locationObject.getString("lat");//纬度
        String lng =locationObject.getString("lng");//经度
        productStatistics.setIplat(lat);
        productStatistics.setIplng(lng);
        String adInfo = resultObject.getString("ad_info");
        JSONObject adInfoObject=JSONObject.parseObject(adInfo);
        String nation=adInfoObject.getString("nation");
        String province=adInfoObject.getString("province");
        String city=adInfoObject.getString("city");
        String district=adInfoObject.getString("district");
        String adcode=adInfoObject.getString("adcode");
        productStatistics.setNation(nation);
        productStatistics.setProvince(province);
        productStatistics.setCity(city);
        productStatistics.setDistrict(district);
        productStatistics.setAdcode(adcode);
        productStatistics.setHits(1l);
        productStatistics.setCreateDate(new Date());
        productStatistics.setCompanyId(record.getCompanyId());
        productStatisticsDao.insertSelective(productStatistics);
        return productDao.updateProductHits(record);

    }

    @Override
    public int updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public List<Map<String, Object>> selectByCompanyCategory(Map<String, Object> map) {
        return productDao.selectByCompanyCategory(map);
    }

    @Override
    public int selectCountByCompanyCategory(Map<String, Object> map) {
        return productDao.selectCountByCompanyCategory(map);
    }

    @Override
    public List<Map<String, Object>> selectByKeywords(Map<String, Object> map) {
        return productDao.selectByKeywords(map);
    }

    @Override
    public List<Product> selectTopByHits(Integer topnum) {
        return productDao.selectTopByHits(topnum);
    }

    @Override
    public List<Map> getCompanyProducts(Map map) {
        return productDao.getCompanyProducts(map);
    }

    @Override
    public List<Map> getOrderProducts(Map map) {
        return productDao.getOrderProducts(map);
    }

    @Override
    public Integer getCountCompanys(Map query_map) {
        return productDao.getCountCompanys(query_map);
    }

    @Override
    public  List<Product> getProducts(Map query_map) {
        return productDao.getProducts(query_map);
    }

}
