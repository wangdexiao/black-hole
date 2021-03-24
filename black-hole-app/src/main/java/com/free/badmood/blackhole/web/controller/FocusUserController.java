package com.free.badmood.blackhole.web.controller;

import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.RedisUserFocus;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关注用户前端控制器
 */
@RestController
@RequestMapping("/focus")
public class FocusUserController {


    @Autowired
    private RedisUserFocus redisUserFocus;


    @Autowired
    private UserInfoContext userInfoContext;

    /**
     * 关注用户或取消关注用户
     * @param focusUserId 被关注的用户id
     * @return
     */
    @PostMapping("/add")
    @RequireAuthentication
    public Result<String> addFocusUser(long focusUserId){
        User userInfo = userInfoContext.getUserInfoByOpenId(OpenIdContext.OPENID.get());
        if(redisUserFocus.existUserFocus(userInfo.getId(),focusUserId)){
            redisUserFocus.canleUserFocus(userInfo.getId(),focusUserId);
            return Result.okData("cancel");
        }else {
            redisUserFocus.addUserFocus(userInfo.getId(), focusUserId);
            return Result.okData("ok");
        }
    }
}
