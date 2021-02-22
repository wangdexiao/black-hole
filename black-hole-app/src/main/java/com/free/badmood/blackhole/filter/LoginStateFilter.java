package com.free.badmood.blackhole.filter;

import com.free.badmood.blackhole.context.LoginStateContext;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "loginStateFilter",urlPatterns = {"/getUserInfo","/logout"})
public class LoginStateFilter implements Filter {

    @Autowired
    private LoginStateContext loginStateContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String openid = ((HttpServletRequest) request).getHeader("openid");
        if(loginStateContext.existLoginState(openid)){
            chain.doFilter(request,response);
        }else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json; charset=UTF-8");
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter writer = httpResponse.getWriter();
            writer.println(Result.unAuth2code(null).toString());
            writer.flush();
            writer.close();
        }
    }

}
