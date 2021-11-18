package com.fjt.util;

import com.fjt.bean.User;

import javax.servlet.http.HttpSession;

public class UserUtil {
    /**
     * 获取用户姓名,存放到session中
     * @param session
     * @return
     */
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }

    /**
     * 获取录入人电话
     * @param session
     * @return
     */
    public static String getUserPhone(HttpSession session){
        //todo的意思就是要去做的事
        //TODO:还没有编写快递柜子端，未存储任何录入人信息
       //return "18888888888";
        return (String) session.getAttribute("userPhone");
    }

    /**
     * 将电话号码存入 session中,方便验证
     * @param session
     * @param userPhone 传入的用户电话号码
     * @return
     */
    public static String getLoginSms(HttpSession session,String userPhone) {
        session.setAttribute("userPhone",userPhone);
        return (String) session.getAttribute(userPhone);
    }

    /**
     * 能够修改对应电话号码中session中的code值
     * 就是多次发送的话，保留新的值
     * @param session
     * @param userPhone
     * @param code
     */
    public static void setLoginSms(HttpSession session,String userPhone,String code) {
        session.setAttribute(userPhone,code);//key,value
    }

    /**
     * 用来存储，登录的user对象
     * @param session
     * @param user
     */
    public static void setWXUser(HttpSession session, User user) {
        session.setAttribute("wxUser",user);
    }

    /**
     * 获取存储在session的user对象
     * @param session
     * @return
     */
    public static User getWxUser(HttpSession session){
        return (User) session.getAttribute("wxUser");
    }



}
