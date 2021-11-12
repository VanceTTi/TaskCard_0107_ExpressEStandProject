package com.fjt.bean;

import java.sql.Timestamp;

/**
 * 用户
 */
public class User {
    private int uid;//编号
    private String nickname;//昵称
    private String uPhone;//手机号码
    private String uPassword;//密码
    private Timestamp registrationDate;//注册时间
    private Timestamp LastLoginTime;//上次登录时间


    public User() {
    }

    public User(String nickname, String uPhone, String uPassword) {
        this.nickname = nickname;
        this.uPhone = uPhone;
        this.uPassword = uPassword;
    }

    public User(int uid, String nickname, String uPassword, Timestamp registrationDate, Timestamp lastLoginTime) {
        this.uid = uid;
        this.nickname = nickname;
        this.uPassword = uPassword;
        this.registrationDate = registrationDate;
        LastLoginTime = lastLoginTime;
    }

    public User(int uid, String nickname, String uPhone, String uPassword, Timestamp registrationDate, Timestamp lastLoginTime) {
        this.uid = uid;
        this.nickname = nickname;
        this.uPhone = uPhone;
        this.uPassword = uPassword;
        this.registrationDate = registrationDate;
        LastLoginTime = lastLoginTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Timestamp getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }
}
