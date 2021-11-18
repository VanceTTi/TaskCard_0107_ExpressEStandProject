package com.fjt.wx.controller;

import com.fjt.bean.BootStrapTableExpress;
import com.fjt.bean.Express;
import com.fjt.bean.Message;
import com.fjt.bean.User;
import com.fjt.mvc.ResponseBody;
import com.fjt.service.ExpressService;
import com.fjt.util.DateFormatUtil;
import com.fjt.util.JSONUtil;
import com.fjt.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpressController {
    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest request, HttpServletResponse response){
        User wxUser = UserUtil.getWxUser(request.getSession());//用户登录后就能获取到用户的对象
        String userPhone = wxUser.getuPhone();//获取用户的手机号码
        List<Express> list = ExpressService.findByUserPhone(userPhone);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        //将list转换成list2这样格式的集合。list2中的时间是String类型
        for (Express e:list){
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUserName(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }

        Message msg = new Message();
        if (list.size() == 0) {//如果没有查找到数据
            msg.setStatus(-1);//标记为-1
        }else {
            msg.setStatus(0);//如果查找有结果就标记为0
            /*
            java 8 新特性：Lambda 表达式
            对上面lsit进行筛选
            jdk 8 的新技术  stream流
            list.stream()获取流
            filter()  过滤里面的数据
            sorted  排序
             */
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("待取件")) {//如果没有取件
                    return true;//返回true
                } else {
                    return false;
                }
            }).sorted((o1,o2) -> {//排序，
                long o1time = DateFormatUtil.toTime(o1.getInTime());
                long o2time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1time - o2time);//然后根据时间进行排序
            });


            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("已取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1,o2) -> {
                long o1time = DateFormatUtil.toTime(o1.getInTime());
                long o2time = DateFormatUtil.toTime(o2.getInTime());
                return (int) (o1time - o2time);
            });
            //toArray():集合转数组的方法。
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            //响应前台的数据传输
            Map data = new HashMap();
            data.put("status0",s0);
            data.put("status1",s1);
            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg.getData());
        return json;
    }

    /**
     * 这个方法是用户查询没有收货的快递，当用户每次点击确认查询的时候，
     * 都是要重新发送请求重新查询
     * @param request
     * @param response
     * @return
     * 对应页面：pickExpress.html
     */
    @ResponseBody("wx/userExpressList.do")
    public String expressList(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        //查询数据
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);//直接设置为0表示查询的是未取件
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        //将list转换成list2这样格式的集合。list2中的时间是String类型
        for (Express e:list){
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUserName(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();//发送信息给前端。
        if (list.size() == 0) {
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(list2);
        }
        return JSONUtil.toJSON(msg);
    }



    /**
     * 添加快递页面
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        //1.获取前端数据
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        System.out.println(number);
        System.out.println(company);
        System.out.println(username);
        System.out.println(userPhone);


        Express e = new Express(number,username,userPhone, company,UserUtil.getUserPhone(request.getSession()));
        boolean flag = ExpressService.insert(e);

        //下面的数据如：msg等   都会给到add.html页面中datafunction (data){});中的data
        Message msg = new Message();
        if (flag) {
            //录入成功
            msg.setStatus(0);//成功就是0
            msg.setResult("快递录入成功！");
        } else {
            //录入失败
            msg.setStatus(-1);//失败就是-1
            msg.setResult("快递录入失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

}
