package com.fjt.controller;

import com.fjt.bean.Message;
import com.fjt.mvc.ResponseBody;
import com.fjt.service.AdminService;
import com.fjt.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AdminController {
    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        //1.接收参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2. 调用service传参数，并获取结果
            //返回true或者false
        boolean result = AdminService.login(username, password);
        //3. 根据结果，准备不同的返回数据
        Message msg = null;//消息对象

        if (result) {
            msg = new Message(0,"登录成功");//{status:0,result:"登录成功"}
            //登录时间 和 ip更新
            Date date = new Date();//获取当前时间
            String ip = request.getRemoteAddr();//获取远程ip，但是如果是本机登录那就是本机ip0:0:0:0
            //调用方法去更新信息
            AdminService.updateLoginTimeAndIP(username,date,ip);

            //将username存储到Session中
            request.getSession().setAttribute("adminUserName","username");

        }else {
            msg = new Message(-1,"登录失败");//{status:-1,result:"登录失败"}
        }
        //4.将数据转换为JSON
        String json = JSONUtil.toJSON(msg);
        //5. 将JSON回复给ajax
        return json;
    }
}
