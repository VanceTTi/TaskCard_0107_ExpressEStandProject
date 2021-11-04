package com.fjt.filter;

import com.fjt.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/admin/index.html","/amdin/views/*","/express/*"})
public class AccessControlFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //从session中获取username
        String userName = UserUtil.getUserName(request.getSession());
        if (userName != null) {//如果session不是空的正常执行程序，否则报错
            chain.doFilter(req,resp);
        } else {
            response.sendError(404,"很遗憾，权限不足");
        }
        chain.doFilter(req, resp);
    }
}
