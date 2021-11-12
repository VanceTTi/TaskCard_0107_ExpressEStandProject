package com.fjt.bean;



public class BootStrapTableUser {
    private int uid;//编号
    private String nickname;//昵称
    private String uPhone;//手机号码
    private String uPassword;//密码
    private String registrationDate;//注册时间
    private String LastLoginTime;//上次登录时间

    public BootStrapTableUser() {
    }

    public BootStrapTableUser(int uid, String nickname, String uPhone, String uPassword, String registrationDate, String lastLoginTime) {
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "BootStrapTableUser{" +
                "uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", LastLoginTime='" + LastLoginTime + '\'' +
                '}';
    }
}
