package com.free.badmood.blackhole.filter;

import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.context.OpenIdContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 判断是否认证拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInfoContext userInfoContext;

    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
     * return true 继续往下走 否则false
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //需不需要认证的接口都读取设置下openid
        String openid = ((HttpServletRequest) request).getHeader("openid");
        OpenIdContext.OPENID.set(openid);

        boolean needAuthentication;
        if (handler != null) {
            if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                HandlerMethod handlerMethod = ((HandlerMethod) handler);
                needAuthentication = handlerMethod.getMethod().isAnnotationPresent(RequireAuthentication.class);

                if (!needAuthentication){
                    Class clazz = handlerMethod.getMethod().getDeclaringClass();
                    needAuthentication = clazz.isAnnotationPresent(RequireAuthentication.class);
                    if(needAuthentication){
                        return hasAuthentication(request, response);
                    }
                }else {
                    return hasAuthentication(request, response);
                }


            }
        }
        //默认继续往下走
        return true;
    }

    /**
     * 判断是否已经认证过了
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private boolean hasAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String openid = ((HttpServletRequest) request).getHeader("openid");
        if(userInfoContext.existUserInfo(openid)){
            OpenIdContext.OPENID.set(openid);
            return true;
        }else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json; charset=UTF-8");
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter writer = httpResponse.getWriter();
            writer.println(Result.unAuth2code(null).toString());
            writer.flush();
            writer.close();
            return false;
        }
    }

    /**
     * 在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），
     * 有机会修改ModelAndView （不怎么用）；
     */

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
