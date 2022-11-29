package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.Util.TxtUtil;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.EmailSend;
import com.tongfu.email.aliyun.MailInfo;
import com.tongfu.email.aliyun.MailSendUtils;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;


@Controller
@RequestMapping("/feiTainZhaoYe")
public class FeiTianController {

    @Value("${path-url}")
    private String pathurl;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ShippingService shippingService;

    @Autowired
    private ShippingItemService shippingItemService;

    //#邮箱账号
    @Value("${emailUserName}")
    private String emailUserName;

    //#发送者密码
    @Value("${emailPassword}")
    private String emailPassword;

    //#邮箱服务器地址
    @Value("${emailHost}")
    private String emailHost;

    //#主机端口
    @Value("${emailPort}")
    private String emailPort;

    //#发送者邮箱
    @Value("${emailUserName}")
    private String emailFromAddress;

    //阿里云发件人账户
    @Value("${aliyunSendEmailAccount}")
    private String aliyunSendEmailAccount;

    //阿里云发件人密码
    @Value("${aliyunSendEmailPassword}")
    private String aliyunSendEmailPassword;
    /**
     * 附件上传路径
     */
    @Value("${file-web-path}")
    private String fileWebPath;
    /**
     * 飞天自动回传激活码
     * @param jsonObject
     * @return
     * @throws IOException
     */
    @PostMapping("/authorizationCode")
    @ResponseBody
    public String authorizationCode(@RequestBody String jsonObject ) throws IOException {
        System.out.println("----------------------------------");
        System.out.println("飞天授权码:"+jsonObject);
        JSONObject jsonObjectData = JSON.parseObject(jsonObject);
        Long orderItemId = jsonObjectData.getLong("orderItemId");
        if (orderItemId==35){
            return ResultUtil.error("orderItemId:"+orderItemId+",已阻断！");
        }
        OrderItem orderItem = orderItemService.selectByPrimaryKey(jsonObjectData.getLong("orderItemId"));
        Order order = orderService.selectById(orderItem.getOrders());
        String code = jsonObjectData.getString("code");
        String staticPath= fileWebPath+"/orderItemCode";
        String fileName = orderItem.getOrders()+"_"+orderItem.getId()+"_"+orderItem.getPurchaseName()+"_"+orderItem.getQuantity();
        boolean b = TxtUtil.writeDataHubData(code, staticPath, fileName);
        if (b){
            String content = "你购买的“"+orderItem.getFullName()+"”产品，购买规格“"+orderItem.getPurchaseName()+"”，购买数量"+orderItem.getQuantity()+",请下载附件获取你的激活码";
            MailInfo mailInfo = new MailInfo(aliyunSendEmailAccount,
                    aliyunSendEmailPassword,
                    order.getEmail(), "surgi-plan",
                    order.getConsignee(), "发货通知", content,staticPath+"/"+fileName+".txt");
            boolean flags = MailSendUtils.sendEmail(mailInfo);
            System.err.println("邮件发送结果=="+flags);
            Map query_map = new HashMap();
            query_map.put("companyId",order.getCompanyId());
            query_map.put("isSystem",true);

            Admin admin = adminService.getSystemCompanyAdmin(query_map);
            if (flags){
                    Shipping shipping = new Shipping();
                    shipping.setCreateDate(new Date());
                    shipping.setModifyDate(new Date());
                    shipping.setFreight(new BigDecimal(0));
                    order.setFreight(new BigDecimal(0));
                    shipping.setMemo(order.getMemo());
                    shipping.setConsignee(admin.getUsername());
                    shipping.setOperator(admin.getName());
//				shipping.setOperatorPhone(operatorPhone);
                    shipping.setShippingMethod("邮件配送");
                    shipping.setSn("FHD"+System.currentTimeMillis());
                    shipping.setTrackingNo("");//运单号
                    shipping.setZipCode("");
                    shipping.setOrders(orderItem.getOrders());
                    shipping.setWeight(new BigDecimal(0));
                shippingService.insertSelective(shipping);
//				邮件配送 给用户邮箱发送激活码

                    List<Map<String,Object>> shippingItemList = new ArrayList<>();
                    Map<String,Object> shipping_item = new HashMap<>();
                    shipping_item.put("shipping",shipping.getId());
                    Boolean flag = true;


                        orderItem.setReturnQuantity(orderItem.getQuantity());
                        orderItem.setShippedQuantity(0);//未发货数量 等于购买数量减去已发货数量
                        orderItemService.updateByPrimaryKeySelective(orderItem);
                        Map<String,Object> shipping_item_map = new HashMap();
                        shipping_item_map.put("sn",orderItem.getSn());
                        shipping_item_map.put("quantity",orderItem.getQuantity());
                        shipping_item_map.put("name",orderItem.getName());
                        shipping_item_map.put("createDate",new Date());
                        shipping_item_map.put("modifyDate",new Date());
                        shipping_item_map.put("isDelete",false);
                        shippingItemList.add(shipping_item_map);
                        Long productPurchaseId = orderItem.getProductPurchaseId();//购买方式id
                        query_map.put("productPurchaseId",productPurchaseId);
                        query_map.put("status",1);
                        shipping_item.put("shippingItemList",shippingItemList);
                shippingItemService.insertSelectiveMap(shipping_item);

                    List<OrderItem> orderItems = orderItemService.selectByOrderid(order.getId());
                    for (OrderItem orderItem1 : orderItems) {
                        if (orderItem1.getReturnQuantity()<orderItem1.getQuantity()){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        order.setShippingStatus(2);
                        order.setShippingMethodName("线上发货");
                        order.setShippingMethod(1L);
                        content = "已发货";
                    }else {
                        order.setShippingStatus(1);
                        order.setShippingMethodName("线上发货");
                        order.setShippingMethod(1L);
                        content = "部分已发货";
                    }
                    order.setOrderStatus(1);
                    orderService.updateOrder(order);
                    OrderLog orderLog = new OrderLog();
                    orderLog.setCreateDate(new Date());
                    orderLog.setModifyDate(new Date());
                    orderLog.setOperator(admin.getUsername());
                    orderLog.setOperatorName(admin.getName());
                    orderLog.setContent("发货操作，邮件发货，"+content);
                    orderLog.setIsDeleted(false);
                    orderLog.setType(3);
                    orderLog.setOrders(orderItem.getOrders());
                    orderLogService.insertOrderLog(orderLog);
                    return ResultUtil.success();
            }else {
                return ResultUtil.error("给用户发送邮件失败");
            }
        }else {
            return ResultUtil.error("生产txt文件失败");
        }




    }



}
