package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongfu.Util.*;
import com.tongfu.dao.OrderPaymentMapper;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.EmailSend;
import com.tongfu.email.aliyun.MailInfo;
import com.tongfu.email.aliyun.MailSendUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;





@Controller
@RequestMapping("/order")
public class OrderController{

    @Autowired
    private OrderService orderService;//订单

    @Autowired
    private OrderItemService orderItemService;//订单项

    @Autowired
    private ReceiverService receiverService;//收件人

    @Autowired
    private InvoiceService invoiceService;//发票

    @Autowired
    private MemberService memberService;//用户

    @Autowired
    private OrderLogService orderLogService;//订单日志

    @Autowired
    private ProductService productService;//商品

    @Autowired
    private ProductPurchaseService productPurchaseService;//商品规格

    @Autowired
    private CasesService casesService;//病例

    @Autowired
    private ProductMealService productMealService; //套餐

    @Autowired
    private OrderPaymentService orderPaymentService;//付款单

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AdminService adminService;

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

    //	微信appid
    @Value("${wxAppid}")
    private String appid;

    //	微信商户号
    @Value("${mch_id}")
    private String mch_id;

    //	维信商户key
    @Value("${key}")
    private String key;


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


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private PlatformTransactionManager txManager;

    @RequestMapping("/addOrder")
    //先没管的字段：lock_expire，memo，promotion，operator，payment_method，shipping_method，paid_date，refunded_date，delivery_corp，delivery_sn
    //先没管的字段：锁定到期时间，备注，促销码，操作员，支付方式，配送方式，付款日期，退款日期，物流公司，物流单号
    //订单结算页点击提交订单按钮 tf_receiver的id，购物车项id string类型用逗号隔开了，invoiceType 0普 1专，userid tf_member的主键,orderType 订单类型0普通 1增值，isInvoice 是否需要发票0不需要 1需要，buytype表示是从购物车过来的订单还是从立即购买过来的订单（null为购物车，1为立即购买）
    public String addOrder(Model model,
                           Long oreceiverId, String myCartItemId,Integer buytype,Long goproductid,Long gopurchaseid,Integer gonum,Long isaddedvalue,Boolean isMakeInvoice,
                           Long type,String pcontent,String title,String taxpayerNo,String pmobile,String pemail,String companyName,String registerAddress,String registerMobile,String bank,
                           String bankAccount,String memberName,Long areaId,String address
                           ){
        Map map = orderService.saveOrder(
                oreceiverId,myCartItemId,buytype,isaddedvalue,isMakeInvoice,type,pcontent,title,taxpayerNo,pmobile,pemail,companyName,registerAddress,registerMobile,bank,bankAccount,memberName,areaId,address
        );
//        Map map = orderService.insertOrder(oreceiverId,myCartItemId,invoiceType,orderType,isInvoice,buytype,goproductid,gopurchaseid,gonum,isaddedvalue);

//        Order order = orderService.selectById(orderId);//查询order
//
//        List<OrderItem> listItem = orderItemService.selectByOrderid(orderId);//查询orderitem
//
//        //计算订单金额并显示在页面上
//        BigDecimal totalAmount = orderService.getTotalAmount(order.getId());
//
        OrderPayment orderPayment = (OrderPayment) map.get("orderPayment");
        model.addAttribute("orderPayment",map.get("orderPayment"));
        Map query_map = new HashMap();
        query_map.put("orderIds",orderPayment.getOrderId().split(","));
        List<Map> orders = orderService.getOrders(query_map);
        for (Map order : orders) {
            List<OrderItem> orderItems = orderItemService.selectByOrderid(Long.valueOf(String.valueOf(order.get("id"))));
            order.put("orderItems",orderItems);
        }
//        model.addAttribute("listItem",map.get("listItem"));
        model.addAttribute("orders",orders);

//        model.addAttribute("totalAmount",map.get("totalAmount"));
//        model.addAttribute("productName",map.get("productName"));
//        model.addAttribute("orderInvoice",map.get("orderInvoice"));
        model.addAttribute("pathUrl",pathUrl);

        //再跳转到支付页
        return "/order/orderToPay";
    }


