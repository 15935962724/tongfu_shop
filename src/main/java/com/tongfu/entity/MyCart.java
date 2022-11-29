package com.tongfu.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 自定义我的购物车
 */
public class MyCart {

    private String name;//商品名称

    private String image;//商品图片

    private String sn;//商品编码

    private BigDecimal price;//单价

    private Long status ; //商品状态

    private boolean isMarketable;

    private Integer quantity;//数量

    private Long purchaseId;//规格id

    private Long productId;//产品id

    private String purchaseName;//规格名称

    private Long cart;//购物车id

    private Long itemid;//购物车项id

    private Long isvalue;//是否是增值服务商品

    private Long companyId;//供应商id

    private String companyName;//供应商公司名称

    private Long  purId;//实际购买规格id

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isMarketable() {
        return isMarketable;
    }

    public void setMarketable(boolean marketable) {
        isMarketable = marketable;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCart() {
        return cart;
    }

    public void setCart(Long cart) {
        this.cart = cart;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Long getIsvalue() {
        return isvalue;
    }

    public void setIsvalue(Long isvalue) {
        this.isvalue = isvalue;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public boolean getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }
}