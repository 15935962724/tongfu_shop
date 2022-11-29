package com.tongfu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.FileUpload;
import com.tongfu.Util.MapUtil;
import com.tongfu.config.ShiroConfig;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import com.tongfu.service.impl.CartServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {


	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ReceiverService receiverService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ProductService productService;

	@Autowired
    private ProductPurchaseService productPurchaseService;

    @Autowired
    private PurchaseService  purchaseService;

	@Autowired
    private MemberService memberService;

	@Autowired
    private CompanyService companyService;

    @Value("${path-url}")
    private String pathUrl;

	@RequestMapping("/addCart")
    //详情页添加购物车按钮
    @ResponseBody
	public int addCart(Integer num, Long productid, Long purchaseid){
	   Member member = memberService.getCurrent();
        Product product = productService.selectProductById(productid);
        //先查询当前购物车中有没有当前用户
        Map query_map = new HashMap();
        query_map.put("member",member.getId());
        query_map.put("companyId",product.getCompanyId());
        Cart cart = cartService.selectByMember(query_map);
        if(cart==null){
            //添加购物车
             cart=new Cart();
            cart.setCreateDate(new Date());
            cart.setModifyDate(new Date());
            cart.setMember(member.getId());
            cart.setCompanyId(product.getCompanyId());
            cart.setIsDeleted(false);
            cartService.insertCart(cart);
        }

        //如果购物车中有当前用户的记录，则根据当前购物车id和规格id查询购物车项的记录
        CartItem item = cartItemService.selectByCartPurchase(cart.getId(),purchaseid);
        if(item!=null){
            //修改购物车项的数量
            System.out.println("修改购物车项的数量："+item.getQuantity()+num);
            item.setQuantity(item.getQuantity()+num);
            cartItemService.updateCartItem(item);

        }else{
            //添加购物车项
            CartItem cartItem=new CartItem();
            cartItem.setCreateDate(new Date());
            cartItem.setModifyDate(new Date());
            cartItem.setQuantity(num);
            cartItem.setCart(cart.getId());
            cartItem.setProduct(productid);
            cartItem.setIsDeleted(false);
            cartItem.setProductPurchase(purchaseid);
            cartItem.setIsAddedvalue(product.getIsAddService());
            cartItemService.insertCartItem(cartItem);
        }
        int sumqua = cartService.selectCartQuantitySum(member.getId());
        return sumqua;
	}
    @RequestMapping("/changeCart")
    @ResponseBody
    //我的购物车页面增加或减少数量，改变购物车项
    public int changeCartNum(Long cart,Long purchaseid,Integer num){
	    //根据用户id和规格id查询到购物车项
        CartItem cartItem = cartItemService.selectByCartPurchase(cart,purchaseid);
        cartItem.setQuantity(num);
        int result = cartItemService.updateCartItem(cartItem);
        return result;
    }

    @RequestMapping("/delCart")
    @ResponseBody
    //删除购物车项，如果购物车项删除至0，则删除购物车
    public int delCart(Long cart,Long purchaseid){

        //根据购物车id和规格id查询到购物车项
        CartItem cartItem = cartItemService.selectByCartPurchase(cart,purchaseid);
        int result=cartItemService.delCartItem(cartItem.getId());
        return result;
    }

    @RequestMapping("/listCart")//跳转到购物车列表页
    public String listCart(Model model)
    {
        Member member =memberService.getCurrent();
        Map query_map = new HashMap();
        query_map.put("member",member.getId());
        List<Map> carts = cartService.getCarts(query_map);
        Integer totalNum = 0;
        for (Map cart : carts) {
            //根据用户id查询我的购物车
            Integer[] isAddedvalueFalse = {1,3,4};
            query_map.put("isAddedvalue",isAddedvalueFalse);
            query_map.put("cartId",cart.get("id"));
            List<MyCart> listMy=cartItemService.selectMyCart(query_map);
            cart.put("listMy",listMy);
            totalNum+=listMy.stream().mapToInt(MyCart::getQuantity).sum();
//            Integer[] isAddedvalueTrue= {2};
//            query_map.put("isAddedvalue",isAddedvalueTrue);
//            List<MyCart> listServiceMy=cartItemService.selectMyCart(query_map);
//            cart.put("listServiceMy",listServiceMy);
        }

        model.addAttribute("totalNum",totalNum);
        model.addAttribute("carts",carts);
        model.addAttribute("pathUrl",pathUrl);
        return "/cart/list";
    }

    @RequestMapping("/balance")//从购物车跳转到结算页
    public String balance(Model model,String cartItemIds,Long isaddedvalue){
        Member member =memberService.getCurrent();
	    //根据用户id查询我的购物车
        Map query_map = new HashMap();
        query_map.put("member",member.getId());
        query_map.put("cartItemIds",cartItemIds.split(","));
        List<Map> byCartItems = cartService.getByCartItem(query_map);
        Integer totalNum = 0;
        BigDecimal totalAmount = new BigDecimal(0);
        for (Map map : byCartItems) {
            query_map.put("cartId",map.get("id"));

            List<MyCart> listMy=cartItemService.selectMyCart(query_map);
            totalNum+=listMy.stream().mapToInt(MyCart::getQuantity).sum();

            totalAmount = totalAmount.add(listMy.stream().map(d -> d.getPrice().multiply(new BigDecimal(d.getQuantity())) )
                    .reduce(new BigDecimal(0),BigDecimal::add));

            map.put("listMy",listMy);
        }
        System.out.println("商品总数:"+totalNum);
        model.addAttribute("totalNum",totalNum);
        model.addAttribute("totalAmount",totalAmount);
        model.addAttribute("byCartItems",byCartItems);//购物车项id
        model.addAttribute("cartItems",JSONObject.toJSONString(byCartItems));//购物车项id项
        System.out.println("购物车数据:"+JSONObject.toJSONString(byCartItems));
        //收货人信息
        List<Receiver> listReceiver = receiverService.selectByMember(member.getId());
        model.addAttribute("listReceiver",listReceiver);
        //默认的收货地址
        Receiver receiver = receiverService.selectIsDefault(member.getId());
        model.addAttribute("receiverId",receiver==null?0:receiver.getId());

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
        Map<String,Object> map1=new HashMap<>();
        map1.put("member",member.getId());
        map1.put("type",1);
        Invoice invoiceZ = invoiceService.selectByMember(map1);
        if(invoiceZ==null){
            invoiceZ=new Invoice();
        }
        model.addAttribute("invoiceP",invoiceP);//普通发票
        model.addAttribute("invoiceZ",invoiceZ);//增值税专用发票
        model.addAttribute("isaddedvalue",isaddedvalue);//ture：增值服务订单 false：普通订单
        model.addAttribute("pathUrl",pathUrl);
        return "/cart/balance";
    }

    @RequestMapping("/gotobalance")//从商品详情页跳转到结算页
    public String gotobalance(Model model,Long goproductid,Long gopurchaseid,Integer gonum){

	    Member member = memberService.getCurrent();
	    Product product = productService.selectProductById(goproductid);
        ProductPurchase productPurchase = productPurchaseService.selectById(gopurchaseid);
        List<Map> byCartItems = new ArrayList<>();
        Map cart = new HashMap();
        cart.put("company_id",product.getCompanyId());
        Company company = companyService.selectByPrimaryKey(product.getCompanyId());
        cart.put("name",company.getName());
        cart.put("is_deleted",false);
        cart.put("create_date",new Date());
        cart.put("modify_date",new Date());
        cart.put("member",member.getId());

        MyCart myCart = new MyCart();
	    myCart.setName(product.getName());
        myCart.setImage(product.getImage());
        BigDecimal price = productPurchase==null?product.getPrice():productPurchase.getPrice();
        myCart.setPrice(price);
        myCart.setQuantity(gonum);
        myCart.setPurchaseId(gopurchaseid);
        myCart.setImage(product.getImage());
        myCart.setIsMarketable(product.getIsMarketable());
        myCart.setIsvalue(product.getIsAddService());
        myCart.setProductId(product.getId());
        myCart.setPurchaseName("-");
        myCart.setItemid(0l);
        if (productPurchase!=null){
            Purchase purchase = purchaseService.selectByPrimaryKey(Long.valueOf(productPurchase.getPurchaseId()));
            myCart.setPurchaseName(purchase.getName());
        }
        myCart.setSn(product.getSn());
        myCart.setStatus(product.getStatus());

        //根据用户id查询我的购物车
        List<MyCart> list = new ArrayList<>();
        list.add(myCart);
        cart.put("listMy",list);
        byCartItems.add(cart);

        BigDecimal totalAmount = new BigDecimal(0);
        totalAmount = price.multiply(new BigDecimal(gonum));
        model.addAttribute("totalNum",gonum);
        model.addAttribute("totalAmount",totalAmount);
        model.addAttribute("byCartItems",byCartItems);//购物车项id
        model.addAttribute("cartItems",JSONObject.toJSONString(byCartItems));//购物车项id项

        //收货人信息
        List<Receiver> listReceiver = receiverService.selectByMember(member.getId());
        model.addAttribute("listReceiver",listReceiver);
//        //默认的收货地址
        Receiver receiver = receiverService.selectIsDefault(member.getId());
        model.addAttribute("receiverId",receiver!=null?receiver.getId():null);

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
        Map<String,Object> map1=new HashMap<>();
        map1.put("member",member.getId());
        map1.put("type",1);
        Invoice invoiceZ = invoiceService.selectByMember(map1);
        if(invoiceZ==null){
            invoiceZ=new Invoice();
        }
        model.addAttribute("invoiceP",invoiceP);//普通发票
        model.addAttribute("invoiceZ",invoiceZ);//增值税专用发票
        model.addAttribute("buytype",1);//从立即购买直接跳到结算页面
        model.addAttribute("goproductid",goproductid);//产品id（product表的主键）
        model.addAttribute("gopurchaseid",gopurchaseid);//规格id（product_purchase表的主键）
        model.addAttribute("isaddedvalue",product.getIsAddService());//是否是增值服务产品
        model.addAttribute("pathUrl",pathUrl);
        return "/cart/balance";
    }




    @RequestMapping("/sumCartQuantity")
    @ResponseBody
    //查询购物车中的数量
    public int sumCartQ(Long member){
        Integer cartQsum = cartService.selectCartQuantitySum(member);//该用户的购物车下的商品总数量
        int cartQsum2 = 0 ;
        if(cartQsum!=null){
            cartQsum2 = cartQsum;
        }
        return cartQsum2;
    }

	 
	 
	 
}
