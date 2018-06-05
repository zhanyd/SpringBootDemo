package com.zhanyd.biz.model;

import java.util.Date;

public class Users {
    private Integer id;

    private String username;

    private String password;

    private Boolean enabled;

    private String memo;

    private Date lastLonginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getLastLonginTime() {
        return lastLonginTime;
    }

    public void setLastLonginTime(Date lastLonginTime) {
        this.lastLonginTime = lastLonginTime;
    }
}