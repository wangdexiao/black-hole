package com.free.badmood.blackhole.web.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.RedisUserFans;
import com.free.badmood.blackhole.config.redisconfig.RedisUserFocus;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Article;
import com.free.badmood.blackhole.web.entity.BlackHoleUserVo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user")
public class BlackHoleUserInfoController {

    @Autowired
    private UserInfoContext userInfoContext;

    @Autowired
    private RedisUserFocus redisUserFocus;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private RedisUserFans redisUserFans;



    @PostMapping("/get")
    @RequireAuthentication
    public Result<BlackHoleUserVo> getUser(){
        BlackHoleUserVo blackHoleUserVo = new BlackHoleUserVo();
        User userInfo = userInfoContext.getUserInfoByOpenId(OpenIdContext.OPENID.get());
        long userId = userInfo.getId();
        // 关注数量
        long focusCount = redisUserFocus.sizeUserFocus(userId);
        blackHoleUserVo.setFocusCount(focusCount);

        long fansCount = redisUserFans.sizeUserFans(userId);
        blackHoleUserVo.setFansCount(fansCount);

        int articleCount = articleService.count(Wrappers.<Article>lambdaQuery()
                .eq(Article::getType, 0));
        blackHoleUserVo.setArticleCount(articleCount);

        int videoCount = articleService.count(Wrappers.<Article>lambdaQuery()
                .eq(Article::getType, 1));
        blackHoleUserVo.setVideoCount(videoCount);

        return Result.okData(blackHoleUserVo);

    }


}
