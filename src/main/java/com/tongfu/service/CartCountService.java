package com.tongfu.service;

import com.tongfu.entity.Member;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

//计算购物车数量
@Service("cartCountService")
public class CartCountService {

    @Autowired
    private CartService cartService;

    public Integer getCartCount(){
        Subject subject = SecurityUtils.getSubject();
        Member member = (Member) SecurityUtils.getSubject().getPrincipal();
        Integer integer = cartService.selectCartQuantitySum(member.getId());
        return  integer==null?0:integer;
    }

}
