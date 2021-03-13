package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.constant.SupportType;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Support;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.ISupportService;
import com.free.badmood.blackhole.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 点赞表 前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/support")
public class SupportController extends BaseController {

    /**
     * 点赞表service
     */
    @Autowired
    private ISupportService supportService;

    /**
     * 文黯表service
     */
    @Autowired
    private IArticleService articleService;


    @Autowired
    private IUserService userService;

    @Autowired
    private UserInfoContext userInfoContext;



    /**
     * 添加点赞
     */
    @PostMapping("/add")
    @RequireAuthentication
    public Result<Boolean> addSupport(Support support){
        boolean result = false;

        long typeId = support.getTypeId();//文黯的id或者评论的id
        int type = support.getType();
        boolean existFlag;

        if(type == SupportType.SUPPORT_ARTICLE.ordinal()){
            existFlag = articleService.getById(typeId) != null;
        }else{
            existFlag = supportService.getById(typeId) != null;
        }

        //如果存在改文黯或者评论
        if(existFlag){
            //获取用户的id
            User user = userInfoContext.getUserInfoByOpenId(OpenIdContext.OPENID.get());
            //设置用户id
            support.setUserId(user.getId());
            result = supportService.save(support);
        }

        return Result.okData(result);
    }

    /**
     * 添加点赞
     */
    @PostMapping("/cancel")
    @RequireAuthentication
    public Result<Boolean> cancelSupport(Support support){

        long typeId = support.getTypeId();//文黯的id或者评论的id
        int type = support.getType();
        //获取用户的id
        User user = userInfoContext.getUserInfoByOpenId(OpenIdContext.OPENID.get());
        long userId = user.getId();
        boolean deltedFlag =
                supportService.remove(Wrappers.<Support>lambdaQuery()
                                        .eq(Support::getType,type)
                                        .eq(Support::getTypeId,typeId)
                                        .eq(Support::getUserId,userId));

        return Result.okData(deltedFlag);
    }



}
