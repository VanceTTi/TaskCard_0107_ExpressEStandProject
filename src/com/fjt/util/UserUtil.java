package com.fjt.util;

import javax.servlet.http.HttpSession;

public class UserUtil {
    /**
     * 获取用户姓名
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
       return "18888888888";
    }
}
