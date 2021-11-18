package com.fjt.controller;

import com.fjt.bean.BootStrapTableExpress;
import com.fjt.bean.Express;
import com.fjt.bean.Message;
import com.fjt.bean.ResultData;
import com.fjt.mvc.ResponseBody;
import com.fjt.service.ExpressService;
import com.fjt.util.DateFormatUtil;
import com.fjt.util.JSONUtil;
import com.fjt.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressController {

    /**
     * 主页面
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        //调用Service层的console()方法
        List<Map<String, Integer>> data = ExpressService.console();
        Message msg = new Message();//创建Message对象，作用看bean类
        if (data.size() == 0) {
            //如果查询失败,则给-1
            msg.setStatus(-1);
        } else {
            //如果console()查询成功
            msg.setStatus(0);
        }
        msg.setData(data);//携带的一组数据
        String json = JSONUtil.toJSON(msg);//得到json数据
        return json;//将json返回出去，mvc会响应给前端
    }
    @ResponseBody("/express/consoleCourier.do")
    public String consoleCourier(HttpServletRequest request, HttpServletResponse response){
        //调用Service层的console()方法
        List<Map<String, Integer>> data = ExpressService.consoleCourier();
        Message msg = new Message();//创建Message对象，作用看bean类
        if (data.size() == 0) {
            //如果查询失败,则给-1
            msg.setStatus(-1);
        } else {
            //如果console()查询成功
            msg.setStatus(0);
        }
        msg.setData(data);//携带的一组数据
        String json = JSONUtil.toJSON(msg);//得到json数据
        return json;//将json返回出去，mvc会响应给前端
    }
    @ResponseBody("/express/consoleUser.do")
    public String consoleUser(HttpServletRequest request, HttpServletResponse response){
        //调用Service层的console()方法
        List<Map<String, Integer>> data = ExpressService.consoleUser();
        Message msg = new Message();//创建Message对象，作用看bean类
        if (data.size() == 0) {
            //如果查询失败,则给-1
            msg.setStatus(-1);
        } else {
            //如果console()查询成功
            msg.setStatus(0);
        }
        msg.setData(data);//携带的一组数据
        String json = JSONUtil.toJSON(msg);//得到json数据
        return json;//将json返回出去，mvc会响应给前端
    }

    /**
     * 快递的  列表，分页等等
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response){
        //1.  获取查询数据起始索引值(从第几个开始开始查询数据)（每一页显示的数据都是要重新查询，所以需要前端发送请求）
        int offset = Integer.parseInt(request.getParameter("offset"));//偏移值(就是多少页)
        //2.  获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));//一页显示多少个
        //3.  进行分页查询
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
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
        List<Map<String, Integer>> console = ExpressService.console();//console()用于查询数据库中的全部快递(总数+新增)，待取件快递（总数+新增）
        //get(0)就是ArrayList中指定0下标的元素，data1_size就是快递总数(key)
        Integer total = console.get(0).get("data1_size");
        //4.  将集合封装为 Bootstrap-table识别的格式
                //这个data里面一个是数据的集合，一个是数据的总量
        ResultData<BootStrapTableExpress> data = new ResultData<>();//分页实体类对象
        data.setRows(list2);
        data.setTotal(total);
        //将data转换成json返回给客户端
        String json = JSONUtil.toJSON(data);
        return json;
    }

    /**
     * 添加快递页面
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        //1.获取前端数据
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");

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


    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response){
        //1.接收参数
        String number = request.getParameter("number");
        //2.调用方法
        Express e = ExpressService.findByNumber(number);

        Message msg = new Message();//创建消息
        if (e == null) {//如果没有找到e就是空的
            msg.setStatus(-1);
            msg.setResult("单号不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @ResponseBody("/express/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        //1.接收数据
        int id = Integer.parseInt(request.getParameter("id"));
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        int status = Integer.parseInt(request.getParameter("status"));
        Express newExpress = new Express();
        newExpress.setNumber(number);
        newExpress.setCompany(company);
        newExpress.setUserName(username);
        newExpress.setUserPhone(userPhone);
        newExpress.setStatus(status);
        //2.调用方法
        boolean flag = ExpressService.update(id, newExpress);
        //3.消息
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @ResponseBody("/express/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        //1.接收数据
        int id = Integer.parseInt(request.getParameter("id"));
        //2.调用方法
        boolean flag = ExpressService.delete(id);
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
