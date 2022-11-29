package com.tongfu.service;

import com.tongfu.dao.CartDao;
import com.tongfu.dao.ProductCategoryDao;
import com.tongfu.dao.ProductDao;
import com.tongfu.entity.Member;
import com.tongfu.entity.ProductCategory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//订单列表计算价格
@Service("index")
public class Index {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    public List<Map<String, List<Map<String,Object>>>> getList(){


        Map<String ,Object> query_map = new HashMap<>();
        query_map.put("position",1);
        List<Map<String, List<Map<String,Object>>>> listcate0 = new ArrayList<>();
        List<Map<String ,Object> > productCategorys = productCategoryDao.selectByParents2(query_map);
        for (Map<String, Object> productCategory : productCategorys) {
            Map<String,List<Map<String,Object>>> mapcate=new HashMap<>();
            Long type = (Long) productCategory.get("type");
            List<Map<String,Object>> listcate2 = new ArrayList<>();
            if (type==2){
                Map<String ,Object> queryMap = new HashMap<>();
                queryMap.put("parent",productCategory.get("id"));
                listcate2 = productCategoryDao.selectByParents2(queryMap);
                mapcate.put(String.valueOf(productCategory.get("name")),listcate2);
            }else {
                listcate2.add(productCategory);
                mapcate.put(String.valueOf(productCategory.get("name")),listcate2);
            }
            listcate0.add(mapcate);
        }

        return listcate0;
    }

    public List<Map<String, Object>> productCategorys(){
        Map<String ,Object> query_map = new HashMap<>();
        query_map.put("type",1);
        List<Map<String ,Object> > productCategorys = productCategoryDao.selectByParents2(query_map);
        return productCategorys;
    }

}
