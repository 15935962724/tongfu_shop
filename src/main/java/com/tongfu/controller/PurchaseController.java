package com.tongfu.controller;

import com.tongfu.entity.ProductPurchase;
import com.tongfu.entity.Purchase;
import com.tongfu.service.ProductPurchaseService;
import com.tongfu.service.PurchaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @Autowired
    ProductPurchaseService productPurchaseService;

    @ResponseBody
    @RequestMapping(value = "/selectPurchase",method = RequestMethod.POST)
    public List<Purchase> selectPurchase(Model model, @Param("id") Long id,@Param("parentid") Long parentid) {
        //根据商品id和父类id查询购买方式下的购买规格
        Map<String,Object> purchasemap=new HashMap<>();
        purchasemap.put("productId",id);
        purchasemap.put("parent",parentid);
        List<Purchase> list = purchaseService.selectPurchase(purchasemap);
        return list;

    }



    @ResponseBody
    @RequestMapping(value = "/selectGuige",method = RequestMethod.POST)
    public Object selectGuige(Model model, @Param("id") Long id,@Param("parentid") Long parentid) {
        //根据商品id和父类id查询购买方式下的购买规格
        Map<String,Object> purchasemap=new HashMap<>();
        purchasemap.put("productId",id);
        purchasemap.put("parent",parentid);
        List<Map<String,Object>> list = purchaseService.selectGuige(purchasemap);
        return list;

    }

    @ResponseBody
    @RequestMapping(value = "/selectById",method = RequestMethod.POST)
    public ProductPurchase selectGuige(Model model, @Param("id") Long id) {
        //根据规格id查询规格，从而获取价格
        ProductPurchase productPurchase = productPurchaseService.selectById(id);
        return productPurchase;

    }
}
