package com.fjt.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter("*.do")
public class CharSetFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //处理编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        chain.doFilter(request,response);
    }
}
