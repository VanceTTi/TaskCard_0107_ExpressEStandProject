package com.fjt.util;

/**
 * 后台输出短信内容
 */
public class SMSUtil {

    public static boolean send(String phoneNumber,String code) {
        System.out.println("[快递e栈]"+phoneNumber+"您的验证码："+ code +",该验证码长期有效，请勿泄露于他人！");
        return true;
    }

    /**
     * 登录验证，的短信发送模拟
     * @param phoneNumber 手机号码
     * @param code 验证码
     * @return
     */
    public static boolean loginSMS(String phoneNumber,String code){
        System.out.println("[快递e栈]提醒您，"+phoneNumber+"的验证码为："+ code +",您正在登录，若非本人操作，请勿泄露.");
        return true;
    }


}
