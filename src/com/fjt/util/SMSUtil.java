package com.fjt.util;

/**
 * 后台输出短信内容
 */
public class SMSUtil {

    public static boolean send(String phoneNumber,String code) {
        System.out.println("[快递e栈]"+phoneNumber+"您的验证码："+ code +",该验证码长期有效，请勿泄露于他人！");
        return true;
    }

}
