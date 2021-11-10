package com.fjt.bean;


public class BootStrapTableCourier {
    private int courierId;//编号
    private String courierName;//姓名
    private String courierPhone;//手机号码
    private String courierIdCard;//身份证
    private String courierPassword;//密码
    private String sendQty;//派件数
    private String registrationDate;//registration Date 注册日期
    private String LastLoginTime;//上次登录时间

    public BootStrapTableCourier() {
    }

    public BootStrapTableCourier(int courierId, String courierName, String courierPhone, String courierIdCard, String courierPassword, String sendQty, String registrationDate, String lastLoginTime) {
        this.courierId = courierId;
        this.courierName = courierName;
        this.courierPhone = courierPhone;
        this.courierIdCard = courierIdCard;
        this.courierPassword = courierPassword;
        this.sendQty = sendQty;
        this.registrationDate = registrationDate;
        LastLoginTime = lastLoginTime;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public String getCourierIdCard() {
        return courierIdCard;
    }

    public void setCourierIdCard(String courierIdCard) {
        this.courierIdCard = courierIdCard;
    }

    public String getCourierPassword() {
        return courierPassword;
    }

    public void setCourierPassword(String courierPassword) {
        this.courierPassword = courierPassword;
    }

    public String getSendQty() {
        return sendQty;
    }

    public void setSendQty(String sendQty) {
        this.sendQty = sendQty;
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
        return "BootStrapTableCourier{" +
                "courierId=" + courierId +
                ", courierName='" + courierName + '\'' +
                ", courierPhone='" + courierPhone + '\'' +
                ", courierIdCard='" + courierIdCard + '\'' +
                ", courierPassword='" + courierPassword + '\'' +
                ", sendQty='" + sendQty + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", LastLoginTime='" + LastLoginTime + '\'' +
                '}';
    }
}
