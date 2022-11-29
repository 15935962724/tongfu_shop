package com.tongfu.entity;

public class ReturnsItem {
    private String sn;

    private String name;

    private Integer quantity;

    private Long returns;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getReturns() {
        return returns;
    }

    public void setReturns(Long returns) {
        this.returns = returns;
    }
}