    /**
     * 订单列表页去付款
     * @param model
     * @param orderId
     * @return
     */
    @RequestMapping("/goPayment")
     public String goPayment(Model model,Long orderId){
        Member member = memberService.getCurrent();
        Map query_map = new HashMap();
        query_map.put("orderIds",orderId.toString());
        OrderPayment orderPayment = orderPaymentService.getByOrderId(query_map);
        if (orderPayment==null){
            orderPayment = new OrderPayment();
            orderPayment.setOrderId(orderId.toString());
            Date newDate = new Date();
            String out_trade_no = (String.valueOf(newDate.getTime())+String.valueOf(newDate.getTime())+String.format("%06d%n", 1)).trim();
            orderPayment.setOutTradeNo(out_trade_no);
            BigDecimal totalAmount = orderService.getTotalAmount(orderId);
            orderPayment.setCreateDate(newDate);
            orderPayment.setAmount(totalAmount);
            orderPayment.setMemberId(member.getId());
            orderPaymentService.insertSelective(orderPayment);

        }
        model.addAttribute("orderPayment",orderPayment);
        query_map.put("orderIds",orderId.toString().split(","));
        List<Map> orders = orderService.getOrders(query_map);
        for (Map order : orders) {
            List<OrderItem> orderItems = orderItemService.selectByOrderid(Long.valueOf(String.valueOf(order.get("id"))));
            order.put("orderItems",orderItems);
        }
//        model.addAttribute("listItem",map.get("listItem"));
        model.addAttribute("orders",orders);
        model.addAttribute("pathUrl",pathUrl);
//        model.addAttribute("map",map);
        //再跳转到支付页
        return "/order/orderToPay";
    }



    /**
     * 套餐购买
     * @param model
     * @param productMealId
     * @return
     */
    @RequestMapping("/addProductMealOrder")//从商品详情页跳转到结算页
    public String productMealOrder(Model model,Long productMealId){
        Member member = memberService.getCurrent();
        ProductMeal productMeal = productMealService.selectByPrimaryKey(productMealId);
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

        model.addAttribute("MyCart",list);//购物车信息
        model.addAttribute("totalNum",list.size());
        model.addAttribute("isaddedvalue",product.getIsAddService());



        //收货人信息
        List<Receiver> listReceiver = receiverService.selectByMember(member.getId());
        model.addAttribute("listReceiver",listReceiver);
        //默认的收货地址
        Receiver receiver = receiverService.selectIsDefault(member.getId());
        model.addAttribute("receiverId",receiver!=null?receiver.getId():"");
        model.addAttribute("totalAmount",productMeal.getPrice());//计算金额总计
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
        model.addAttribute("invoiceP",invoiceP);//普通发票
        model.addAttribute("invoiceZ",invoiceZ);//增值税专用发票
        model.addAttribute("buytype",1);//从立即购买直接跳到结算页面
        model.addAttribute("productMealId",productMealId);//购物车信息
        model.addAttribute("pathUrl",pathUrl);
        return "/order/addProductMealOrder";
    }


    /**
     * 培训包立即购买
     * @param model
     * @return
     */
    @RequestMapping("/addProductPackageOrder")//从商品详情页跳转到结算页
    public String addProductPackageOrder(Model model,Long productId){
        Member member = memberService.getCurrent();
        Product product = productService.selectProductById(productId);


        MyCart myCart = new MyCart();//主产品
        myCart.setName(product.getName());
        myCart.setImage(product.getImage());
        myCart.setPrice(product.getPrice());
        myCart.setQuantity(1);
        myCart.setPurchaseId(0l);

        //根据用户id查询我的购物车
        List<MyCart> list = new ArrayList<>();
        list.add(myCart);
        model.addAttribute("MyCart",list);//购物车信息
        model.addAttribute("totalNum",list.size());
        model.addAttribute("isaddedvalue",product.getIsAddService());

        //收货人信息
        List<Receiver> listReceiver = receiverService.selectByMember(member.getId());
        model.addAttribute("listReceiver",listReceiver);
        //默认的收货地址
        Receiver receiver = receiverService.selectIsDefault(member.getId());
        model.addAttribute("receiverId",receiver!=null?receiver.getId():"");
        model.addAttribute("totalAmount",product.getPrice());//计算金额总计
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
        model.addAttribute("invoiceP",invoiceP);//普通发票
        model.addAttribute("invoiceZ",invoiceZ);//增值税专用发票
        model.addAttribute("buytype",1);//从立即购买直接跳到结算页面
        model.addAttribute("pathUrl",pathUrl);
        return "/order/addProductMealOrder";
    }





    //套餐购买提交订单
    @PostMapping("/saveProducMealOrder")
    public String saveProducMealOrder(Model model,ProductMealOrder productMealOrder){

        Object obj = JSONArray.toJSON(productMealOrder);
        String json = obj.toString();
        System.out.println("将Person对象转成json:" + json);
        Map<String,Object> map = orderService.saveProductMealOrder(productMealOrder);

        OrderPayment orderPayment = (OrderPayment) map.get("orderPayment");
        model.addAttribute("orderPayment",map.get("orderPayment"));
        Map query_map = new HashMap();
        query_map.put("orderIds",orderPayment.getOrderId().split(","));
        List<Map> orders = orderService.getOrders(query_map);
        for (Map order : orders) {
            List<OrderItem> orderItems = orderItemService.selectByOrderid(Long.valueOf(String.valueOf(order.get("id"))));
            order.put("orderItems",orderItems);
        }
        model.addAttribute("orders",orders);
        model.addAttribute("pathUrl",pathUrl);

        //再跳转到支付页
        return "/order/orderToPay";
    }


