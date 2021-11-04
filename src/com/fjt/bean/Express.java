package com.fjt.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 实体类
 */
public class Express {
    private int id;             //id
    private String number;      //快递单号
    private String userName;    //收件人姓名
    private String userPhone;   //收件人电话
    private String company;     //快递公司
    private String code;        //取件码
    private Timestamp inTime;   //入库时间
    private Timestamp outTime;  //出库时间
    private int status;         //快递的状态
    private String sysPhone;    //录入快递的录入人手机号码
    public Express() {
    }
    /**
     * 新增快递时需要那些属性
     * id 为自动生成
     * number 快递单号 由管理员输入    √
     * userName 收件人姓名 由管理员输入  √
     * userPhone 收件人电话 由管理员输入  √
     * company 快递公司 是选择的   √
     * code  取件码是系统产生的，也是要插入的  √
     * inTime 入库时间是 根据状态的改变而变
     * outTime 出库时间是 根据状态的改变而变
     * status 状态 默认为0表示入库，
     * sysPhone 录入快递的快递人的手机号，通过当前登录用户来获取  √
     */
    public Express(String number, String userName, String userPhone, String company, String sysPhone,String code) {
        this.number = number;
        this.userName = userName;
        this.userPhone = userPhone;
        this.company = company;
        this.sysPhone = sysPhone;
        this.code = code;
    }

    public Express(String number, String userName, String userPhone, String company, String sysPhone) {
        this.number = number;
        this.userName = userName;
        this.userPhone = userPhone;
        this.company = company;
        this.sysPhone = sysPhone;
    }

    public Express(int id, String number, String userName, String userPhone, String company, String code, Timestamp inTime, Timestamp outTime, int status, String sysPhone) {
        this.id = id;
        this.number = number;
        this.userName = userName;
        this.userPhone = userPhone;
        this.company = company;
        this.code = code;
        this.inTime = inTime;
        this.outTime = outTime;
        this.status = status;
        this.sysPhone = sysPhone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getInTime() {
        return inTime;
    }

    public void setInTime(Timestamp inTime) {
        this.inTime = inTime;
    }

    public Timestamp getOutTime() {
        return outTime;
    }

    public void setOutTime(Timestamp outTime) {
        this.outTime = outTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSysPhone() {
        return sysPhone;
    }

    public void setSysPhone(String sysPhone) {
        this.sysPhone = sysPhone;
    }

    @Override
    public String toString() {
        return "Express{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", company='" + company + '\'' +
                ", code='" + code + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", status=" + status +
                ", sysPhone='" + sysPhone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Express express = (Express) o;
        return id == express.id && status == express.status && Objects.equals(number, express.number) && Objects.equals(userName, express.userName) && Objects.equals(userPhone, express.userPhone) && Objects.equals(company, express.company) && Objects.equals(code, express.code) && Objects.equals(inTime, express.inTime) && Objects.equals(outTime, express.outTime) && Objects.equals(sysPhone, express.sysPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, userName, userPhone, company, code, inTime, outTime, status, sysPhone);
    }
}
