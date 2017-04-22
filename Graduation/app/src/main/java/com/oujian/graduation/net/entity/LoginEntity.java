package com.oujian.graduation.net.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yi on 2016/12/12.
 */

public class LoginEntity {

    private String id;

    private String custName;

    private String custNick;

    private String custAccount;

    private String custPassword;

    private BigDecimal custBalance;

    private String custLinkman;

    private String custLinktel;

    private String custEmail;

    private BigDecimal apiDiscount;

    private String custLogo;

    private String publicKey;

    private String privateKey;

    private BigDecimal minBalance;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustNick() {
        return custNick;
    }

    public void setCustNick(String custNick) {
        this.custNick = custNick;
    }

    public String getCustAccount() {
        return custAccount;
    }

    public void setCustAccount(String custAccount) {
        this.custAccount = custAccount;
    }

    public String getCustPassword() {
        return custPassword;
    }

    public void setCustPassword(String custPassword) {
        this.custPassword = custPassword;
    }

    public BigDecimal getCustBalance() {
        return custBalance;
    }

    public void setCustBalance(BigDecimal custBalance) {
        this.custBalance = custBalance;
    }

    public String getCustLinkman() {
        return custLinkman;
    }

    public void setCustLinkman(String custLinkman) {
        this.custLinkman = custLinkman;
    }

    public String getCustLinktel() {
        return custLinktel;
    }

    public void setCustLinktel(String custLinktel) {
        this.custLinktel = custLinktel;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public BigDecimal getApiDiscount() {
        return apiDiscount;
    }

    public void setApiDiscount(BigDecimal apiDiscount) {
        this.apiDiscount = apiDiscount;
    }

    public String getCustLogo() {
        return custLogo;
    }

    public void setCustLogo(String custLogo) {
        this.custLogo = custLogo;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
