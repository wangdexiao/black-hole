package com.free.badmood.blackhole.web.controller;


import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.RedisAritcleCollect;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户收藏了哪些文黯 前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController {


    @Autowired
    private RedisAritcleCollect redisAritcleCollect;

    @Autowired
    private UserInfoContext userInfoContext;

    @PostMapping("/add")
    @RequireAuthentication
    public Result<String> collectArticle(long articleId){
        User userInfo = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        if(redisAritcleCollect.existCollectArticle(userInfo.getId(),articleId)){
            redisAritcleCollect.canleCollectArticle(userInfo.getId(),articleId);
            return Result.okData("cancel");
        }else {
            redisAritcleCollect.collectArticle(userInfo.getId(), articleId);
            return Result.okData("ok");
        }
    }

//    @PostMapping("/cancel")
//    @RequireAuthentication
//    public Result<Boolean> cancelCollectArticle(long articleId){
//        User userInfo = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
//        redisAritcleCollect.canleCollectArticle(userInfo.getId(),articleId);
//        return Result.okData(true);
//    }

}
