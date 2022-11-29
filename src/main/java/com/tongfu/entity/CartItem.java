package com.tongfu.entity;

import java.util.Date;

public class CartItem {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Integer quantity;

    private Long cart;

    private Long product;

    private Boolean isDeleted;

    private Long productPurchase;

    private Long isAddedvalue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCart() {
        return cart;
    }

    public void setCart(Long cart) {
        this.cart = cart;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getProductPurchase() {
        return productPurchase;
    }

    public void setProductPurchase(Long productPurchase) {
        this.productPurchase = productPurchase;
    }

    public Long getIsAddedvalue() {
        return isAddedvalue;
    }

    public void setIsAddedvalue(Long isAddedvalue) {
        this.isAddedvalue = isAddedvalue;
    }
}