    /**
     * 我的订单
     * @param model
     * @param pageNum
     * @param pageSize
     * @param pageNames
     * @param orderstatus
     * @param ordersn
     * @param begindate
     * @param enddate
     * @return
     */
    @RequestMapping("/orderList")
    public String orderList(Model model,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize,Integer pageNames,
                            @RequestParam(defaultValue = "") String orderstatus,String ordersn,String begindate,String enddate){
        //分页开始
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        Member member = memberService.getCurrent();
        Map<String,Object> querymap = new HashMap<>();
        querymap.put("member",member.getId());
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
        //订单号

        querymap.put("ordersn",ordersn);

        //开始时间
        if (begindate!=null&&!begindate.equals("")){
            Date start_Date = DateUtil.getStringtoDate( begindate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
            querymap.put("begindate",start_Date);
            model.addAttribute("begindate",begindate);
        }
        //结束时间
        if (enddate!=null&&!enddate.equals("")){
            Date end_Date = DateUtil.getStringtoDate( enddate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
            querymap.put("enddate",end_Date);
            model.addAttribute("enddate",enddate);
        }

        //querymap.put("orderstatus",orderstatus);//订单状态
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

        //分页结束
        model.addAttribute("page",pageInfo);
        model.addAttribute("pageNames",pageNames);//go去第几页输入框的值
        model.addAttribute("pageSize",pageSize);//每页显示多少条
        model.addAttribute("orderstatus",orderstatus);//用于订单状态的class选中
        model.addAttribute("ordersn",ordersn);
        //查询各个订单状态的数量
        Map orderStatus = orderService.getOrderStatus(querymap);
        model.addAttribute("orderStatus",orderStatus);//已取消个数

        model.addAttribute("activeNone","order");//打开左侧导航
        model.addAttribute("active",4);//选中字菜单
        model.addAttribute("pathUrl",pathUrl);

        return "/order/list";
    }




    /**
     * 增值服务订单
     * @param model
     * @param orderstatus
     * @param pageNum
     * @param pageSize
     * @param ordersn
     * @param begindate
     * @param enddate
     * @return
     */
     @RequestMapping("/addedOrderList")
     public String addedOrderList(Model model,String orderstatus,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize,String ordersn,String begindate,String enddate){
        Member member = memberService.getCurrent();
        Map map = new HashMap<>();
         map.put("member",member.getId());//用户id
         map.put("isMemberDelete",false);//用户未删除订单
         map.put("type",2);
         map.put("ordersn",ordersn);
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
         model.addAttribute("orderstatus",orderstatus);//用于前台展示订单状态

         model.addAttribute("ordersn",ordersn);
         //开始时间
         if(begindate!=null&&!begindate.equals("")){
             Date start_Date = DateUtil.getStringtoDate( begindate+" 00:00:00","yyyy-MM-dd HH:mm:ss");
             map.put("begindate",start_Date);
         }
         model.addAttribute("begindate",begindate);
         //结束时间
         if(enddate!=null&&begindate.length()>0){
             Date end_Date = DateUtil.getStringtoDate( enddate+" 23:59:59","yyyy-MM-dd HH:mm:ss");
             map.put("enddate",end_Date);
         }
         model.addAttribute("enddate",enddate);
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
         model.addAttribute("page",pageInfo);//增值服务订单
         Map addServiceOrderStatus = orderService.getAddServiceOrderStatus(map);
         model.addAttribute("allcount",addServiceOrderStatus.get("count_order"));//全部订单
         model.addAttribute("paidcount",addServiceOrderStatus.get("stay_payment"));//待付款订单数
         model.addAttribute("casedcount",addServiceOrderStatus.get("stay_upload"));//待上传订单数
         model.addAttribute("cancelcount",addServiceOrderStatus.get("cancel_order"));//已取消订单数量
         model.addAttribute("activeNone","order");//打开左侧导航
         model.addAttribute("active",5);//选中字菜单
         model.addAttribute("pathUrl",pathUrl);
        return "/order/addedOrderList";
     }


    //申请售后
    @RequestMapping("/orderAftersales")
    public String orderAftersales(Model model,Long orderId){

        Map query_map = new HashMap();
        query_map.put("orderId",orderId);
        Map orderMap = orderService.getOrder(query_map);
        model.addAttribute("order",orderMap);
        Map<String,Object> map = new HashMap<>();
        map.put("orderid",orderId);
        List<Map<String,Object>> listview = orderService.selectOrderView(map);
        model.addAttribute("listview",listview);
        model.addAttribute("pathUrl",pathUrl);
        BigDecimal totalamount = orderService.getTotalAmount(orderId);
        model.addAttribute("totalamount",totalamount);

        return "/order/orderAftersales";
    }


    //获取激活码页面
    @RequestMapping("/addApplicationCode")
    public String addApplicationCode(Model model,Long orderItemId){
        OrderItem orderItem = orderItemService.selectByPrimaryKey(orderItemId);
         Order order = orderService.selectById(orderItem.getOrders());
        model.addAttribute("order",order);
        model.addAttribute("orderItem",orderItem);
        return "/order/addApplicationCode";
    }




    /**
     * knowHow卡订单
     * @param model
     * @param orderstatus
     * @param pageNum
     * @param pageSize
     * @param ordersn
     * @param begindate
     * @param enddate
     * @return
     */
    @RequestMapping("/knowHowList")
    public String knowHowList(Model model,String orderstatus,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize,String ordersn,String begindate,String enddate){
        Member member = memberService.getCurrent();
        Long mid = member.getId();
        Map<String,Object> map = new HashMap<>();
        map.put("member",mid);//用户id
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
        model.addAttribute("orderstatus",orderstatus==null?"":orderstatus);//用于前台展示订单状态
        //订单号
        if(ordersn!=null&&ordersn.length()>0){
            map.put("ordersn",ordersn);
            model.addAttribute("ordersn",ordersn);
        }
        //开始时间
        if(begindate!=null&&begindate.length()>0){
            map.put("begindate",begindate);
            model.addAttribute("begindate",begindate);
        }

        //结束时间
        if(enddate!=null&&begindate.length()>0){
            map.put("enddate",enddate);
            model.addAttribute("enddate",enddate);
        }
        //查询各个订单状态的数量
        //全部订单
        Map<String,Object> allmap=new HashMap<>();
        allmap.put("member",member.getId());
        Integer allcount = orderService.selectAddedCount(allmap);
        //待付款
        Map<String,Object> paidmap=new HashMap<>();
        paidmap.put("member",member.getId());
        paidmap.put("paymentStatus",0);
        Integer paidcount = orderService.selectAddedCount(paidmap);
        //待上传病例
        Map<String,Object> casedmap = new HashMap<>();
        casedmap.put("member",member.getId());
        casedmap.put("paymentStatus",2);
        casedmap.put("caseStatus",0);
        Integer casedcount = orderService.selectAddedCount(casedmap);
        //已取消
        Map<String,Object> canmap = new HashMap<>();
        canmap.put("member",member.getId());
        canmap.put("orderStatus",3);
        Integer cancelcount = orderService.selectAddedCount(canmap);
        model.addAttribute("allcount",allcount==null?0:allcount);//全部订单
        model.addAttribute("paidcount",paidcount==null?0:paidcount);//待付款订单数
        model.addAttribute("casedcount",casedcount==null?0:casedcount);//待上传订单数
        model.addAttribute("cancelcount",cancelcount==null?0:cancelcount);//已取消订单数量
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        Page<Map<String,Object>> page  = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = orderService.selectAddedOrderList(map);
        pageInfo.setList(list);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(page.getPages());
        model.addAttribute("page",pageInfo);//增值服务订单
        model.addAttribute("activeNone","order");//打开左侧导航
        model.addAttribute("active",6);//选中字菜单
        return "/order/knowHowList";
    }


    /**
     * 添加报告
     * @param model
     * @param orderid
     * @return
     */
     @RequestMapping("/addcase")
     public String addcase(Model model,Long orderid){
        model.addAttribute("orderId",orderid);
        return "/order/addcase";
     }

    /**
     * 我的sp远程报告
     * @param model
     * @return
     */
    @RequestMapping("/remoteReport")
    public String remoteReport(Model model){

        model.addAttribute("activeNone","presentation");//打开左侧导航
        return "/order/remoteReport";
    }




    @RequestMapping(value = "/addcases",method = RequestMethod.POST)
    @ResponseBody
    public Integer addcases(Model model, String patientid, String name, Long sheng, Long shi, String hospital, String department, String doctor,
                           String deliveryDate, String discussDate, String diagnosis, String demand, MultipartFile datafiles,Long orderId){
        CasesWithBLOBs cases = new CasesWithBLOBs();
        cases.setPatientid(patientid);//病人id
        cases.setName(name);
        if(shi!=null){
            cases.setArea(shi);
        }else if(sheng!=null){
            cases.setArea(sheng);//地区
        }
        cases.setHospital(hospital);//来源医院
        cases.setDepartment(department);//科室
        cases.setDoctor(doctor);//医生姓名
        try{
            Date delivery = sdf.parse(deliveryDate);
            cases.setDeliveryDate(delivery);//交付时间
            Date discuss = sdf.parse(discussDate);
            cases.setDiscussDate(discuss);//术前讨论时间

        }catch (Exception exp){

        }
        cases.setDiagnosis(diagnosis);//初步诊断
        cases.setDemand(demand);//具体要求
        String cases_file= FileUpload.upload(datafiles,caseFile);//DICOM数据和诊断报告
        cases.setDatafiles(cases_file);
        cases.setOrderid(orderId);//订单id
        Member member = memberService.getCurrent();
        cases.setMember(member.getId());//用户id

        cases.setCreatedate(new Date());
        cases.setModifyDate(new Date());
        int result = casesService.insertSelective(cases);

        return result;
    }


    /**
     * 支付宝回调页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/paySuccess")
    public String gotoPaySuccess(HttpServletRequest request,Model model) {

        Map<String,String> params = new HashMap<>();
        //获取支付宝POST过来反馈信息
        Map requestParams = request.getParameterMap();
        //keyset()是获取所有的key值，iterator()是迭代遍历
                 for(Iterator iter = requestParams.keySet().iterator();iter.hasNext();){
                         String name = (String)iter.next();
                         //这里把key放到数组里面
                         String[] values = (String[]) requestParams.get(name);
                         String valueStr = "";
                         //这个for循环的尊用就是把上面那个String中的值都遍历一遍
                         for(int i = 0 ; i <values.length;i++){
                                 //这个是三元运算符
                                 valueStr = (i == values.length -1)?valueStr + values[i]:valueStr + values[i]+",";
                             }
                         //把数据全部加进map集合中   name就是key  valueStr就是value
                         params.put(name,valueStr);
                     }
         //sign就是签名    trade_status是交易的状态
        System.out.println("支付宝回调,sign:{},trade_status:{},参数:{}"+params.get("sign")+","+params.get("trade_status")+","+params.toString());
        String out_trade_no = params.get("out_trade_no");
         System.out.println("订单编号:"+out_trade_no);
//         Order order = orderService.selectBySn(out_trade_no);
//        model.addAttribute("order",order);
//        model.addAttribute("email",order.getEmail());
//        model.addAttribute("orderId",order.getId());
        return"redirect:orderList";
//        return "/order/paySuccess";
    }

    @RequestMapping("/view")
    public String view(Model model,Long orderId){
//        Order order = orderService.selectById(orderId);
        Map query_map = new HashMap();
        query_map.put("orderId",orderId);
        Map orderMap = orderService.getOrder(query_map);
        model.addAttribute("order",orderMap);
        Map<String,Object> map = new HashMap<>();
        map.put("orderid",orderId);
        List<Map<String,Object>> listview = orderService.selectOrderView(map);
        model.addAttribute("listview",listview);
        model.addAttribute("pathUrl",pathUrl);
        BigDecimal totalamount = orderService.getTotalAmount(orderId);
        model.addAttribute("totalamount",totalamount);
        return "/order/view";
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


    /**
     * 用戶刪除訂單
     * @param jsonObject
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody JSONObject jsonObject ){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            Member member = memberService.getCurrent();
            String[] ids = jsonObject.getString("ids").split(",");
            for (String id : ids) {
                Order order = orderService.selectById(Long.valueOf(id));
                order.setIsMemberDelete(true);
                orderService.updateOrder(order);
                OrderLog orderLog = new OrderLog();
                orderLog.setCreateDate(new Date());
                orderLog.setModifyDate(new Date());
                orderLog.setContent("用戶刪除订单");//操作内容
                orderLog.setOperator(member.getUsername());//前台用户名称(账号）
                orderLog.setType(1);//用户类型1前台用户
                orderLog.setOrders(order.getId());//订单表id
                orderLog.setIsDeleted(false);
                orderLog.setOperatorName(member.getName());//操作人姓名
                orderLogService.insertOrderLog(orderLog);//插入订单日志
            }

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
     * 上传病例报告
     * @param files
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadCaseReport")
    @ResponseBody
    public String uploadCaseReport(@RequestParam("file") MultipartFile[] files,Long id,HttpServletRequest request) throws IOException {
        System.out.println("id:"+id);
        Integer i = 1;
        JSONArray jsonArray = new JSONArray();
        for (MultipartFile file : files) {
            JSONObject jsonObject = new JSONObject();
            //上传到远程服务器
            String path = FileUpload.upload(file,"/caseReport/");
            jsonObject.put("index",i);
            String caseReport_url =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
            jsonObject.put("path",caseReport_url);
            jsonObject.put("name",file.getOriginalFilename());
            jsonArray.add(jsonObject);
            i++;

        }
        System.out.println("上传病例结果:"+jsonArray);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setMemberCaseReport(jsonArray.toString());
        int updateByPrimaryKeySelective = orderItemService.updateByPrimaryKeySelective(orderItem);
        if (updateByPrimaryKeySelective>0){
            return ResultUtil.success();
        }
        return ResultUtil.error("上传病例失败!");

    }


    /**
     *  申请码
     * @param jsonObject
     * @return
     * @throws IOException
     */
    @PostMapping("/applicationCode")
    @ResponseBody
    public String read(@RequestBody String jsonObject,HttpServletRequest request ) throws Exception {
        Member member = memberService.getCurrent();
        System.out.println("用户的申请码为:"+jsonObject);
        JSONObject jsonObjectCode = JSON.parseObject(jsonObject);
        String code = jsonObjectCode.getString("code");
        Long orderItemId = jsonObjectCode.getLong("orderItemId");
        OrderItem orderItem = orderItemService.selectByPrimaryKey(orderItemId);
        Order order = orderService.selectById(orderItem.getOrders());
        Receiver receiver = receiverService.selectById(order.getReceiverId());//此处后续需修改
        Map query_map = new HashMap();
        query_map.put("companyId",order.getCompanyId());
        query_map.put("isSystem",true);

        Admin admin = adminService.getSystemCompanyAdmin(query_map);
//        EmailEntity email = new EmailEntity();
        Integer CaseNumber = 0;//按次购买
        Integer Deadline = 0;//按时间购买
        if (orderItem.getPurchaseName().equals("1个病例")){
             CaseNumber = orderItem.getQuantity();
        }
        if (orderItem.getPurchaseName().equals("2个病例")){
            CaseNumber = orderItem.getQuantity()*2;
        }
        if (orderItem.getPurchaseName().equals("5个病例")){
            CaseNumber = orderItem.getQuantity()*5;
        }

        if (orderItem.getPurchaseName().equals("1个月")){
            Deadline = orderItem.getQuantity()*30;
        }
        if (orderItem.getPurchaseName().equals("2个月")){
            Deadline = orderItem.getQuantity()*2*30;
        }
        if (orderItem.getPurchaseName().equals("5个月")){
            Deadline = orderItem.getQuantity()*5*30;
        }


        Map<String, String> data = new HashMap<String, String>();
        data.put("System", "Apsaras Brachy 3");
        data.put("Time", DateUtil.getDatetoString("yyyy-MM-dd HH:mm:ss",new Date()));
        data.put("HospitalName", receiver.getHospital());
        data.put("Deadline", String.valueOf(Deadline));
        data.put("CaseNumber", String.valueOf(CaseNumber));
        data.put("SerialNumber", code);
        String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        System.out.println(url);
        data.put("url", url+"/feiTainZhaoYe/authorizationCode");
        data.put("orderItemId", orderItemId.toString());
        String content = XmlUtil.mapToXmlRequest(data);
        System.out.println(content);
        MailInfo mailInfo = new MailInfo(aliyunSendEmailAccount,
                aliyunSendEmailPassword,
                admin.getEmail(), "surgi-plan",
                admin.getName(), "申请码", content,null);
        boolean flag = MailSendUtils.sendEmail(mailInfo);

        if (flag){
            OrderLog orderLog = new OrderLog();
            orderLog.setCreateDate(new Date());
            orderLog.setModifyDate(new Date());
            orderLog.setContent(member.getName()+"上传申请码");
            orderLog.setOperator(member.getUsername());
            orderLog.setType(1);
            orderLog.setOrders(orderItem.getOrders());
            orderLog.setIsDeleted(false);
            orderLog.setOperatorName(member.getName());
            int insertSelective = orderLogService.insertOrderLog(orderLog);//订单日志表完成
        }
        return flag?ResultUtil.success():ResultUtil.error("提交申请码失败！");
    }







    /**
     * 支付宝支付
     * @param httpRequest
     * @param httpResponse
     * @param amount
     * @throws IOException
     */
    @RequestMapping("/orderalipay")
    public void pay(HttpServletRequest httpRequest, HttpServletResponse httpResponse,Long orderPaymentId,BigDecimal amount) throws IOException {
        OrderPayment orderPayment = orderPaymentService.selectByPrimaryKey(orderPaymentId);
        if(amount.compareTo(orderPayment.getAmount())!=0){
            //如果传进来的价格和计算的价格不同
            System.out.println("该价格可能被人串改");
            return;

        }
        amount = amount.setScale(2,BigDecimal.ROUND_HALF_UP);//只能入不能舍!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        String orderTitle = "SP-购物-软件";//订单标题
        Member member = memberService.getCurrent();//当前用户
        JSONObject json = new JSONObject();//回调使用参数
        json.put("memberId",member.getId());//用户表id
        json.put("orderPaymentId",orderPaymentId);//付款单id
        json.put("amount",amount);//订单金额
        String passback_params = URLEncoder.encode(json.toString(),"UTF-8");
//        byte[] bytes= json.toString().getBytes();
//        String string = new String("".getBytes("UTF-8"), "UTF-8");
        String APP_ID="2019111969290311";//APPID 即创建应用后生成
        String APP_PRIVATE_KEY=privatekey;//开发者私钥，由开发者自己生成
        String ALIPAY_PUBLIC_KEY=publickey;//支付宝公钥，由支付宝生成
        String SIGN_TYPE="RSA2";//商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐使用 RSA2
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, SIGN_TYPE); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        StringBuffer url=httpRequest.getRequestURL();
        String url2=url.toString();
        url2=url2.substring(0,url2.lastIndexOf("/"));

        String path = httpRequest.getContextPath();
        String basePath = httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+path+"/";
        // alipayRequest.setReturnUrl(url2);
        alipayRequest.setReturnUrl(url2+"/paySuccess");//000000000000000000//支付成功后跳转的页面
//          alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址


        alipayRequest.setNotifyUrl(basePath+"/payment/alipayNotifyUrl");//在公共参数中设置回跳和通知地址
        System.out.println("支付回调地址：》》》》》》》》》》》》》》》》》》》》"+basePath+"/payment/alipayNotifyUrl");
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+orderPayment.getOutTradeNo()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+amount+"," +
                "    \"subject\":\""+orderTitle+"\"," +
                "    \"body\":\""+orderTitle+"\"," +
                "    \"passback_params\":\""+passback_params+"\"" +
//                 "    \"extend_params\":{" +
//                 "    \"sys_service_provider_id\":\"2088511833207846\"" +
//                 "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println("统一下单:"+form);
        httpResponse.setContentType("text/html;charset=UTF-8" );
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }



    //微信统一下单扫码支付
    @RequestMapping(value = "/wxPay", method = RequestMethod.GET)
    public void wxPay(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam Long orderPaymentId) throws Exception {

        OrderPayment orderPayment = orderPaymentService.selectByPrimaryKey(orderPaymentId);
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appid);	//	是	公众账号ID	String(32)	wxd678efh567hg6787	微信支付分配的公众账号ID（企业号corpid即为此appId）
        data.put("mch_id", mch_id);	//	是	商户号	String(32)	1230000109	微信支付分配的商户号
//        data.put("device_info", "");	//	否	设备号	String(32)	1.3467E+13	自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("nonce_str", UUIDUtil.getUUID());	//	是	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，长度要求在32位以内。推荐随机数生成算法
        data.put("sign_type", "MD5");	//	否	签名类型	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
        data.put("body", "购买软件");	//	是	商品描述	String(128)	腾讯充值中心-QQ会员充值	商品简单描述，该字段请按照规范传递，具体请见参数规定

        Map query_map = new HashMap();
        JSONObject jsonObjectDetail = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        query_map.put("orderIds",orderPayment.getOrderId().split(","));
        List<Map> orders = orderService.getOrders(query_map);
        for (Map order : orders) {
            List<OrderItem> orderItems = orderItemService.selectByOrderid(Long.valueOf(String.valueOf(order.get("id"))));
            order.put("orderItems",orderItems);
            for (OrderItem orderItem : orderItems) {
                JSONObject object = new JSONObject();
                object.put("goods_id",orderItem.getProduct());
                object.put("goods_name",orderItem.getName());
                object.put("quantity",orderItem.getQuantity());
                object.put("price",orderItem.getPrice().multiply(new BigDecimal(100)).intValue());
                jsonArray.add(object);
            }
        }
        jsonObjectDetail.put("goods_detail",jsonArray);
        data.put("detail", jsonObjectDetail.toJSONString());	//	否	商品详情	String(6000)		商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见“单品优惠参数说明”
//		data.put("attach", "");	//	否	附加数据	String(127)	深圳分店	附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。

//		商户订单号为 订单编号与订单id 拼接 切记 商户订单号里面不允许有 英文字符和汉字(只能是纯数字) 否则加密不成功
        Date newDate = new Date();
        String out_trade_no = orderPayment.getId() +"_"+  String.format("%0"+(31-orderPayment.getId().toString().length())+"d%n",newDate.getTime()).trim();
        System.out.println("商户订单号:"+out_trade_no+"长度:"+out_trade_no.length());
        data.put("out_trade_no", out_trade_no);	//	是	商户订单号	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
        data.put("fee_type", "CNY");	//	否	标价币种	String(16)	CNY	符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
//		价格
        Integer total_fee = orderPayment.getAmount().multiply(new BigDecimal(100)).intValue();//转换成分 并转int
        data.put("total_fee", String.valueOf(total_fee));	//	是	标价金额	Int	88	订单总金额，单位为分，详见支付金额
        InetAddress ip4 = Inet4Address.getLocalHost();
        System.out.println("终端ip:"+ip4.getHostAddress());
        data.put("spbill_create_ip", ip4.getHostAddress());	//	是	终端IP	String(64)	123.12.12.123	支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
//		data.put("time_start", "");	//	否	交易起始时间	String(14)	2.00912E+13	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//		data.put("time_expire", "");	//	否	交易结束时间	String(14)	2.00912E+13	订time_expire只能第一次下单传值，不允许二次修改，二次修改系统将报错。如用户支付失败后，需再次支付，需更换原订单号重新下单。建议：最短失效时间间隔大于1分钟
//		data.put("goods_tag", "");	//	否	订单优惠标记	String(32)	WXG	订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
        String notify_url =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/payment/weChatNotifyUrl?orderPaymentId="+orderPayment.getId();
        System.out.println("微信支付异步通知地址:"+notify_url);
        data.put("notify_url", notify_url);	//	是	通知地址	String(256)	http://www.weixin.qq.com/wxpay/pay.php	异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        data.put("trade_type", "NATIVE");	//	是	交易类型	String(16)	JSAPI	JSAPI -JSAPI支付NATIVE -Native支付APP -APP支付			说明详见参数规定
//		data.put("product_id", "");	//	否	商品ID	String(32)	1.22354E+22	trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
//		data.put("limit_pay", "");	//	否	指定支付方式	String(32)	no_credit	上传此参数no_credit--可限制用户不能使用信用卡支付
        data.put("key",key);
        String sign_xml = XmlUtil.mapToXml(data);
        System.out.println("签名钱xml："+sign_xml);
        String sign = WxPayUtil.createSign(data);
        data.put("sign",sign);	//
        data.remove("key");
        String data_xml = XmlUtil.mapToXml(data);
        String return_data_xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder",data_xml);
        System.out.println("统一下单结果:"+return_data_xml);
        Map<String,Object> return_Data_Map = XmlUtil.getXmlToMap(return_data_xml);
        String content = "";
        if (return_Data_Map.get("return_code").toString().equals("SUCCESS")){
            if (return_Data_Map.get("result_code").toString().equals("SUCCESS")){
                String code_url = return_Data_Map.get("code_url").toString();
                content = code_url;
            }else{
                content = return_Data_Map.get("err_code_des").toString();
            }

        }else {
            content = return_Data_Map.get("return_msg").toString();
        }
        QRCoderUtil.creatRrCode(content, 128,128,response);
    }


