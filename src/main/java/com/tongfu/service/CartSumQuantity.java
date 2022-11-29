package com.tongfu.service;

import com.tongfu.dao.CartDao;
import com.tongfu.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

//订单列表计算价格
@Service("cartSumQuantity")
public class CartSumQuantity {
    @Autowired
    CartDao cartDao;
    @Autowired
    private MemberService memberService;

    public int getCartQSum(){
        Member member = memberService.getCurrent();
        Integer sum = cartDao.selectCartSum(member.getId());
        int sum2 = 0;
        if(sum!=null){
            sum2 = sum;
        }
        return sum2;
    }

}
