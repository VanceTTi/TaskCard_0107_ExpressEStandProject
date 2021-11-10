package com.fjt.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 这个实体类专门为主页面显示日期时间而新增的
 *
 * 将时间改成String，可以直接查到之后就直接就是String类型进行封装，然后显示在主页面上
 */
public class BootStrapTableExpress {
    private int id;             //id
    private String number;      //快递单号
    private String userName;    //收件人姓名
    private String userPhone;   //收件人电话
    private String company;     //快递公司
    private String code;        //取件码
    private String inTime;   //入库时间
    private String outTime;  //出库时间
    private String status;         //快递的状态
    private String sysPhone;    //录入快递的录入人手机号码

    public BootStrapTableExpress(int id, String number, String userPhone, String company, String code, String inTime, String outTime, String sysPhone) {
    }

    public BootStrapTableExpress(int id, String number, String userName, String userPhone, String company, String code, String inTime, String outTime, String status, String sysPhone) {
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

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        return "BootStrapTableExpress{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", company='" + company + '\'' +
                ", code='" + code + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", status='" + status + '\'' +
                ", sysPhone='" + sysPhone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BootStrapTableExpress that = (BootStrapTableExpress) o;
        return id == that.id && Objects.equals(number, that.number) && Objects.equals(userName, that.userName) && Objects.equals(userPhone, that.userPhone) && Objects.equals(company, that.company) && Objects.equals(code, that.code) && Objects.equals(inTime, that.inTime) && Objects.equals(outTime, that.outTime) && Objects.equals(status, that.status) && Objects.equals(sysPhone, that.sysPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, userName, userPhone, company, code, inTime, outTime, status, sysPhone);
    }
}
