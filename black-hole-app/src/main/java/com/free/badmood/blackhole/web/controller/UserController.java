package com.free.badmood.blackhole.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.base.utils.wxdecode.WXCore;
import com.free.badmood.blackhole.constant.CommonConstant;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.User;
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
public class UserController extends BaseController {


    private final Environment environment;

    private final UserInfoContext userInfoContext;


    private final IUserService tfUserService;

    public UserController(Environment environment, UserInfoContext userInfoContext, IUserService tfUserService) {
        this.environment = environment;
        this.userInfoContext = userInfoContext;
        this.tfUserService = tfUserService;
    }

    /**
     * 解密用户信息（通过session_key（不能放到前端））
     * @param iv iv
     * @param rawData 加密的用户数据
     * @return User
     */
    @PostMapping(value = "/userinfo")
    @RequireAuthentication
    public Result<User> decodeUserInfo(String iv, String rawData){
        String wxAppId = environment.getProperty(CommonConstant.WX_APPID);
        String openid = OpenIdContext.OPENID.get();
        User falseUser = userInfoContext.getUserInfoByOpenId(openid);
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



        return saved ? Result.okData(userInfo) : Result.fail("解密，磁盘化过程失败！",userInfo);
    }
}
