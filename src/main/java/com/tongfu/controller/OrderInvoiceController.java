package com.tongfu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/orderInvoice")
public class OrderInvoiceController {


	@Autowired
	private InvoiceService invoiceService;

    @Autowired
    private PlatformTransactionManager txManager;

	@Autowired
    private OrderInvoiceService orderInvoiceService;

	@Autowired
    private OrderService orderService;

	@Autowired
    private MemberService memberService;

	@Autowired
    private OrderItemService orderItemService;

    @Value("${path-url}")
    private String pathUrl;



    //发票申请页面
    @RequestMapping("/add")
    public String add(Model model,Long orderId){
        model.addAttribute("activeNone","invoice");//打开左侧导航
        model.addAttribute("orderId",orderId);
        return "/orderInvoice/add";
    }

    //我的发票
    @RequestMapping("/list")
    public String list(Model model ,@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "4")Integer pageSize){
        Member member = memberService.getCurrent();
        Map query_map = new HashMap();
        query_map.put("memberId",member.getId());

        Page<Map> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map> orderInvoices = orderService.getOrderInvoice(query_map);
        for (Map orderInvoice : orderInvoices) {
            Long id = (Long) orderInvoice.get("id");
            List<OrderItem> orderItems = orderItemService.selectByOrderid(id);
            orderInvoice.put("orderItems",orderItems);
        }
        PageInfo<Map> pageInfo = new PageInfo<Map>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        pageInfo.setList(orderInvoices);
        model.addAttribute("page",pageInfo);


        model.addAttribute("pathUrl",pathUrl);
        model.addAttribute("orderInvoices",orderInvoices);
        query_map.put("member",member.getId());
        query_map.put("type",1);
        Invoice invoice = invoiceService.selectByMember(query_map);
        model.addAttribute("invoice",invoice==null?new Invoice():invoice);
        model.addAttribute("activeNone","invoice");//打开左侧导航
        return "/orderInvoice/list";
    }


    //提交发票
    @RequestMapping("/save")
    public String add(Model model,OrderInvoice orderInvoice){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            Member member = memberService.getCurrent();
            Order order = orderService.selectById(Long.valueOf(orderInvoice.getOrderId()));
            orderInvoice.setModifyDate(new Date());
            orderInvoice.setCreateDate(new Date());
            orderInvoice.setIsDelete(false);
            orderInvoice.setIsMakeInvoice(1L);
            orderInvoice.setMember(member.getId().toString());
            orderInvoice.setAmount(order.getAmountPaid());
            int i = orderInvoiceService.insertSelective(orderInvoice);
            if (i>0){
                order.setIsMakeInvoice(true);
                orderService.updateOrder(order);
            }
            txManager.commit(status);
            return  "redirect:/orderInvoice/list";
        }catch (Exception e){
            System.out.println("事务回滚了");
            //强制手动事务回滚
            txManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  "redirect:/orderInvoice/list";
        }


    }


    //发票详情页
    @RequestMapping("/view")
    public String view(Model model ,Long id){
        OrderInvoice orderInvoice = orderInvoiceService.selectByPrimaryKey(id);
        Order order = orderService.selectById(Long.valueOf(orderInvoice.getOrderId()));
        model.addAttribute("orderInvoice",orderInvoice);
        model.addAttribute("order",order);
        model.addAttribute("activeNone","invoice");//打开左侧导航
        return "/orderInvoice/view";
    }




}
