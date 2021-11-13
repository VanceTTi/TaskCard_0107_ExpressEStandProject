package com.fjt.wx.controller;

import com.fjt.bean.Courier;
import com.fjt.bean.Message;
import com.fjt.bean.User;
import com.fjt.mvc.ResponseBody;
import com.fjt.service.CourierService;
import com.fjt.util.JSONUtil;
import com.fjt.util.RandomUtil;
import com.fjt.util.SMSUtil;
import com.fjt.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {


    /**
     * 登录时的短信发送
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/loginSms.do")
    public String sendSms(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        System.out.println("loginSms:"+userPhone);
        //使用工具类随机生成6位随机数
        String code = RandomUtil.getCode() + "";//使用+""拼接可以变成String类型
        boolean flag = SMSUtil.loginSMS(userPhone, code);
        System.out.println("loginSms："+flag);
        Message msg = new Message();
        if (flag) {
            //短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送,请查收!");
        } else {
            //短信发送失败
            msg.setStatus(1);
            msg.setResult("验证码发送失败");
        }
        UserUtil.setLoginSms(request.getSession(),userPhone,code);//在session中存储手机号和验证码
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    /**
     * 用户登录的响应
     * 用户登录有短信发送
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request,HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        String userCode = request.getParameter("code");//userCode是用户输入的code
        String sysCode = UserUtil.getLoginSms(request.getSession(), userPhone);//sysCode是系统session保存的code，验证码的code

        Message msg = new Message();
        if (sysCode == null){
            //如果这个sysCode是空的，这个手机号就没有获取到短信
            msg.setStatus(-1);
            msg.setResult("手机号码未获取到短信");
        }else if (sysCode.equals(userCode)) {
            //手机号码一致，输入验证码与验证码一致
            //登录成功
            // 查询快递员表格判断是否存在，不存在就是用户登录
            Courier byPhone = CourierService.findByPhone(userPhone);//查询快递员表格
            User user = new User();
            if (byPhone != null) {//如果可以查到那就是快递员，否则就是普通用户
                //快递员
                msg.setStatus(1);
                user.setUser(false);//非用户
            } else {
                //用户
                msg.setStatus(0);//只要是0就直接跳转页面
                user.setUser(true);//是用户
            }
            user.setuPhone(userPhone);
            UserUtil.setWXUser(request.getSession(),user);
        }else {
            //手机号码一致，输入验证码与验证码不一致
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 专门用来获取用户身份信息
     * 登录之后才获取
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request,HttpServletResponse response) {
        User user = UserUtil.getWxUser(request.getSession());
        boolean isUser = user.isUser();
        Message msg = new Message();
        if (isUser){//
            msg.setStatus(0);
        } else {
            msg.setStatus(1);
        }
        msg.setResult(user.getuPhone());
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    /**
     * 退出的响应
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        //1.  销毁session
        request.getSession().invalidate();
        //2.  给客户端回复消息
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }




}
