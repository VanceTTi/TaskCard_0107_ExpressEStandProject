package com.fjt.wx.filter;

import com.fjt.bean.User;
import com.fjt.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/index.html"})
public class UserFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)request).getSession();//获取session
        User wxUser = UserUtil.getWxUser(session);
        if (wxUser != null){
            chain.doFilter(request, response);
        }else {
            ((HttpServletResponse)response).sendRedirect("login.html");//跳转登录页面
        }


    }
}
