package com.tongfu.entity;

import java.math.BigDecimal;

/**
 * 自定义 套餐商品提交订单属性
 */
public class ProductMealOrder {

    private String oreceiverId;//<!--收货地址，如果没有选择，则是默认的-->
    private String orderInvoiceType;//<!--发票类型 1普 2专 -->
    private Boolean issueInvoice;//<!--是否开具发票-->
    private String productMealId;//<!--购买套餐ID-->
    private String orderInvoicePtitle;//<!--普通发票抬头-->
    private String orderInvoicePcontent;//<!--普通发票发票内容-->
    private String orderInvoicePmobile;//<!--普通发票收件人电话-->
    private String orderInvoicePemail;//<!--普通发票收件人邮箱-->
    private String orderInvoiceZcompany;//<!--专票单位名称-->
    private String orderInvoiceZidentityno;//<!--专票纳税人识别号-->
    private String orderInvoiceZregAddress;//<!--专票注册地址-->
    private String orderInvoiceZregmobile;//<!--专票单位注册地电话->
    private String orderInvoiceZbank;//<!--专票单位开户银行-->
    private String orderInvoiceZaccount;//<!--专票单位账户-->
    private String orderInvoiceZname;//<!--专票收票人姓名-->
    private String orderInvoiceZmobile;//<!--专票收票人电话-->
    private Long orderInvoiceZareaId;//<!--专票收票人县(区)-->
    private String orderInvoiceZaddress;//<!--专票收票人详细地址-->

    public String getOreceiverId() {
        return oreceiverId;
    }

    public void setOreceiverId(String oreceiverId) {
        this.oreceiverId = oreceiverId;
    }

    public String getOrderInvoiceType() {
        return orderInvoiceType;
    }

    public void setOrderInvoiceType(String orderInvoiceType) {
        this.orderInvoiceType = orderInvoiceType;
    }

    public Boolean getIssueInvoice() {
        return issueInvoice;
    }

    public void setIssueInvoice(Boolean issueInvoice) {
        this.issueInvoice = issueInvoice;
    }

    public String getProductMealId() {
        return productMealId;
    }

    public void setProductMealId(String productMealId) {
        this.productMealId = productMealId;
    }

    public String getOrderInvoicePtitle() {
        return orderInvoicePtitle;
    }

    public void setOrderInvoicePtitle(String orderInvoicePtitle) {
        this.orderInvoicePtitle = orderInvoicePtitle;
    }

    public String getOrderInvoicePcontent() {
        return orderInvoicePcontent;
    }

    public void setOrderInvoicePcontent(String orderInvoicePcontent) {
        this.orderInvoicePcontent = orderInvoicePcontent;
    }

    public String getOrderInvoicePmobile() {
        return orderInvoicePmobile;
    }

    public void setOrderInvoicePmobile(String orderInvoicePmobile) {
        this.orderInvoicePmobile = orderInvoicePmobile;
    }

    public String getOrderInvoicePemail() {
        return orderInvoicePemail;
    }

    public void setOrderInvoicePemail(String orderInvoicePemail) {
        this.orderInvoicePemail = orderInvoicePemail;
    }

    public String getOrderInvoiceZcompany() {
        return orderInvoiceZcompany;
    }

    public void setOrderInvoiceZcompany(String orderInvoiceZcompany) {
        this.orderInvoiceZcompany = orderInvoiceZcompany;
    }

    public String getOrderInvoiceZidentityno() {
        return orderInvoiceZidentityno;
    }

    public void setOrderInvoiceZidentityno(String orderInvoiceZidentityno) {
        this.orderInvoiceZidentityno = orderInvoiceZidentityno;
    }

    public String getOrderInvoiceZregAddress() {
        return orderInvoiceZregAddress;
    }

    public void setOrderInvoiceZregAddress(String orderInvoiceZregAddress) {
        this.orderInvoiceZregAddress = orderInvoiceZregAddress;
    }

    public String getOrderInvoiceZregmobile() {
        return orderInvoiceZregmobile;
    }

    public void setOrderInvoiceZregmobile(String orderInvoiceZregmobile) {
        this.orderInvoiceZregmobile = orderInvoiceZregmobile;
    }

    public String getOrderInvoiceZbank() {
        return orderInvoiceZbank;
    }

    public void setOrderInvoiceZbank(String orderInvoiceZbank) {
        this.orderInvoiceZbank = orderInvoiceZbank;
    }

    public String getOrderInvoiceZaccount() {
        return orderInvoiceZaccount;
    }

    public void setOrderInvoiceZaccount(String orderInvoiceZaccount) {
        this.orderInvoiceZaccount = orderInvoiceZaccount;
    }

    public String getOrderInvoiceZname() {
        return orderInvoiceZname;
    }

    public void setOrderInvoiceZname(String orderInvoiceZname) {
        this.orderInvoiceZname = orderInvoiceZname;
    }

    public String getOrderInvoiceZmobile() {
        return orderInvoiceZmobile;
    }

    public void setOrderInvoiceZmobile(String orderInvoiceZmobile) {
        this.orderInvoiceZmobile = orderInvoiceZmobile;
    }

    public Long getOrderInvoiceZareaId() {
        return orderInvoiceZareaId;
    }

    public void setOrderInvoiceZareaId(Long orderInvoiceZareaId) {
        this.orderInvoiceZareaId = orderInvoiceZareaId;
    }

    public String getOrderInvoiceZaddress() {
        return orderInvoiceZaddress;
    }

    public void setOrderInvoiceZaddress(String orderInvoiceZaddress) {
        this.orderInvoiceZaddress = orderInvoiceZaddress;
    }
}