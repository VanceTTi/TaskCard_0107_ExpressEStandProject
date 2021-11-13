package com.fjt.controller;

import com.fjt.bean.*;
import com.fjt.mvc.ResponseBody;

import com.fjt.service.UserService;
import com.fjt.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    @ResponseBody("/user/list.do")
    public String userList(HttpServletRequest request, HttpServletResponse response){
        //1.接收数据
        int offset = Integer.parseInt(request.getParameter("offset"));//第几个开始查
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//要查多少个
        //2.调用方法
        List<User> userList1 = UserService.findUserAll(true, offset, pageNumber);
        List<BootStrapTableUser> userList2 = new ArrayList<>();
        for (User u: userList1) {
            String registrationDate = DateFormatUtil.format(u.getRegistrationDate());
            String lastLoginTime = DateFormatUtil.format(u.getLastLoginTime());
            BootStrapTableUser u2 = new BootStrapTableUser(u.getUid(),u.getNickname(),u.getuPhone(),u.getuPassword(),registrationDate,lastLoginTime);
            userList2.add(u2);
        }
        System.out.println(userList2);
        Integer total = UserService.total();
        //3.将集合封装为 Bootstrap-table识别的格式
        ResultData<BootStrapTableUser> data = new ResultData<>();
        data.setRows(userList2);
        data.setTotal(total);
        //4.将data转换成json返回给客户端
        String json = JSONUtil.toJSON(data);
        return json;
    }

    /**
     * 用户信息录入
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/insert.do")
    public String UserInsert (HttpServletRequest request, HttpServletResponse response) {
        //1.从前端获取数据
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        //2.调用到方法
        User user = new User(name,phone,password);

        boolean flag = UserService.userInsert(user);
        //下面的数据如：msg等   都会给到add.html页面中datafunction (data){});中的data
        Message msg = new Message();
        if (flag) {
            //录入成功
            msg.setStatus(0);//成功就是0
            msg.setResult("用户信息录入成功！");
        } else {
            //录入失败
            msg.setStatus(-1);//失败就是-1
            msg.setResult("用户信息录入失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 根据手机号查询用户
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        //1.接收参数
        String phone = request.getParameter("phone");
        //2.调用方法
        User byPhone = UserService.findByPhone(phone);

        Message msg = new Message();
        if (byPhone == null) {//如果没有找到e就是空的
            msg.setStatus(-1);
            msg.setResult("手机号码不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(byPhone);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 通过id来修改用户
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/update.do")
    public String upDate(HttpServletRequest request, HttpServletResponse response) {
        //1.接收数据
        //int userId = Integer.parseInt(request.getParameter("userId"));
        String userId = request.getParameter("userId");
        System.out.println(userId);
        String nickname = request.getParameter("nickname");
        String uPhone = request.getParameter("uPhone");
        String uPassword = request.getParameter("uPassword");

        System.out.println(userId);
        System.out.println(nickname);
        System.out.println(uPhone);
        System.out.println(uPassword);


        //2.调用方法
        User newUser = new User();
        newUser.setNickname(nickname);
        newUser.setuPhone(uPhone);
        newUser.setuPassword(uPassword);
        boolean flag = UserService.update(Integer.parseInt(userId), newUser);

        System.out.println(flag);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);//修改成功
            msg.setResult("修改成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 根据id来删除用户信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        //1.接收数据
        int uid = Integer.parseInt(request.getParameter("userId"));
        //2.调用方法
        boolean flag = UserService.delete(uid);//调用方法
        //3.给前端发送消息，数据
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("删除成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }



}
