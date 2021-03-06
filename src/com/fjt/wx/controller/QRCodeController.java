package com.fjt.wx.controller;

import com.fjt.bean.BootStrapTableExpress;
import com.fjt.bean.Express;
import com.fjt.bean.Message;
import com.fjt.bean.User;
import com.fjt.mvc.ResponseBody;
import com.fjt.mvc.ResponseView;
import com.fjt.service.ExpressService;
import com.fjt.util.DateFormatUtil;
import com.fjt.util.JSONUtil;
import com.fjt.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 微信端：二维码展示
 */
public class QRCodeController {
    /**
     * 这个方法是 二维码展示，可以是index主页面的二维码
     * 也可以是expressList中快递列表中的二维码
     * ResponseView的作用就是直接跳转到某一个地址
     * @param request
     * @param response
     * @return
     */
    @ResponseView("/wx/createQRCode.do")
    public String createQrcode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        //type的值通过expressList.html点进来就是express,通过index.html点进来的就是user
        String type = request.getParameter("type");

        String userPhone = null;
        String qRCodeContent = null;
        if ("express".equals(type)){
            //快递二维码：快递的二维码被扫后，展示单个快递信息。
            //传递code查询单个快递
            qRCodeContent = "express_"+code;
        } else {
            //用户二维码：用户的二维码被扫后，快递员（柜子）展示用户所有的快递信息
            //userPhone查询该手机号的所有快递
            User wxUser = UserUtil.getWxUser(request.getSession());
            userPhone = wxUser.getuPhone();
            qRCodeContent = "userPhone"+userPhone;
        }
        HttpSession session = request.getSession();
        session.setAttribute("qrcode",qRCodeContent);

        return "/personQRcode.html";

    }

    /**
     *
     * 发送二维码信息给前端
     * createQrcode（）的方法跳转到personQRcode.html的页面后发送请求到这里获取信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        //上面的createQrcode（）在session中存储了qrcode
        String qrcode = (String) session.getAttribute("qrcode");
        Message msg = new Message();
        if (qrcode == null) {
            msg.setStatus(-1);
            msg.setResult("取件码获取出错，请用户重新操作");
        } else {
            msg.setStatus(0);
            msg.setResult(qrcode);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updataExpressStatus(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        boolean flag = ExpressService.updateStatus(code);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("取件成功");

        }else {
            msg.setStatus(-1);
            msg.setResult("取件码不存在，请用户更新二维码");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    /**
     * 第二种取件方式
     * @param request
     * @param response
     * @return
     *
     * pickExpress.html
     */
    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        Express e = ExpressService.findByCode(code);
        BootStrapTableExpress e2 = null;
        if (e != null) {
            e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUserName(), e.getUserPhone(), e.getCompany(), e.getCode(), DateFormatUtil.format(e.getInTime()), e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime()), e.getStatus() == 0 ? "待取件" : "已取件", e.getSysPhone());
        }
        Message msg = new Message();
        if (e == null){
            msg.setStatus(-1);
            msg.setResult("取件码不存在");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        return JSONUtil.toJSON(msg);
    }

}
