package com.tongfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 判断用户是否购买过该产品
 */
@Service("productpurchase")
public class Productpurchase {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    public Boolean isProductPurchase(Long id){
        Map query_map = new HashMap();
        query_map.put("product",id);
        query_map.put("member",memberService.getCurrent().getId());
        Integer countAmount = orderService.isProductPurchase(query_map);
        return  countAmount>0;
    }

}
