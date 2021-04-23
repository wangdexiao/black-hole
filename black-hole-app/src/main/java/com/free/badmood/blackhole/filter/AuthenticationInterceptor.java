package com.free.badmood.blackhole.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.context.UnionIdContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.free.badmood.blackhole.web.service.ITokenService.SECRET;

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

        String unionId = null;

        //需不需要认证的接口都读取设置下openid
        String token = ((HttpServletRequest) request).getHeader("token");
        if(StringUtils.hasLength(token)){
            try{
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
                DecodedJWT verify = jwtVerifier.verify(token);
                unionId = verify.getAudience().get(0);
                UnionIdContext.UNIONID.set(unionId);
            }catch (Exception exception){
                return401(response);
                return false;
            }

        }


        boolean needAuthentication;
        if (handler != null) {
            if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                HandlerMethod handlerMethod = ((HandlerMethod) handler);
                needAuthentication = handlerMethod.getMethod().isAnnotationPresent(RequireAuthentication.class);

                if (!needAuthentication){
                    Class clazz = handlerMethod.getMethod().getDeclaringClass();
                    needAuthentication = clazz.isAnnotationPresent(RequireAuthentication.class);
                    if(needAuthentication){
                        return hasAuthentication(request, response,unionId);
                    }
                }else {
                    return hasAuthentication(request, response,unionId);
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
    private boolean hasAuthentication(HttpServletRequest request, HttpServletResponse response,String unionId) throws IOException {

        String token = ((HttpServletRequest) request).getHeader("token");


        if(userInfoContext.existUserInfo(unionId)){
//                UnionIdContext.UNIONID.set(unionId);
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException e) {
                e.printStackTrace();
                return401(response);
                return false;
            }
        }else {
            return401(response);
            return false;
        }
        return true;
    }

    public void return401(HttpServletResponse response) throws IOException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter writer = response.getWriter();
        writer.println(Result.unAuth2code(null).toString());
        writer.flush();
        writer.close();
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
