package com.fjt.controller;

import com.fjt.bean.*;
import com.fjt.mvc.ResponseBody;
import com.fjt.service.CourierService;
import com.fjt.service.ExpressService;
import com.fjt.util.DateFormatUtil;
import com.fjt.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CourierController {
    /**
     * 快递员列表信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/list.do")
    public String CourierList (HttpServletRequest request, HttpServletResponse response) {
        //1.接收数据
        //1.1接收查询数据起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //1.2接收当前页需邀查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//一页显示多少个
        //2.调用方法
        //2.1调用分页查询的方法
        List<Courier> courierAllList = CourierService.findCourierAll(true, offset, pageNumber);

        /**
         * 测试
         */
        System.out.println(courierAllList);

        List<BootStrapTableCourier> courierAllList2 = new ArrayList<>();
        //将时间转换
        for (Courier c : courierAllList){
            String registrationDate = DateFormatUtil.format(c.getRegistrationDate());
            String lastLoginTime = DateFormatUtil.format(c.getLastLoginTime());
            BootStrapTableCourier c2 = new BootStrapTableCourier(c.getCourierId(),c.getCourierName(),c.getCourierPhone(),c.getCourierIdCard(),c.getCourierPassword(),c.getSendQty(),registrationDate,lastLoginTime);
            courierAllList2.add(c2);
        }

        /**
         * 测试
         */
        System.out.println("courierAllList2"+courierAllList2);

        //查询总条数
        Integer total = CourierService.total();

        System.out.println("total"+total);

        //3.将集合封装为 Bootstrap-table识别的格式
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(courierAllList2);
        data.setTotal(total);
        //4.将data转换成json返回给客户端
        String json = JSONUtil.toJSON(data);
        return json;
    }

    /**
     * 快递员信息录入
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/insert.do")
    public String CourierInsert (HttpServletRequest request, HttpServletResponse response) {
        //1.从前端获取数据
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String idCard = request.getParameter("idCard");
        String password = request.getParameter("password");
        //2.调用到方法
        Courier courier = new Courier(name,phone,idCard,password);
        boolean flag = CourierService.courierInsert(courier);
        //下面的数据如：msg等   都会给到add.html页面中datafunction (data){});中的data
        Message msg = new Message();
        if (flag) {
            //录入成功
            msg.setStatus(0);//成功就是0
            msg.setResult("快递员信息录入成功！");
        } else {
            //录入失败
            msg.setStatus(-1);//失败就是-1
            msg.setResult("快递员信息录入失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 根据手机号查询快递员
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        //1.接收参数
        String phone = request.getParameter("phone");
        //System.out.println(phone);
        //2.调用方法
        Courier byPhone = CourierService.findByPhone(phone);
        //System.out.println(byPhone);
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
     * 通过id来修改快递员信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/update.do")
    public String upDate(HttpServletRequest request, HttpServletResponse response) {
        //1.接收数据
        int courierId = Integer.parseInt(request.getParameter("courierId"));
        String courierName = request.getParameter("courierName");
        String courierPhone = request.getParameter("courierPhone");
        String courierIdCard = request.getParameter("courierIdCard");
        String courierPassword = request.getParameter("courierPassword");

        //2.调用方法
        //封装数据
        Courier newCourier = new Courier();
        newCourier.setCourierName(courierName);
        newCourier.setCourierPhone(courierPhone);
        newCourier.setCourierIdCard(courierIdCard);
        newCourier.setCourierPassword(courierPassword);

        boolean flag = CourierService.update(courierId, newCourier);
        System.out.println("修改flag："+flag);
        //消息
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
     * 根据id来删除快递员信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        //1.接收数据
        int courierId = Integer.parseInt(request.getParameter("courierId"));
        //2.调用方法
        boolean flag = CourierService.delete(courierId);//调用方法
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
