package com.tongfu.controller.weixin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.*;
import com.tongfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;


@Controller("weixincart")
@RequestMapping("/weixin/cart")
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
    private MemberService memberService;

	@Autowired
    private CompanyService companyService;

	@Autowired
    private PurchaseService purchaseService;

    //详情页添加购物车按钮
	@RequestMapping("/addCart")
    @ResponseBody
	public String addCart(@RequestBody JSONObject jsonObject){
        Integer num = jsonObject.getInteger("num");
        Long productId = jsonObject.getLong("productId");
        Long purchaseId = jsonObject.getLong("purchaseId");
	   Member member = memberService.getCurrent();
        Product product = productService.selectProductById(productId);
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
        CartItem item = cartItemService.selectByCartPurchase(cart.getId(),purchaseId);
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
            cartItem.setProduct(productId);
            cartItem.setIsDeleted(false);
            cartItem.setProductPurchase(purchaseId);
            cartItem.setIsAddedvalue(product.getIsAddService());
            cartItemService.insertCartItem(cartItem);
        }
//        int sumqua = cartService.selectCartQuantitySum(member.getId());
        return ResultUtil.success();
	}


    //我的购物车页面增加或减少数量，改变购物车项
    @RequestMapping("/changeCart")
    @ResponseBody
    public String changeCartNum(@RequestBody JSONObject jsonObject ){
	    //根据用户id和规格id查询到购物车项
        Long cart = jsonObject.getLong("cart");
        Long purchaseId = jsonObject.getLong("purchaseId");
        Integer num = jsonObject.getInteger("num");
        CartItem cartItem = cartItemService.selectByCartPurchase(cart,purchaseId);
        cartItem.setQuantity(num);
        int result = cartItemService.updateCartItem(cartItem);
        return ResultUtil.success();
    }

    /**
     * 购物车为0的话删除购物车
     * @param jsonObject
     * @return
     */
    @RequestMapping("/delCart")
    @ResponseBody
    //删除购物车项，如果购物车项删除至0，则删除购物车
    public String delCart(@RequestBody JSONObject jsonObject){
        //根据购物车id和规格id查询到购物车项
        CartItem cartItem = cartItemService.selectByCartPurchase(jsonObject.getLong("cartId"),jsonObject.getLong("purchaseid"));
        int result=cartItemService.delCartItem(cartItem.getId());
        return ResultUtil.success();
    }

    //跳转到购物车列表页
    @RequestMapping("/listCart")
    @ResponseBody
    public String listCart(){
        JSONObject returnJson = new JSONObject();
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
            Integer[] isAddedvalueTrue= {2};
            query_map.put("isAddedvalue",isAddedvalueTrue);
            List<MyCart> listServiceMy=cartItemService.selectMyCart(query_map);
            cart.put("listServiceMy",listServiceMy);
        }
        returnJson.put("totalNum",totalNum);
        returnJson.put("carts",carts);
        return ResultUtil.success(returnJson);
    }


    /**
     * 删除购物车列表
     * @param jsonObject
     * @return
     */
    @RequestMapping("/delCartItem")
    @ResponseBody
    public String delCartItem(@RequestBody JSONObject jsonObject){
        JSONArray ids = jsonObject.getJSONArray("ids");
        for (Object id : ids) {
            cartItemService.delCartItem(Long.valueOf(id.toString()));
        }
        return ResultUtil.success();
    }

    /**
     * //从购物车跳转到结算页
     * @return
     */
    @RequestMapping("/balance")
    @ResponseBody
    public String balance(@RequestBody JSONObject jsonObject ){
        JSONObject returnJson = new JSONObject();
        Member member =memberService.getCurrent();
	    //根据用户id查询我的购物车
        Map query_map = new HashMap();
        query_map.put("member",member.getId());
        query_map.put("cartItemIds",jsonObject.getString("cartItemIds").split(","));
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
        returnJson.put("byCartItems",byCartItems);//购物车信息
        //收货人信息
        List<Receiver> listReceiver = receiverService.selectByMember(member.getId());
        returnJson.put("listReceiver",listReceiver);
        returnJson.put("totalNum",totalNum);
        returnJson.put("totalAmount",totalAmount);
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
        returnJson.put("invoiceP",invoiceP);//普通发票
        returnJson.put("invoiceZ",invoiceZ);//增值税专用发票
        return ResultUtil.success(returnJson);
    }

    @RequestMapping("/gotobalance")//从商品详情页跳转到结算页
    @ResponseBody
    public String gotobalance(@RequestBody JSONObject jsonObject){
	    JSONObject returnJson = new JSONObject();
        Long goproductId = jsonObject.getLong("goproductId");
        Long gopurchaseid = jsonObject.getLong("gopurchaseId");
        Integer gonum = jsonObject.getInteger("gonum");
        Member member = memberService.getCurrent();
        Product product = productService.selectProductById(goproductId);
        Long isaddedvalue = product.getIsAddService();
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
        returnJson.put("byCartItems",byCartItems);//购物车信息
        //收货人信息
        List<Receiver> listReceiver = receiverService.selectByMember(member.getId());
        returnJson.put("listReceiver",listReceiver);
        //计算金额总计
        BigDecimal totalAmount= new BigDecimal(0);
        BigDecimal gonum2 = new BigDecimal(gonum);
        totalAmount = price.multiply(gonum2);
        returnJson.put("totalNum",gonum);
        returnJson.put("totalAmount",totalAmount);
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
        returnJson.put("invoiceP",invoiceP);//普通发票
        returnJson.put("invoiceZ",invoiceZ);//增值税专用发票
        returnJson.put("goproductid",goproductId);//产品id（product表的主键）
        returnJson.put("gopurchaseid",gopurchaseid);//规格id（product_purchase表的主键）
        returnJson.put("gonum",gonum);//数量
        returnJson.put("isaddedvalue",isaddedvalue);//是否是增值服务产品
        return ResultUtil.success(returnJson);
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
