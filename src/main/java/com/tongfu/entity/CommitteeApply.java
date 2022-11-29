package com.tongfu.entity;

import java.util.Date;

public class CommitteeApply {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Long member;

    private String website;

    private String meetname;

    private Date beginHoldDate;

    private Date endHoldDate;

    private Long area;

    private String address;

    private String contract;

    private Long amount;

    private Long status;

    private String message;

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

    public Long getMember() {
        return member;
    }

    public void setMember(Long member) {
        this.member = member;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getMeetname() {
        return meetname;
    }

    public void setMeetname(String meetname) {
        this.meetname = meetname == null ? null : meetname.trim();
    }

    public Date getBeginHoldDate() {
        return beginHoldDate;
    }

    public void setBeginHoldDate(Date beginHoldDate) {
        this.beginHoldDate = beginHoldDate;
    }

    public Date getEndHoldDate() {
        return endHoldDate;
    }

    public void setEndHoldDate(Date endHoldDate) {
        this.endHoldDate = endHoldDate;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract == null ? null : contract.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}