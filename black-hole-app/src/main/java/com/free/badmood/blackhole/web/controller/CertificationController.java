package com.free.badmood.blackhole.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.free.badmood.blackhole.context.LoginStateContext;
import com.free.badmood.blackhole.web.entity.WxCreditInfoEntity;
import entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 认证
 */
@Slf4j
@RestController
public class CertificationController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

//    @Value("${WXAPPID}")
//    private String wxAppId;
//
//    @Value("${WXAPPSECRET}")
//    private String wxAppSecret;


    @Autowired
    private LoginStateContext loginStateContext;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result<WxCreditInfoEntity> login(HttpServletRequest request,String code){
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
                    loginStateContext.addLoginState(wxCreditInfoEntity);
                }
            }
        }else {
            errCode = -1;
        }


//        log.info("code:" + wxCreditInfoResult.toString());
        return errCode == 0 ? Result.ok(wxCreditInfoEntity) : Result.fail(errCode, errMsg, null);
    }


    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Result<Boolean> login(HttpHeaders headers){
        String openid = headers.getFirst("openid");
        boolean bool = loginStateContext.removeLoginState(openid);
        return Result.ok(bool);
    }
}