    /**
     * 微信支付轮询查询账单
     * @param orderPaymentId
     * @throws IOException
     */
    @PostMapping("/wxOrderquery")
    @ResponseBody
    public Boolean wxOrderquery(Long orderPaymentId) throws Exception {
        boolean fals = true;
        OrderPayment orderPayment = orderPaymentService.selectByPrimaryKey(orderPaymentId);
        if (orderPayment.getTransactionId()==null){
            return false;
        }
//        String out_trade_no = orderPayment.getOutTradeNo();
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appid);	//	是	公众账号ID	String(32)	wxd678efh567hg6787	微信支付分配的公众账号ID（企业号corpid即为此appId）
        data.put("mch_id", mch_id);	//	是	商户号	String(32)	1230000109	微信支付分配的商户号
//        data.put("device_info", "");	//	否	设备号	String(32)	1.3467E+13	自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("nonce_str", UUIDUtil.getUUID());	//	是	随机字符串	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，长度要求在32位以内。推荐随机数生成算法
        data.put("sign_type", "MD5");	//	否	签名类型	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
        data.put("body", "购买软件");	//	是	商品描述	String(128)	腾讯充值中心-QQ会员充值	商品简单描述，该字段请按照规范传递，具体请见参数规定
//        System.out.println("商户订单号长度:"+out_trade_no.length());
//        data.put("out_trade_no", out_trade_no);	//	是	商户订单号	String(32)	2.01508E+13	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
        data.put("transaction_id", orderPayment.getTransactionId());	//	微信的订单号，建议优先使用
        data.put("trade_type", "NATIVE");	//	是	交易类型	String(16)	JSAPI	JSAPI -JSAPI支付NATIVE -Native支付APP -APP支付			说明详见参数规定
        data.put("key",key);
        String sign_xml = XmlUtil.mapToXml(data);
        System.out.println("签名钱xml："+sign_xml);
        String sign = WxPayUtil.createSign(data);
        data.put("sign",sign);	//
        data.remove("key");
        String data_xml = XmlUtil.mapToXml(data);
        String return_data_xml = HttpUtil.post("https://api.mch.weixin.qq.com/pay/orderquery",data_xml);
        Map<String,Object> return_Data_Map = XmlUtil.getXmlToMap(return_data_xml);
        if (return_Data_Map.get("return_code").toString().equals("SUCCESS")){
            if (return_Data_Map.get("result_code").toString().equals("SUCCESS")){
                if (return_Data_Map.get("trade_state").toString().equals("SUCCESS")){
                    fals = true;
                }else {
                    fals = false;
                }
            }else{
                fals = false;
            }

        }else {
            fals = false;
        }
        return fals;
    }


}
