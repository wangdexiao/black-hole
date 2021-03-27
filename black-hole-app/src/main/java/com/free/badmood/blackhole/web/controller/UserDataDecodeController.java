package com.free.badmood.blackhole.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.base.utils.wxdecode.WXCore;
import com.free.badmood.blackhole.constant.CommonConstant;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.TokenInfo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.ITokenService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-02-26
 */

@Slf4j
@RestController
public class UserDataDecodeController extends BaseController {


    private final Environment environment;

    private final UserInfoContext userInfoContext;


    private final IUserService tfUserService;

    private final ITokenService tokenService;


    public UserDataDecodeController(Environment environment, UserInfoContext userInfoContext, IUserService tfUserService,
                                    ITokenService tokenService) {
        this.environment = environment;
        this.userInfoContext = userInfoContext;
        this.tfUserService = tfUserService;
        this.tokenService = tokenService;
    }

    /**
     * 解密用户信息（通过session_key（不能放到前端））
     * @param iv iv
     * @param rawData 加密的用户数据
     * @return User
     */
    @PostMapping(value = "/userinfo")
    @RequireAuthentication
    public Result<TokenInfo> decodeUserInfo(String iv, String rawData){
        TokenInfo token = null;
        String wxAppId = environment.getProperty(CommonConstant.WX_APPID);
        String unionId = UnionIdContext.UNIONID.get();
        User falseUser = userInfoContext.getUserInfoByUnionId(unionId);
        log.info("sessionKey為：" + falseUser.getSessionKey());
        log.info("wxAppId為：" + wxAppId);
        log.info("rawData為：" + rawData);
        log.info("iv為：" + iv);
        String decrypt = WXCore.decrypt(wxAppId, rawData, falseUser.getSessionKey(), iv);
        log.info("解密得到的用户信息为：" + decrypt);
        User userInfo = JSONObject.parseObject(decrypt, User.class);
        userInfo.setSessionKey(falseUser.getSessionKey());
        userInfo.setAvatarUrl(userInfo.getAvatarUrl());
        User dbUser = tfUserService.queryUserByUnionId(userInfo.getUnionid());
        //已经存在该用户信息
        if (dbUser != null){
            userInfo.setId(dbUser.getId());
        }
        //添加或更新用户信息
        boolean saved = tfUserService.saveOrUpdate(userInfo);
        userInfoContext.addUserInfo(userInfo);
        if(saved){
            token = tokenService.getToken(userInfo);
        }


        return token !=null ? Result.okData(token) : Result.fail("获取token失败",null);
    }


    /**
     * 获取文黯的点赞用户列表
     */
}
