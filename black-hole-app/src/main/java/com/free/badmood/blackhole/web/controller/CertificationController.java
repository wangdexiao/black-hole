package com.free.badmood.blackhole.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.entity.WxCreditInfoEntity;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
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

    public CertificationController(RestTemplate restTemplate, Environment env, UserInfoContext userInfoContext, IUserService userService) {
        this.restTemplate = restTemplate;
        this.env = env;
        this.userInfoContext = userInfoContext;
        this.userService = userService;
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result<WxCreditInfoEntity> login(String code){
        log.info("code:" + code);

        HttpHeaders headers = new HttpHeaders();

        MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        String errMsg =null;
        int errCode = 0;
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
        if(StringUtils.hasText(cretResult)){
            wxCreditInfoEntity = JSONObject.parseObject(cretResult, WxCreditInfoEntity.class);
            if(wxCreditInfoEntity!= null){
                errMsg = wxCreditInfoEntity.getErrmsg();
                errCode = wxCreditInfoEntity.getErrcode();
                if(errCode == 0){
                    User user = new User();
                    user.setOpenId(wxCreditInfoEntity.getOpenid());
                    user.setSessionKey(wxCreditInfoEntity.getSessionKey());
                    wxCreditInfoEntity.setSessionKey(null);
                    OpenIdContext.OPENID.set(wxCreditInfoEntity.getOpenid());
                    log.error("最新获得的sessionkey为：" + wxCreditInfoEntity.getSessionKey());
                    userInfoContext.addUserInfo(user);
                    User dbUser = userService.queryUserByOpenId(user.getOpenId());
                    if(dbUser != null){
                        log.error("现在更新sessionkey");
                        userService.update(Wrappers.<User>lambdaUpdate()
                                .eq(User::getId, dbUser.getId())
                                .set(User::getSessionKey, user.getSessionKey()));
                    }
                }
            }
        }else {
            errCode = -1;
        }



        assert wxCreditInfoEntity != null;
        return errCode == 0 ? Result.okData(wxCreditInfoEntity) : Result.fail(errCode, errMsg, null);
    }

    @PostMapping("/logout")
    @RequireAuthentication
    public Result<Boolean> login(HttpHeaders headers){
        String openid = headers.getFirst("openid");
        boolean bool = userInfoContext.removeUserInfo(openid);
        return Result.okData(bool);
    }

}
