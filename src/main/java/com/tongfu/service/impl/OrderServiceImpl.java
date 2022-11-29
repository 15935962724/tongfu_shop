package com.tongfu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.SettingUtils;
import com.tongfu.dao.*;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;//订单

    @Autowired
    private ReceiverDao receiverDao;//收货人

    @Autowired
    private InvoiceDao invoiceDao;//发票

    @Autowired
    private CartItemDao cartItemDao;//购物车项

    @Autowired
    private CartDao cartDao;//购物车

    @Autowired
    private ProductDao productDao;//产品

    @Autowired
    private ProductPurchaseDao productPurchaseDao;//商品和商品规格关联关系表

    @Autowired
    private PurchaseDao purchaseDao;//商品规格

    @Autowired
    private OrderItemDao orderItemDao;//订单项

    @Autowired
    private OrderLogDao orderLogDao;//订单日志

    @Autowired
    private MemberDao memberDao;//用户

    @Autowired
    private OrderInvoiceDao orderInvoiceDao;//发票

    @Autowired
    private MemberServiceImpl memberService;

    @Value("${path-url}")
    private String pathUrl;

    @Autowired
    private OrderService orderService;

    @Autowired
    private  OrderLogService orderLogService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductSaleService productSaleService;//商品购买记录

    @Autowired
    private MemberPointLogService memberPointLogService;//积分记录

    @Autowired
    private ProductMealDao productMealDao;//套餐

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;//付款单

    @Override
    public int insert(Order record){
        return orderDao.insert(record);
    }

    @Override
    public int insertSelective(Order record){
        return orderDao.insertSelective(record);
    }

    @Override
    @Transactional
    public Map insertOrder( Long oreceiverId, String myCartItemId, Integer invoiceType,Integer orderType,Integer isInvoice,Integer buytype,
                            Long goproductid,Long gopurchaseid,Integer gonum,Boolean isaddedvalue) {
        Map map = new HashMap();
//        try{
//            Member member = memberService.getCurrent();
//            Receiver receiver = receiverDao.selectByPrimaryKey(oreceiverId);//收件人
//            //根据当前用户和发票类型（普或专）查找发票
//            Invoice invoice = null;
//            Integer ptype = null;//0是个人，1是单位
//            if(invoiceType!=null){
//                Map<String,Object> mapInvoice=new HashMap<>();
//                mapInvoice.put("member",member.getId());
//                mapInvoice.put("type",invoiceType);
//
//                invoice = invoiceDao.selectByMember(mapInvoice);
////                ptype = invoice.getPtype();//0是个人，1是单位
//            }
//            //1、插入订单
//            Order order=new Order();
//                //增值服务订单
//            order.setType(isaddedvalue?1l:0l);//1增值服务订单  0普通订单
//            order.setCreateDate(new Date());
//            order.setModifyDate(new Date());
//            order.setAddress(receiver.getAddress());
//            order.setAmountPaid(new BigDecimal(0));//已付金额，付款之前是0
//            order.setAreaName(receiver.getAreaName());//地区全称
//            order.setConsignee(receiver.getConsignee());//收件人
//            order.setCouponDiscount(new BigDecimal(0));//减免金额 算法中做减法
//            Date dt= new Date();
//            long time= dt.getTime();
//            //获取7天的毫秒数
//            long sevenTime = 7*24*60*60*1000;
//            //当前时间毫秒数+7天的毫秒数=7天之后那天的毫秒数
//            long times = time + sevenTime;
//            //将毫秒数转日期
//            Date dat=new Date(times);
//            order.setExpire(dat);//订单到期日期，下单后往后推7天
//            order.setFee(new BigDecimal(0));//支付手续费 算法中做加法
//            order.setFreight(new BigDecimal(0));//运费 算法中做加法
////            if(invoiceType!=null&&invoiceType==0){
////                order.setInvoiceTitle(ptype==0?"个人":invoice.getPtitle());//发票抬头，只有普票有抬头 发票抬头，只有普票有抬头
////            }
//            order.setIsAllocatedStock(false);//是否已分配库存
//            order.setIsInvoice(!(isInvoice==0));//true  需要开发票  false 不需要开发票
//            order.setOffsetAmount(new BigDecimal(0));//调整金额 算法中做加法
//            order.setOrderStatus(0);//订单状态 0未确认
//            order.setPaymentMethodName("");//支付方式名称 暂无
//            order.setPaymentStatus(0);//支付状态 0未支付
//            order.setPhone(receiver.getPhone());//收件人电话
//            order.setPoint(0l);//积分？？？？？？？？？？？？？？？？？？？？后改
//            order.setPromotionDiscount(new BigDecimal(1));//促销折扣
//            order.setShippingMethodName("");//配送方式名称
//            order.setShippingStatus(0);//发货状态 0未发货
//            String sn = "SP"+System.currentTimeMillis();
//
//            order.setSn(sn);//订单编号 sp+毫秒数生成
//            order.setTax(new BigDecimal(0));//税金 算法中做加法
//            order.setArea(receiver.getArea());//tf_area表的主键
//            order.setMember(member.getId());//下单人
//            order.setEvaluate(0);//评价状态 0未评价
//            order.setIsDeleted(false);//是否删除
//            order.setReceiverAddress(receiver.getAddress());//详细地址
//            order.setIsMemberDelete(false);//用户是否删除订单
//            order.setZipCode(receiver.getZipCode());
//            order.setEmail(invoice.getPemail());//普票发票接收邮箱
////            if(invoiceType!=null){
////                order.setMakeType(ptype.longValue());//普票开票类型0个人 1企业
////                order.setInvoiceType(invoiceType.longValue());//发票类型 0普 1专
////                order.setReceivedType(invoiceType.longValue());
////                if (invoiceType==0){
////                    //普票
////                    order.setTaxno(invoice.getPidentityno());
////                }else{
////                    //专票
////                    order.setTaxno(invoice.getZidentityno());
////                }
////                order.setBank(invoice.getZbank());//专票开户行名称
////                order.setAccount(invoice.getZaccount());//专票账户
////                order.setRegAddress(invoice.getZregaddress());//专票注册地址
////                order.setRegPhone(invoice.getZregmobile());//专票注册电话
////            }
//            order.setIsMakeInvoice(false);//是否已开具发票，未开具
//            order.setType(orderType.longValue());//订单类型0普通订单 1增值服务订单
//            order.setCaseStatus(0);
//            orderDao.insertSelective(order);
////            orderId=order.getId();
//
//            //2、插入订单项
//            String[] myCartItemIds = null;
//            if(buytype==null){
//               myCartItemIds = myCartItemId.split(",");
//                List<OrderItem> listOrderItem = new ArrayList<>();
//                String productName="";//要存入order表的产品名称
//                for (String mycart : myCartItemIds) {
//
//                    Long mycartId=Long.valueOf(mycart);
//                    CartItem cartItem = cartItemDao.selectByPrimaryKey(mycartId);
//                    Product product = productDao.selectByPrimaryKey(cartItem.getProduct());
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setCreateDate(new Date());
//                    orderItem.setModifyDate(new Date());
//                    orderItem.setFullName(product.getFullName());
//                    orderItem.setIsGift(false);
//                    orderItem.setName(product.getName());
//                    ProductPurchase productPurchase = productPurchaseDao.selectByPrimaryKey(cartItem.getProductPurchase());//商品和规格的关联表
//                    orderItem.setPrice(productPurchase.getPrice());//单价
//                    orderItem.setQuantity(cartItem.getQuantity());//数量
//                    orderItem.setReturnQuantity(0);
//                    orderItem.setShippedQuantity(0);
//                    orderItem.setSn(sn);
//                    orderItem.setThumbnail(product.getImage());
//                    orderItem.setWeight(0);
//                    orderItem.setOrders(order.getId());//订单id
//                    orderItem.setProduct(product.getId());
//                    orderItem.setIsDeleted(false);
//                    orderItem.setCompany(product.getCompanyId());
//                    if(!product.getIsAddService()){
//                        orderItem.setCompanyReceiver(product.getCompanyId());
//                    }
//                    orderItem.setProductPurchaseId(productPurchase.getId());
//                    Purchase purchase = purchaseDao.selectByPrimaryKey(Long.valueOf(productPurchase.getPurchaseId()));
//                    orderItem.setPurchaseName(purchase.getName());
//                    listOrderItem.add(orderItem);
//                    productName=product.getName();
//                }
//                orderItemDao.insertList(listOrderItem);//插入订单项
//            }else{
//                //从立即购买直接跳转到结算页面的提交订单
//               List<OrderItem> listOrderItem = new ArrayList<>();
//                String productName="";//要存入order表的产品名称
//
//                    Product product = productDao.selectByPrimaryKey(goproductid);
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setCreateDate(new Date());
//                    orderItem.setModifyDate(new Date());
//                    orderItem.setFullName(product.getFullName());
//                    orderItem.setIsGift(false);
//                    orderItem.setName(product.getName());
//                    ProductPurchase productPurchase = productPurchaseDao.selectByPrimaryKey(gopurchaseid);//商品和规格的关联表
//                    orderItem.setPrice(productPurchase.getPrice());//单价
//                    orderItem.setQuantity(gonum);//数量
//                    orderItem.setReturnQuantity(0);
//                    orderItem.setShippedQuantity(0);
//                    orderItem.setSn(sn);
//                    orderItem.setThumbnail(product.getImage());
//                    orderItem.setWeight(0);
//                    orderItem.setOrders(order.getId());//订单id
//                    orderItem.setProduct(product.getId());
//                    orderItem.setIsDeleted(false);
//                    orderItem.setCompany(product.getCompanyId());
//                    if(!product.getIsAddService()){
//                        orderItem.setCompanyReceiver(product.getCompanyId());
//                    }
//                    orderItem.setProductPurchaseId(productPurchase.getId());
//                    Purchase purchase = purchaseDao.selectByPrimaryKey(Long.valueOf(productPurchase.getPurchaseId()));
//                    orderItem.setPurchaseName(purchase.getName());
//                    listOrderItem.add(orderItem);
//                    productName=product.getName();
//
//                orderItemDao.insertList(listOrderItem);//插入订单项
//            }
//
//            //order.setProductName(productName);
//            orderDao.updateByPrimaryKeySelective(order);//修改order表，给产品名称字段赋值
//            //发票信息表
//            if(order.getIsInvoice()){
//                Map<String,Object> orderInvoiceMap = MapObjUtil.object2Map(order);
//                orderInvoiceMap.put("orderId",order.getId());
//                orderInvoiceMap.put("amount",this.getTotalAmount(order.getId()));
//                orderInvoiceMap.put("createDate",new Date());
//                orderInvoiceMap.put("modifyDate",new Date());
//                orderInvoiceMap.put("isMakeInvoice",1);
//                Map<String,Object> mapInvoice=new HashMap<>();
//                mapInvoice.put("member",member.getId());
//                mapInvoice.put("type",invoiceType);
//                mapInvoice.put("ptype",ptype);
//                Invoice invoice1 = invoiceService.selectByMember(mapInvoice);
////                orderInvoiceMap.put("ptype",invoice1.getPtype());
////                orderInvoiceMap.put("ptitle",invoice1.getPtitle());
//                orderInvoiceMap.put("pcontent",invoice1.getPcontent());
//                //OrderInvoice orderInvoice = MapObjUtil.map2Object(orderInvoiceMap,OrderInvoice.class);
//                OrderInvoice orderInvoice = JSON.parseObject(JSON.toJSONString(orderInvoiceMap), OrderInvoice.class);
//                orderInvoiceDao.insertSelective(orderInvoice);
//            }
//            //发票信息表结束
//            //3、插入订单日志
//            OrderLog orderLog = new OrderLog();
//            orderLog.setCreateDate(new Date());
//            orderLog.setModifyDate(new Date());
//            orderLog.setContent("创建订单");//操作内容
//            orderLog.setOperator(member.getUsername());//前台用户名称(账号）
//            orderLog.setType(1);//用户类型1前台用户
//            orderLog.setOrders(order.getId());//订单表id
//            orderLog.setIsDeleted(false);
//            orderLog.setOperatorName(member.getName());//操作人姓名
//            orderLogDao.insertSelective(orderLog);//插入订单日志
//            //4、删除购物车
//            if(buytype==null){
//                for (String mycart : myCartItemIds) {
//                    Long mycartId=Long.valueOf(mycart);
//                    cartItemDao.deleteByPrimaryKey(mycartId);
//                }
//            }
//
//            List<OrderItem> listItem = orderItemService.selectByOrderid(order.getId());//查询orderitem
//
//            //计算订单金额并显示在页面上
//            BigDecimal totalAmount = orderService.getTotalAmount(order.getId());
//
//            map.put("order",order);
//            map.put("listItem",listItem);
//            map.put("totalAmount",totalAmount);
//            map.put("productName",listItem.size()>0?listItem.get(0).getName():"");
//
//            //发票
//            OrderInvoice orderInvoice = orderInvoiceDao.selectByOrderId(order.getId());
//            map.put("orderInvoice",orderInvoice==null?new OrderInvoice():orderInvoice);
//
//            //测试结束
//
//        }catch (Exception exception){
//            System.out.println("手动事务回滚"+exception.getMessage());
//
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }

        return map;


    }

    @Override
    @Transactional
    public Map<String, Object> saveProductMealOrder(ProductMealOrder productMealOrder) {
        Map<String, Object> return_map = new HashMap<>();

        try{
            Member member = memberService.getCurrent();
            Receiver receiver = receiverDao.selectByPrimaryKey(Long.valueOf(productMealOrder.getOreceiverId()));//收件人
            ProductMeal productMeal = productMealDao.selectByPrimaryKey(Long.valueOf(productMealOrder.getProductMealId()));//套餐id
            Product product = productDao.selectByPrimaryKey( productMeal.getProductId());//主产品id
            Product productPackage = productDao.selectByPrimaryKey( productMeal.getProductPackageId());//主产品id
            ProductPurchase productPurchase = productPurchaseDao.selectByPrimaryKey(productMeal.getProductPurchaseId());//主产品价格

            //1、插入订单
            Order order=new Order();
            //1增值服务订单 0 普通订单
            order.setType(product.getIsAddService());
            order.setCreateDate(new Date());
            order.setModifyDate(new Date());
            order.setAddress(receiver.getAddress());
            order.setAmountPaid(new BigDecimal(0));//已付金额，付款之前是0
            order.setAreaName(receiver.getAreaName());//地区全称
            order.setConsignee(receiver.getConsignee());//收件人

            BigDecimal couponDiscount =  (productPurchase.getPrice().add(productPackage.getPrice())).subtract(productMeal.getPrice());//减免金额等于 主产品价格加上培训包的价格减去套餐价
            order.setCouponDiscount(couponDiscount);//减免金额 算法中做减法
            Date dt= new Date();
            long time= dt.getTime();
            //获取7天的毫秒数
            long sevenTime = 7*24*60*60*1000;
            //当前时间毫秒数+7天的毫秒数=7天之后那天的毫秒数
            long times = time + sevenTime;
            //将毫秒数转日期
            Date dat=new Date(times);
            order.setExpire(dat);//订单到期日期，下单后往后推7天
            order.setFee(new BigDecimal(0));//支付手续费 算法中做加法
            order.setFreight(new BigDecimal(0));//运费 算法中做加法

            order.setIsAllocatedStock(false);//是否已分配库存
            order.setIsMakeInvoice(productMealOrder.getIssueInvoice());//是否需要开具发票
            order.setOffsetAmount(new BigDecimal(0));//调整金额 算法中做加法
            order.setOrderStatus(0);//订单状态 0未确认
            order.setPaymentMethodName("");//支付方式名称 暂无
            order.setPaymentStatus(0);//支付状态 0未支付
            order.setPhone(receiver.getPhone());//收件人电话
            order.setPoint(0l);//使用积分积分后改
            order.setPromotionDiscount(new BigDecimal(1));//促销折扣
            order.setShippingMethodName("");//配送方式名称
            order.setShippingStatus(0);//发货状态 0未发货
            String sn = "SP"+System.currentTimeMillis();

            order.setSn(sn);//订单编号 sp+毫秒数生成
            order.setTax(new BigDecimal(0));//税金 算法中做加法
            order.setArea(receiver.getArea());//tf_area表的主键
            order.setMember(member.getId());//下单人
            order.setEvaluate(0);//评价状态 0未评价
            order.setIsDeleted(false);//是否删除
            order.setHospital(receiver.getHospital());//详细医院
            order.setIsMemberDelete(false);//用户是否删除订单

            order.setIsMakeInvoice(false);//是否已开具发票，未开具
            orderDao.insertSelective(order);

            //2、插入订单项

                //从立即购买直接跳转到结算页面的提交订单
                List<OrderItem> listOrderItem = new ArrayList<>();
                OrderItem orderItem = new OrderItem();
                orderItem.setCreateDate(new Date());
                orderItem.setModifyDate(new Date());
                orderItem.setFullName(product.getFullName());
                orderItem.setIsGift(false);
                orderItem.setName(product.getName());
                orderItem.setPrice(productPurchase.getPrice());//单价
                orderItem.setQuantity(1);//数量
                orderItem.setReturnQuantity(0);
                orderItem.setShippedQuantity(0);
                orderItem.setSn(sn);
                orderItem.setThumbnail(product.getImage());
                orderItem.setWeight(0);
                orderItem.setOrders(order.getId());//订单id
                orderItem.setProduct(product.getId());
                orderItem.setIsDeleted(false);
                orderItem.setCompany(product.getCompanyId());
                orderItem.setCompanyReceiver(product.getCompanyId());
                orderItem.setProductPurchaseId(productPurchase.getId());
                Purchase purchase = purchaseDao.selectByPrimaryKey(Long.valueOf(productPurchase.getPurchaseId()));
                orderItem.setPurchaseName(purchase.getName());

                OrderItem orderItemProductPackage = new OrderItem();
                orderItemProductPackage.setCreateDate(new Date());
                orderItemProductPackage.setModifyDate(new Date());
                orderItemProductPackage.setFullName(productPackage.getFullName());
                orderItemProductPackage.setIsGift(false);
                orderItemProductPackage.setName(productPackage.getName());
                orderItemProductPackage.setPrice(productPackage.getPrice());//单价
                orderItemProductPackage.setQuantity(1);//数量
                orderItemProductPackage.setReturnQuantity(0);
                orderItemProductPackage.setShippedQuantity(0);
                orderItemProductPackage.setSn(sn);
                orderItemProductPackage.setThumbnail(productPackage.getImage());
                orderItemProductPackage.setWeight(0);
                orderItemProductPackage.setOrders(order.getId());//订单id
                orderItemProductPackage.setProduct(productPackage.getId());
                orderItemProductPackage.setIsDeleted(false);
                orderItemProductPackage.setCompany(productPackage.getCompanyId());
                orderItemProductPackage.setCompanyReceiver(productPackage.getCompanyId());
                orderItemProductPackage.setProductPurchaseId(null);
                orderItemProductPackage.setPurchaseName(productPackage.getName());

                listOrderItem.add(orderItemProductPackage);
                listOrderItem.add(orderItem);

                orderItemDao.insertList(listOrderItem);//插入订单项
            //order.setProductName(productName);
            orderDao.updateByPrimaryKeySelective(order);//修改order表，

            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setAmount(this.getTotalAmount(order.getId()));
            orderPayment.setOrderId(order.getId().toString());
            Date newDate = new Date();
            orderPayment.setCreateDate(newDate);
            String out_trade_no = (String.valueOf(newDate.getTime())+String.valueOf(newDate.getTime())+String.format("%06d%n", 1)).trim();
            orderPayment.setOutTradeNo(out_trade_no);
            orderPaymentMapper.insertSelective(orderPayment);
            return_map.put("orderPayment",orderPayment);

            OrderInvoice orderInvoice = new OrderInvoice();
            //发票信息表

                if (productMealOrder.getIssueInvoice()){//是否开具发票

                    orderInvoice.setIsDelete(false);
                    orderInvoice.setAmount(productMeal.getPrice());
                    orderInvoice.setCreateDate(new Date());
                    orderInvoice.setModifyDate(new Date());
                    orderInvoice.setIsMakeInvoice(1l);
                    orderInvoice.setType(Long.valueOf(productMealOrder.getOrderInvoiceType()));
                    orderInvoice.setTitle(productMealOrder.getOrderInvoicePtitle());
                    orderInvoice.setPcontent(productMealOrder.getOrderInvoicePcontent());
                    orderInvoice.setPmobile(productMealOrder.getOrderInvoiceZmobile());
                    orderInvoice.setPemail(productMealOrder.getOrderInvoicePemail());
                    orderInvoice.setCompanyName(productMealOrder.getOrderInvoiceZcompany());
                    orderInvoice.setTaxpayerNo(productMealOrder.getOrderInvoiceZidentityno());
                    orderInvoice.setRegisterAddress(productMealOrder.getOrderInvoiceZregAddress());
                    orderInvoice.setRegisterMobile(productMealOrder.getOrderInvoiceZmobile());
                    orderInvoice.setBank(productMealOrder.getOrderInvoiceZbank());
                    orderInvoice.setBankAccount(productMealOrder.getOrderInvoiceZaccount());
                    orderInvoice.setMemberName(productMealOrder.getOrderInvoiceZname());
                    orderInvoice.setAreaId(productMealOrder.getOrderInvoiceZareaId());
                    orderInvoice.setAddress(productMealOrder.getOrderInvoiceZaddress());
                    orderInvoice.setMember(member.getId().toString());
                    orderInvoice.setOrderId(order.getId().toString());
                    orderInvoiceDao.insertSelective(orderInvoice);
                }

            //发票信息表结束
            //3、插入订单日志
            OrderLog orderLog = new OrderLog();
            orderLog.setCreateDate(new Date());
            orderLog.setModifyDate(new Date());
            orderLog.setContent("创建订单");//操作内容
            orderLog.setOperator(member.getUsername());//前台用户名称(账号）
            orderLog.setType(1);//用户类型1前台用户
            orderLog.setOrders(order.getId());//订单表id
            orderLog.setIsDeleted(false);
            orderLog.setOperatorName(member.getName());//操作人姓名
            orderLogDao.insertSelective(orderLog);//插入订单日志
            return_map.put("orderInvoice",orderInvoice);

        }catch (Exception exception){
            System.out.println("手动事务回滚"+exception.getMessage());

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return return_map;
    }

    @Override
    @Transactional
    public String notifyUrlService(JSONObject jsonObject){
        OrderPayment orderPayment = (OrderPayment) jsonObject.get("orderPayment");
        BigDecimal amount = jsonObject.getBigDecimal("amount");
        Member member = memberService.selectByPrimaryKey(orderPayment.getMemberId());
        try{
            Boolean flag = false;
            Map query_map = new HashMap();
            query_map.put("orderIds",orderPayment.getOrderId().split(","));
            List<Map> orders = orderService.getOrders(query_map);
            Map<String,Object> map =SettingUtils.getReadXml(pathUrl) ;
            Object productPoint =  map.get("purchaseProductPoint");//读取配置文件中的支付赠送积分
            Integer point = 0;
            if(productPoint!=null&&!productPoint.equals("")){
                if(Integer.valueOf(productPoint.toString())>0){
                    if (amount.setScale( 0, BigDecimal.ROUND_DOWN ).intValue()>=Integer.valueOf(productPoint.toString())){
                        point = amount.setScale( 0, BigDecimal.ROUND_DOWN ).intValue() / Integer.valueOf(productPoint.toString());
                        flag = true;
                    }
                }
            }

            for (Map order_map : orders) {
                //1、改变订单表状态
                Order order = orderDao.selectByPrimaryKey(Long.valueOf(String.valueOf(order_map.get("id"))));

                order.setAmountPaid(this.getTotalAmount(order.getId()));//已付金额
                order.setPaymentMethodName(jsonObject.getString("paymentMethodName"));//支付方式名称
                order.setPaymentStatus(2);//支付状态2已支付
                order.setPoint(0l);//该订单赠送多少积分(如果是普通用户则赠送积分，企业用户则不赠送积分)
                order.setPaidDate(new Date());//付款日期
                order.setOrderStatus(1);//已确认状态
                orderService.updateOrder(order);//修改订单状态完成
                //2、订单日志表
                OrderLog orderLog = new OrderLog();
                orderLog.setCreateDate(new Date());
                orderLog.setModifyDate(new Date());
                orderLog.setContent("支付完成");
                orderLog.setOperator(member.getUsername());
                orderLog.setType(1);
                orderLog.setOrders(order.getId());
                orderLog.setIsDeleted(false);
                orderLog.setOperatorName(member.getName());
                orderLogService.insertOrderLog(orderLog);//订单日志表完成
                //3、改变商品表购买数量
                List<Map<String,Object>> listProductid = orderItemService.selectProductid(order.getId());
                for (int i = 0; i <listProductid.size(); i++) {
                    Map<String,Object> listProduct_map = listProductid.get(i);
                    Long productid = (Long) listProduct_map.get("product");//商品id
                    BigDecimal bigDecimal= (BigDecimal)listProduct_map.get("quantity");
                    Integer quantity=Integer.parseInt(bigDecimal.toString());
                    //int quantity = (int) map.get("quantity");//该商品id的数量
                    Product product = productService.selectProductById(productid);
                    product.setSales(product.getSales()+quantity);
                    productService.updateProduct(product);//修改商品表完成
                }
                //4、插入商品购买记录表

                //循环订单项开始

                List<OrderItem> listItem = orderItemService.selectByOrderid(order.getId());
                for (OrderItem orderItem : listItem) {
                    Product product = productService.selectProductById(orderItem.getProduct());
                    ProductSale productSale = new ProductSale();
                    if (orderItem.getProductPurchaseId()!=0 && orderItem.getProductPurchaseId()!=null){
                        ProductPurchase productPurchase = productPurchaseService.selectById(orderItem.getProductPurchaseId());
                        String purid = productPurchase.getPurchaseId();
                        Purchase purchase = purchaseService.selectByPrimaryKey(Long.valueOf(purid));
                        productSale.setPurchaseId(productPurchase.getId());
                        productSale.setPurchaseName(purchase.getName());
                    }
                    productSale.setProduct(orderItem.getProduct());
                    productSale.setCreateDate(new Date());
                    productSale.setOrderDate(orderItem.getCreateDate());
                    productSale.setMember(order.getMember());
                    productSale.setCount(orderItem.getQuantity());
                    productSale.setProductName(product.getName());
                    productSaleService.insertProductSale(productSale);//插入商品购买记录
                }

                if (member.getType()==1){//如果是普通用户则增加积分
                    if(flag){
                        //5.改变积分
                        Long point2 = member.getPoint()+point.longValue();
                        member.setPoint(point2);
                        memberService.updateMember(member);
                        //6.插入积分记录
                        MemberPointLog memberPointLog = new MemberPointLog();
                        memberPointLog.setCreateDate(new Date());
                        memberPointLog.setIsDeleted(false);
                        memberPointLog.setModifyDate(new Date());
                        memberPointLog.setType(0);//收入
                        memberPointLog.setMember(member.getId());
                        memberPointLog.setDebit(point.longValue());
                        memberPointLog.setMemo("支付订单"+order.getSn()+"成功后获取积分");
                        memberPointLog.setPoint(point2);
                        memberPointLog.setOrders(order.getId());
                        memberPointLogService.insertMemberPointLog(memberPointLog);//插入积分记录完成
                    }
                }
            }
            return "success";
        }catch (Exception exception){
            System.out.println("手动事务回滚"+exception.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "fail";
        }


    }

    @Override
    public Order selectById(Long id) {
        return orderDao.selectByPrimaryKey(id);
    }

    //计算支付订单价格的方法(参数是订单表tf_order的主键）
    @Override
    public BigDecimal getTotalAmount(Long id) {
        System.out.println("开始计算订单总价");
        BigDecimal price = new BigDecimal(0);
        Order order = orderDao.selectByPrimaryKey(id);
        List<OrderItem> listItem = orderItemDao.selectByOrderId(id);//订单项列表
        BigDecimal totalAmount = new BigDecimal(0);
        for(int i=0;i<listItem.size();i++){
            OrderItem orderItem = listItem.get(i);
            if (null==orderItem.getProductPurchaseId()||orderItem.getProductPurchaseId()==0){
                price = orderItem.getPrice();
            }else{
                long propurchaseId = orderItem.getProductPurchaseId();
                ProductPurchase productPurchase = productPurchaseDao.selectByPrimaryKey(propurchaseId);
                if(productPurchase!=null){
                    price = productPurchase.getPrice();//商品单价
                }
            }

            Integer quantity = orderItem.getQuantity();//购买的数量
            BigDecimal amount = price.multiply(new BigDecimal(quantity));//单价X数量=小计
            totalAmount=totalAmount.add(amount);
        }
        totalAmount=totalAmount.subtract(order.getCouponDiscount());//计算完在减去减免金额
        System.out.println("结束计算订单总价"+totalAmount);
        return totalAmount;
    }

    /**
     * 计算订单总价
     * @return
     */
//    @Override
//    public BigDecimal getCountAmount(Long id) {
//        BigDecimal price = new BigDecimal(0);
////        Order order = orderDao.selectByPrimaryKey(id);
//        List<OrderItem> listItem = orderItemDao.selectByOrderId(id);//订单项列表
//        BigDecimal totalAmount = new BigDecimal(0);
//        for(int i=0;i<listItem.size();i++){
//            OrderItem orderItem = listItem.get(i);
//            Integer quantity = orderItem.getQuantity();//购买的数量
//            BigDecimal amount = orderItem.getPrice().multiply(new BigDecimal(quantity));//单价X数量=小计
//            totalAmount=totalAmount.add(amount);
//        }
//        return totalAmount;
//    }


    @Override
    public List<Order> selectByMember(Long member) {
        return orderDao.selectByMember(member);
    }

    @Override
    public List<Map<String, Object>> selectOrderList(Map<String,Object> querymap) {
//        Map<String,Object> query_map = new HashMap<>();
//        query_map.put("member",member);
        return orderDao.selectOrderList(querymap);
    }

    @Override
    public List<Map<String, Object>> selectAddedOrderList(Map<String, Object> querymap) {
        return orderDao.selectAddedOrderList(querymap);
    }

    @Override
    public int selectCount(Map<String, Object> map) {
        return orderDao.selectCount(map);
    }

    @Override
    public Integer selectAddedCount(Map<String, Object> map) {
        return orderDao.selectAddedCount(map);
    }

    @Override
    public List<Map<String, Object>> selectOrderView(Map<String, Object> querymap) {
        return orderDao.selectOrderView(querymap);
    }

    @Override
    public int updateOrder(Order order) {
        return orderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order selectBySn(String sn) {
        return orderDao.selectBySn(sn);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderDao.deleteByPrimaryKey(id);
    }

    //购物车提交订单
    @Override
    @Transactional
    public Map saveOrder(Long oreceiverId, String myCartItemId,Integer buytype,Long isaddedvalue,Boolean isMakeInvoice,
                         Long type,String pcontent,String title,String taxpayerNo,String pmobile,String pemail,String companyName,String registerAddress,String registerMobile,String bank,
                         String bankAccount,String memberName,Long areaId,String address) {
        Map map = new HashMap();
        try{
            Member member = memberService.getCurrent();
            Receiver receiver = receiverDao.selectByPrimaryKey(oreceiverId);//收件人
            JSONArray jsonArray = JSON.parseArray(myCartItemId);
            StringBuffer orderIds = new StringBuffer();
            BigDecimal amount = new BigDecimal(0);
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
                //1、插入订单
                Order order=new Order();
                //增值服务订单
                order.setType(isaddedvalue);//  1.普通订单2.增值服务订单3.培训包4.3d打印
                order.setCreateDate(new Date());
                order.setModifyDate(new Date());
                order.setAddress(receiver.getAddress());
                order.setAmountPaid(new BigDecimal(0));//已付金额，付款之前是0
                order.setAreaName(receiver.getAreaName());//地区全称
                order.setConsignee(receiver.getConsignee());//收件人
                order.setReceiverId(oreceiverId);//收件人id
                order.setEmail(receiver.getEmail());//收件人邮箱
                order.setCouponDiscount(new BigDecimal(0));//减免金额 算法中做减法
                Date dt= new Date();
                long time= dt.getTime();
                //获取7天的毫秒数
                long sevenTime = 7*24*60*60*1000;
                //当前时间毫秒数+7天的毫秒数=7天之后那天的毫秒数
                long times = time + sevenTime;
                //将毫秒数转日期
                Date dat=new Date(times);
                order.setExpire(dat);//订单到期日期，下单后往后推7天
                order.setFee(new BigDecimal(0));//支付手续费 算法中做加法
                order.setFreight(new BigDecimal(0));//运费 算法中做加法
                order.setIsAllocatedStock(false);//是否已分配库存
                order.setOffsetAmount(new BigDecimal(0));//调整金额 算法中做加法
                order.setOrderStatus(0);//订单状态 0未确认
                order.setPaymentMethodName("");//支付方式名称 暂无
                order.setPaymentStatus(0);//支付状态 0未支付
                order.setPhone(receiver.getPhone());//收件人电话
                order.setPoint(0l);//积分？？？？？？？？？？？？？？？？？？？？后改
                order.setPromotionDiscount(new BigDecimal(1));//促销折扣
                order.setShippingMethodName("");//配送方式名称
                order.setShippingStatus(0);//发货状态 0未发货
                String sn = "SP"+System.currentTimeMillis();

                order.setSn(sn);//订单编号 sp+毫秒数生成
                order.setTax(new BigDecimal(0));//税金 算法中做加法
                order.setArea(receiver.getArea());//tf_area表的主键
                order.setMember(member.getId());//下单人
                order.setEvaluate(0);//评价状态 0未评价
                order.setIsDeleted(false);//是否删除
                order.setHospital(receiver.getHospital());//详细医院
                order.setIsMemberDelete(false);//用户是否删除订单
                order.setIsMakeInvoice(isMakeInvoice);//是否申请开票
                order.setCompanyId(jsonObject.getLong("company_id"));
                order.setCompanyName(jsonObject.getString("name"));
                orderDao.insertSelective(order);


                //2、插入订单项
                JSONArray listMy = jsonObject.getJSONArray("listMy");
                List<OrderItem> listOrderItem = new ArrayList<>();
                    for (Object objectOrderItem : listMy) {
                        JSONObject orderItemObj = (JSONObject) JSONObject.toJSON(objectOrderItem);
//                        Product product = productDao.selectByPrimaryKey(goproductid);
                        OrderItem orderItem = new OrderItem();
                        orderItem.setCreateDate(new Date());
                        orderItem.setModifyDate(new Date());
                        orderItem.setFullName(orderItemObj.getString("name"));
                        orderItem.setIsGift(false);
                        orderItem.setName(orderItemObj.getString("name"));
//                        ProductPurchase productPurchase = productPurchaseDao.selectByPrimaryKey(gopurchaseid);//商品和规格的关联表
//                        BigDecimal price = product.getIsAddService() == 3 ?product.getPrice():productPurchase.getPrice(); //如果是培训包 就获取该产品的实际价格，
                        orderItem.setPrice(orderItemObj.getBigDecimal("price"));//单价
                        orderItem.setQuantity(orderItemObj.getInteger("quantity"));//数量
                        orderItem.setReturnQuantity(0);
                        orderItem.setShippedQuantity(0);
                        orderItem.setSn(orderItemObj.getString("sn"));
                        orderItem.setThumbnail(orderItemObj.getString("image"));
                        orderItem.setWeight(0);
                        orderItem.setOrders(order.getId());//订单id
                        orderItem.setProduct(orderItemObj.getLong("productId"));
                        orderItem.setIsDeleted(false);
                        orderItem.setCompany(orderItemObj.getLong("companyId"));
                        orderItem.setCompanyReceiver(orderItemObj.getLong("companyId"));
                        orderItem.setProductPurchaseId(orderItemObj.getLong("purchaseId"));
                        orderItem.setPurchaseName(orderItemObj.getString("purchaseName"));
                        listOrderItem.add(orderItem);
                        cartItemDao.deleteByPrimaryKey(orderItemObj.getLong("itemid"));
                    }
                orderItemDao.insertList(listOrderItem);//插入订单项
                //3、插入订单日志
                OrderLog orderLog = new OrderLog();
                orderLog.setCreateDate(new Date());
                orderLog.setModifyDate(new Date());
                orderLog.setContent("创建订单");//操作内容
                orderLog.setOperator(member.getUsername());//前台用户名称(账号）
                orderLog.setType(1);//用户类型1前台用户
                orderLog.setOrders(order.getId());//订单表id
                orderLog.setIsDeleted(false);
                orderLog.setOperatorName(member.getName());//操作人姓名
                orderLogDao.insertSelective(orderLog);//插入订单日志
                orderIds.append(order.getId()+",");
                amount = amount.add(orderService.getTotalAmount(order.getId()));
            }


            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setAmount(amount);
            orderPayment.setOrderId(orderIds.toString().substring(0,orderIds.length()-1));
            Date newDate = new Date();
            orderPayment.setCreateDate(newDate);
            String out_trade_no = (String.valueOf(newDate.getTime())+String.valueOf(newDate.getTime())+String.format("%06d%n", 1)).trim();
            orderPayment.setOutTradeNo(out_trade_no);
            orderPayment.setMemberId(member.getId());
            orderPaymentMapper.insertSelective(orderPayment);
            map.put("orderPayment",orderPayment);
            OrderInvoice orderInvoice = new OrderInvoice();
            //是否申请开票
            if (isMakeInvoice){
                orderInvoice.setIsDelete(false);
                orderInvoice.setAmount(amount);
                orderInvoice.setCreateDate(new Date());
                orderInvoice.setModifyDate(new Date());
                orderInvoice.setIsMakeInvoice(1l);
                orderInvoice.setType(type);
                orderInvoice.setTitle(title);
                orderInvoice.setPcontent(pcontent);
                orderInvoice.setPmobile(pmobile);
                orderInvoice.setPemail(pemail);
                orderInvoice.setCompanyName(companyName);
                orderInvoice.setTaxpayerNo(taxpayerNo);
                orderInvoice.setRegisterAddress(registerAddress);
                orderInvoice.setRegisterMobile(registerMobile);
                orderInvoice.setBank(bank);
                orderInvoice.setMemberName(memberName);
                orderInvoice.setAreaId(areaId);
                orderInvoice.setAddress(address);
                orderInvoice.setMember(member.getId().toString());
                orderInvoice.setBankAccount(bankAccount);
                orderInvoice.setOrderId(orderIds.toString().substring(0,orderIds.length()-1));
                orderInvoiceDao.insertSelective(orderInvoice);
            }
            map.put("orderInvoice",orderInvoice);
        }catch (Exception exception){
            System.out.println("手动事务回滚"+exception.getMessage());

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return map;
    }

    @Override
    public Map getOrder(Map query_map) {
        return orderDao.getOrder(query_map);
    }

    @Override
    public List<Map> getOrderInvoice(Map querymap) {
        return orderDao.getOrderInvoice(querymap);
    }

    @Override
    public Map getOrderStatus(Map query_map) {
        return orderDao.getOrderStatus(query_map);
    }

    @Override
    public Integer isProductPurchase(Map query_map) {
        return orderDao.isProductPurchase(query_map);
    }

    @Override
    public Map getAddServiceOrderStatus(Map query_map) {
        return orderDao.getAddServiceOrderStatus(query_map);
    }

    @Override
    public List<Map> getOrders(Map query_map) {
        return orderDao.getOrders(query_map);
    }


}
