package com.tongfu.entity;

import java.util.Date;

public class MeetingApply {
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

    private String invitation;

    private Long traffic;

    private Long sailtype;

    private String name;

    private String phone;

    private String email;

    private String cardId;

    private String gostation;

    private String arrivestation;

    private Date gotime;

    private Date backtime;

    private Boolean isstay;

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

    public String getInvitation() {
        return invitation;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation == null ? null : invitation.trim();
    }

    public Long getTraffic() {
        return traffic;
    }

    public void setTraffic(Long traffic) {
        this.traffic = traffic;
    }

    public Long getSailtype() {
        return sailtype;
    }

    public void setSailtype(Long sailtype) {
        this.sailtype = sailtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getGostation() {
        return gostation;
    }

    public void setGostation(String gostation) {
        this.gostation = gostation == null ? null : gostation.trim();
    }

    public String getArrivestation() {
        return arrivestation;
    }

    public void setArrivestation(String arrivestation) {
        this.arrivestation = arrivestation == null ? null : arrivestation.trim();
    }

    public Date getGotime() {
        return gotime;
    }

    public void setGotime(Date gotime) {
        this.gotime = gotime;
    }

    public Date getBacktime() {
        return backtime;
    }

    public void setBacktime(Date backtime) {
        this.backtime = backtime;
    }

    public Boolean getIsstay() {
        return isstay;
    }

    public void setIsstay(Boolean isstay) {
        this.isstay = isstay;
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