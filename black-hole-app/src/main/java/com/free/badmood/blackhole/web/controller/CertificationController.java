package com.free.badmood.blackhole.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.TokenInfo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.entity.WxCreditInfoEntity;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.web.service.ITokenService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * 认证
 */
@Slf4j
@RestController
public class CertificationController {

    private final RestTemplate restTemplate;

    private final Environment env;

//    @Value("${WXAPPID}")
//    private String wxAppId;
//
//    @Value("${WXAPPSECRET}")
//    private String wxAppSecret;


    private final UserInfoContext userInfoContext;

    private final IUserService userService;

    @Autowired
    private ITokenService tokenService;

    public CertificationController(RestTemplate restTemplate, Environment env, UserInfoContext userInfoContext, IUserService userService) {
        this.restTemplate = restTemplate;
        this.env = env;
        this.userInfoContext = userInfoContext;
        this.userService = userService;
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result<TokenInfo> login(String code){
        TokenInfo tokenInfo = null;
        log.info("code:" + code);

        HttpHeaders headers = new HttpHeaders();

        MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        String errMsg =null;
        int errCode = -1;
        WxCreditInfoEntity wxCreditInfoEntity = null;
        String wxAppId = env.getProperty("WX_APPID");
        String wxAppSecret = env.getProperty("WX_APPSECRET");
        log.error("wxAppId:" + wxAppId);
        log.error("wxAppSecret:" + wxAppSecret);
        String cretResult = restTemplate.getForObject(
                "https://api.weixin.qq.com/sns/jscode2session" +
                        "?appid=" + wxAppId +
                        "&secret=" + wxAppSecret +
                        "&js_code=" + code +
                        "&grant_type=authorization_code",
                String.class);
        //授权成功，就相当于验证用户名密码成功
        if(StringUtils.hasText(cretResult)){
            wxCreditInfoEntity = JSONObject.parseObject(cretResult, WxCreditInfoEntity.class);
            if(wxCreditInfoEntity!= null){
                errMsg = wxCreditInfoEntity.getErrmsg();
                errCode = wxCreditInfoEntity.getErrcode();
                if(errCode == 0){

//                    wxCreditInfoEntity.setSessionKey(null);

                    log.error("最新获得的sessionkey为：" + wxCreditInfoEntity.getSessionKey());
                    User dbUser = userService.queryUserByOpenId(wxCreditInfoEntity.getOpenid());

                    //已经授权登录过了，就相当于已经注册过了，返回token（需要unionid）
                    if(dbUser != null){
                        dbUser.setOpenId(wxCreditInfoEntity.getOpenid());
                        dbUser.setSessionKey(wxCreditInfoEntity.getSessionKey());
                        userInfoContext.addUserInfo(dbUser);
                        //这种情况才有unionid（已经解密过用户信息）
                        tokenInfo =  tokenService.getToken(dbUser);
                        log.error("现在更新sessionkey");
                        userService.update(Wrappers.<User>lambdaUpdate()
                                .eq(User::getId, dbUser.getId())
                                .set(User::getSessionKey, dbUser.getSessionKey()));
                    }else {

                        //沒有unionid 先用openid替代
                        User user = new User();
                        user.setOpenId(wxCreditInfoEntity.getOpenid());
                        user.setSessionKey(wxCreditInfoEntity.getSessionKey());
                        user.setUnionid(wxCreditInfoEntity.getOpenid());
                        tokenInfo =  tokenService.getToken(user);
                        userInfoContext.addUserInfo(user);

                    }
                }
            }
        }else {
            errCode = -1;
        }



        assert wxCreditInfoEntity != null;
        return tokenInfo !=null
                ? Result.okData(tokenInfo)
                : Result.fail(errCode, StringUtils.hasLength(errMsg)? errMsg : "第一次授权认证，现在还没法获取token", null);
    }

    @PostMapping("/logout")
    @RequireAuthentication
    public Result<Boolean> login(HttpHeaders headers){
        String token = headers.getFirst("token");
        String unionId = JWT.decode(token).getAudience().get(0);

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(unionId)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
        }
        boolean bool = userInfoContext.removeUserInfo(unionId);
        return Result.okData(bool);
    }

}
