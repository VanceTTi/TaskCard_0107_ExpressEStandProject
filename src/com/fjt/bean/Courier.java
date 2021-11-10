package com.fjt.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 快递员的实体类
 */
public class Courier {
    private int courierId;//编号
    private String courierName;//姓名
    private String courierPhone;//手机号码
    private String courierIdCard;//身份证
    private String courierPassword;//密码
    private String sendQty;//派件数
    private Timestamp registrationDate;//registration Date 注册日期
    private Timestamp LastLoginTime;//上次登录时间

    //构造器
    public Courier() {
    }

    public Courier(String courierName, String courierPhone, String courierIdCard, String courierPassword) {
        this.courierName = courierName;
        this.courierPhone = courierPhone;
        this.courierIdCard = courierIdCard;
        this.courierPassword = courierPassword;
    }

    public Courier(int courierId, String courierName, String courierIdCard, String courierPassword, String sendQty, Timestamp registrationDate, Timestamp lastLoginTime) {
        this.courierId = courierId;
        this.courierName = courierName;
        this.courierIdCard = courierIdCard;
        this.courierPassword = courierPassword;
        this.sendQty = sendQty;
        this.registrationDate = registrationDate;
        LastLoginTime = lastLoginTime;
    }

    public Courier(int courierId, String courierName, String courierPhone, String courierIdCard, String courierPassword, String sendQty, Timestamp registrationDate, Timestamp lastLoginTime) {
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

    @Override
    public String toString() {
        return "Courier{" +
                "courierId=" + courierId +
                ", courierName='" + courierName + '\'' +
                ", courierPhone='" + courierPhone + '\'' +
                ", courierIdCard='" + courierIdCard + '\'' +
                ", courierPassword='" + courierPassword + '\'' +
                ", sendQty='" + sendQty + '\'' +
                ", registrationDate=" + registrationDate +
                ", LastLoginTime=" + LastLoginTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return courierId == courier.courierId && Objects.equals(courierName, courier.courierName) && Objects.equals(courierPhone, courier.courierPhone) && Objects.equals(courierIdCard, courier.courierIdCard) && Objects.equals(courierPassword, courier.courierPassword) && Objects.equals(sendQty, courier.sendQty) && Objects.equals(registrationDate, courier.registrationDate) && Objects.equals(LastLoginTime, courier.LastLoginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courierId, courierName, courierPhone, courierIdCard, courierPassword, sendQty, registrationDate, LastLoginTime);
    }
}
