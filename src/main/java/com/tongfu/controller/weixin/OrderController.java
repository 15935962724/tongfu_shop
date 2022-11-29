package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.DateUtil;
import com.tongfu.Util.ResultUtil;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;


@Controller("weixinorder")
@RequestMapping("/weixin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;//订单

    @Autowired
    private OrderItemService orderItemService;//订单项

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private MemberService memberService;//用户

    @Autowired
    private OrderInvoiceService orderInvoiceService;//发票

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMealService productMealService;

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private InvoiceService invoiceService;

    @Value("${casesFile}")
    private String caseFile;

    @Value("${alipay-privatekey}")
    private String privatekey;

    @Value("${alipay-publickey}")
    private String publickey;

    @Value("${sign-type}")
    private String signtype;

    @Value("${alicharset}")
    private String alicharset;

    @Value("${path-url}")
    private String pathUrl;

    @Autowired
    private PlatformTransactionManager txManager;
    /**
     * 购物车列表页去支付
     * @param jsonObject
     * @return
     */
    //先没管的字段：lock_expire，memo，promotion，operator，payment_method，shipping_method，paid_date，refunded_date，delivery_corp，delivery_sn
    //先没管的字段：锁定到期时间，备注，促销码，操作员，支付方式，配送方式，付款日期，退款日期，物流公司，物流单号
    //订单结算页点击提交订单按钮 tf_receiver的id，购物车项id string类型用逗号隔开了，invoiceType 0普 1专，userid tf_member的主键,orderType 订单类型0普通 1增值，isInvoice 是否需要发票0不需要 1需要，buytype表示是从购物车过来的订单还是从立即购买过来的订单（null为购物车，1为立即购买）
    @RequestMapping("/addOrder")
    @ResponseBody
    public String addOrder(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Long receiverId = jsonObject.getLong("receiverId");
        String myCartItemId = jsonObject.getString("myCartItemId");
        Integer buytype = jsonObject.getInteger("buytype");
        Long goproductid = jsonObject.getLong("goproductid");
        Long gopurchaseid = jsonObject.getLong("gopurchaseid");
        Integer gonum = jsonObject.getInteger("gonum");
        Long isaddedvalue = jsonObject.getLong("isaddedvalue");
        Boolean isMakeInvoice = jsonObject.getBoolean("isMakeInvoice");
        Long type = jsonObject.getLong("type");
        String pcontent = jsonObject.getString("pcontent");
        String title = jsonObject.getString("title");
        String taxpayerNo = jsonObject.getString("taxpayerNo");
        String pmobile = jsonObject.getString("pmobile");
        String pemail = jsonObject.getString("pemail");
        String companyName = jsonObject.getString("companyName");
        String registerAddress = jsonObject.getString("registerAddress");
        String registerMobile = jsonObject.getString("registerMobile");
        String bank = jsonObject.getString("bank");
        String bankAccount = jsonObject.getString("bankAccount");
        String memberName = jsonObject.getString("memberName");
        Long areaId = jsonObject.getLong("areaId");
        String address = jsonObject.getString("address");
        Map map = orderService.saveOrder(
                receiverId,myCartItemId,buytype,isaddedvalue,isMakeInvoice,type,pcontent,title,taxpayerNo,pmobile,pemail,companyName,registerAddress,registerMobile,bank,bankAccount,memberName,areaId,address
        );
        Map query_map = new HashMap();
        OrderPayment orderPayment = (OrderPayment) map.get("orderPayment");
        query_map.put("orderIds",orderPayment.getOrderId().split(","));
        List<Map> orders = orderService.getOrders(query_map);
        for (Map order : orders) {
            List<OrderItem> orderItems = orderItemService.selectByOrderid(Long.valueOf(String.valueOf(order.get("id"))));
            order.put("orderItems",orderItems);
        }
        returnJson.put("orders",orders);
        returnJson.put("orderPayment",map.get("orderPayment"));
        //再跳转到支付页
        return ResultUtil.success(returnJson);
    }


    /**
     * 套餐购买
     * @param jsonObject
     * @return
     */
    @RequestMapping("/addProductMealOrder")//从商品详情页跳转到结算页
    @ResponseBody
    public String productMealOrder(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Member member = memberService.getCurrent();
        ProductMeal productMeal = productMealService.selectByPrimaryKey(jsonObject.getLong("productMealId"));
        Product product = productService.selectProductById(productMeal.getProductId());
        Product productPackage = productService.selectProductById(productMeal.getProductPackageId());
        ProductPurchase productPurchase = productPurchaseService.selectById(productMeal.getProductPurchaseId());

        MyCart myCart = new MyCart();//主产品
        myCart.setName(product.getName());
        myCart.setImage(product.getImage());
        myCart.setPrice(productPurchase.getPrice());
        myCart.setQuantity(1);
        myCart.setPurchaseId(productPurchase.getId());

        MyCart myCart1 = new MyCart();//培训包
        myCart1.setName(productPackage.getName());
        myCart1.setImage(productPackage.getImage());
        myCart1.setPrice(productPackage.getPrice());
        myCart1.setQuantity(1);
        myCart1.setPurchaseId(0l);

        //根据用户id查询我的购物车
        List<MyCart> list = new ArrayList<>();
        list.add(myCart);
        list.add(myCart1);
        returnJson.put("MyCart",list);
        returnJson.put("isaddedvalue",product.getIsAddService());
        returnJson.put("totalNum",list.size());
        returnJson.put("totalAmount",productMeal.getPrice());//计算金额总计
        //收货人信息
        List<Receiver> listReceiver = receiverService.selectByMember(member.getId());
        returnJson.put("listReceiver",listReceiver);
        //发票信息
        //查询当前用户的个人(普通和增值税专用)发票 type=0
        Map<String,Object> map=new HashMap<>();
        map.put("member",member.getId());
        map.put("type",0);
        Invoice invoiceP = invoiceService.selectByMember(map);
        if(invoiceP==null){
            invoiceP=new Invoice();
        }
        //查询当前用户的增值税专用发票 type=1
        map.put("type",1);
        Invoice invoiceZ = invoiceService.selectByMember(map);
        if(invoiceZ==null){
            invoiceZ=new Invoice();
        }
        returnJson.put("invoiceP",invoiceP);//普通发票
        returnJson.put("invoiceZ",invoiceZ);//增值税专用发票
        return ResultUtil.success(returnJson);
    }




    //套餐购买提交订单
    @PostMapping("/saveProducMealOrder")
    @ResponseBody
    public String saveProducMealOrder(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        ProductMealOrder productMealOrder = JSON.parseObject(jsonObject.toString(), ProductMealOrder.class);
        Map<String,Object> map = orderService.saveProductMealOrder(productMealOrder);

        OrderPayment orderPayment = (OrderPayment) map.get("orderPayment");
        returnJson.put("orderPayment",map.get("orderPayment"));
        Map query_map = new HashMap();
        query_map.put("orderIds",orderPayment.getOrderId().split(","));
        List<Map> orders = orderService.getOrders(query_map);
        for (Map order : orders) {
            List<OrderItem> orderItems = orderItemService.selectByOrderid(Long.valueOf(String.valueOf(order.get("id"))));
            order.put("orderItems",orderItems);
        }
        returnJson.put("orders",orders);
        //再跳转到支付页
        return ResultUtil.success(returnJson);
    }


    /**
     * 订单详情页去支付
     * @param jsonObject
     * @return
     */
    @RequestMapping("/goPayment")
    @ResponseBody
    public String goPayment(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Long orderId = jsonObject.getLong("orderId");
        Order order = orderService.selectById(orderId);//查询order
        List<OrderItem> listItem = orderItemService.selectByOrderid(orderId);//查询orderitem
        //计算订单金额并显示在页面上
        BigDecimal totalAmount = orderService.getTotalAmount(order.getId());
        //发票
        OrderInvoice orderInvoice = orderInvoiceService.selectByOrderId(order.getId());
        returnJson.put("order",order);
        returnJson.put("listItem",listItem);
        returnJson.put("totalAmount",totalAmount);
        returnJson.put("productName",listItem.size()>0?listItem.get(0).getName():"");
        returnJson.put("orderInvoice",orderInvoice==null?new OrderInvoice():orderInvoice);
        //再跳转到支付页
        return ResultUtil.success(returnJson);
    }


        /**
         * 代付款订单
         * @param jsonObject
         * @return
         */
        @ResponseBody
        @RequestMapping("/paymentList")
        public String paymentList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        //分页开始
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        Member member = memberService.getCurrent();
        Map querymap = (Map) JSON.parse(jsonObject.toJSONString());
        querymap.put("member",member.getId());
        //待付款
        querymap.put("paymentStatus",0);
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> listOrder = orderService.selectOrderList(querymap);
        pageInfo.setList(listOrder);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        returnJson.put("page",pageInfo);
        return ResultUtil.success(returnJson);
    }

    /**
     * 待收货
     * @param jsonObject
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderStatusList")
    public String orderStatusList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        //分页开始
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        Member member = memberService.getCurrent();
        Map querymap = (Map) JSON.parse(jsonObject.toJSONString());
        querymap.put("member",member.getId());
        //待付款
        querymap.put("shippingStatus",2);
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> listOrder = orderService.selectOrderList(querymap);
        pageInfo.setList(listOrder);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        returnJson.put("page",pageInfo);
        return ResultUtil.success(returnJson);
    }

    /**
     * 订单详情
     * @param jsonObject
     * @return
     */
    @ResponseBody
    @RequestMapping("/view")
    public String view(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Order order = orderService.selectById(jsonObject.getLong("id"));
        List<OrderItem> orderItems = orderItemService.selectByOrderid(jsonObject.getLong("id"));
        returnJson.put("order",order);
        returnJson.put("orderItems",orderItems);
        return ResultUtil.success(returnJson);
    }


    /**
     * 订单列表
     * @param jsonObject
     * @return
     */
    @RequestMapping("/orderList")
    @ResponseBody
    public String orderList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        //分页开始
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        Member member = memberService.getCurrent();
        Map<String,Object> querymap = new HashMap<>();
        querymap.put("member",member.getId());
        String orderstatus = jsonObject.getString("orderstatus");
        if("payment_status".equals(orderstatus)){
            //待付款
            querymap.put("paymentStatus",0);
            querymap.put("orderStatus",0);//订单状态
        }
        if("shipping_status".equals(orderstatus)){
            //待收货
            querymap.put("paymentStatus",2);//支付状态
            Integer[] shippingarr= {2};
            querymap.put("shippingStatus",shippingarr);//收货状态
        }
        if("order_status2".equals(orderstatus)){
            //已完成
            querymap.put("orderStatus",2);//订单状态
        }
        if("order_status3".equals(orderstatus)){
            //已取消
            querymap.put("orderStatus",3);//订单状态
        }
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        querymap.put("isMemberDelete",false);//用户未删除的订单
        querymap.put("type",1);
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> listOrder = orderService.selectOrderList(querymap);
        for (Map<String, Object> stringObjectMap : listOrder) {
            List<Map> orderProducts = productService.getOrderProducts(stringObjectMap);
            stringObjectMap.put("orderProducts",orderProducts);
            stringObjectMap.put("orderSumPrice",orderService.getTotalAmount((Long) stringObjectMap.get("orderId")));

        }
        pageInfo.setList(listOrder);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        returnJson.put("page",pageInfo);
        return ResultUtil.success(returnJson);
    }



    /**
     * 增值服务订单
     * @param jsonObject
     * @return
     */
    @RequestMapping("/addedOrderList")
    @ResponseBody
    public String addedOrderList(@RequestBody JSONObject jsonObject){
        JSONObject returnJson = new JSONObject();
        Member member = memberService.getCurrent();
        Map map = new HashMap<>();
        map.put("member",member.getId());//用户id
        map.put("isMemberDelete",false);//用户未删除订单
        map.put("type",2);
        String orderstatus = jsonObject.getString("orderstatus");
        if("payment_status".equals(orderstatus)){
            //待付款
            map.put("paymentStatus",0);
        }
        if("case_status".equals(orderstatus)){
            //待上传病例
            map.put("paymentStatus",2);//支付状态
            map.put("caseStatus",0);//上传病例状态 0：未上传 1：已上传
        }
        if("order_status3".equals(orderstatus)){
            //已取消
            map.put("orderStatus",3);//订单状态
        }
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = orderService.selectAddedOrderList(map);
        for (Map<String, Object> stringObjectMap : list) {
            List<Map> orderProducts = productService.getOrderProducts(stringObjectMap);
            stringObjectMap.put("orderProducts",orderProducts);
        }
        pageInfo.setList(list);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        returnJson.put("page",pageInfo);//增值服务订单
        return ResultUtil.success(returnJson);
    }


    /**
     * 取消订单
     * @param
     * @return
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public String updateOrder(@RequestBody JSONObject jsonObject ){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            Member member = memberService.getCurrent();
            Order order = orderService.selectById(jsonObject.getLong("id"));
            order.setOrderStatus(3);
            orderService.updateOrder(order);
            OrderLog orderLog = new OrderLog();
            orderLog.setCreateDate(new Date());
            orderLog.setModifyDate(new Date());
            orderLog.setContent("取消订单"+jsonObject.getString("message"));//操作内容
            orderLog.setOperator(member.getUsername());//前台用户名称(账号）
            orderLog.setType(1);//用户类型1前台用户
            orderLog.setOrders(order.getId());//订单表id
            orderLog.setIsDeleted(false);
            orderLog.setOperatorName(member.getName());//操作人姓名
            orderLogService.insertOrderLog(orderLog);//插入订单日志
            txManager.commit(status);
            return ResultUtil.success();
        }catch (Exception e){
            System.out.println("事务回滚了");
            //强制手动事务回滚
            txManager.rollback(status);
            return ResultUtil.error("取消失败");
        }
    }


    /**
     * 确认收货
     * @param jsonObject
     * @return
     */
    @RequestMapping("/updateOrderReceiving")
    @ResponseBody
    public String updateOrderReceiving(@RequestBody JSONObject jsonObject ){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            Member member = memberService.getCurrent();
            Order order = orderService.selectById(jsonObject.getLong("id"));
            order.setShippingStatus(5);
            order.setOrderStatus(2);
            orderService.updateOrder(order);
            OrderLog orderLog = new OrderLog();
            orderLog.setCreateDate(new Date());
            orderLog.setModifyDate(new Date());
            orderLog.setContent("确认收货");//操作内容
            orderLog.setOperator(member.getUsername());//前台用户名称(账号）
            orderLog.setType(1);//用户类型1前台用户
            orderLog.setOrders(order.getId());//订单表id
            orderLog.setIsDeleted(false);
            orderLog.setOperatorName(member.getName());//操作人姓名
            orderLogService.insertOrderLog(orderLog);//插入订单日志
            txManager.commit(status);
            return ResultUtil.success();
        }catch (Exception e){
            System.out.println("事务回滚了");
            //强制手动事务回滚
            txManager.rollback(status);
            return ResultUtil.error("取消失败");
        }
    }




}
