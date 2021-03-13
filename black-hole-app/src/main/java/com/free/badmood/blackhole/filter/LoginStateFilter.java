package com.free.badmood.blackhole.filter;

import com.free.badmood.blackhole.context.UserInfoContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "loginStateFilter",urlPatterns = {"/userinfo","/logout","/article/add"})
public class LoginStateFilter implements Filter {

    @Autowired
    private UserInfoContext userInfoContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String openid = ((HttpServletRequest) request).getHeader("openid");
//        if(loginStateContext.existUserInfo(openid)){
//            OpenIdContext.OPENID.set(openid);
//            chain.doFilter(request,response);
//        }else {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setContentType("application/json; charset=UTF-8");
//            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//            PrintWriter writer = httpResponse.getWriter();
//            writer.println(Result.unAuth2code(null).toString());
//            writer.flush();
//            writer.close();
//        }
    }

}
