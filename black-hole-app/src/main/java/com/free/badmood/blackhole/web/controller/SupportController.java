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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/support")
public class SupportController extends BaseController {

    /**
     * 点赞表service
     */
    private final ISupportService supportService;

    /**
     * 文黯表service
     */
    private final IArticleService articleService;


//    private final IUserService userService;

    private final UserInfoContext userInfoContext;

    public SupportController(ISupportService supportService, IArticleService articleService, /*IUserService userService,*/ UserInfoContext userInfoContext) {
        this.supportService = supportService;
        this.articleService = articleService;
//        this.userService = userService;
        this.userInfoContext = userInfoContext;
    }


    /**
     * 添加点赞
     * question： 点赞只能点一次
     */
    @PostMapping("/add")
    @RequireAuthentication
    public Result<String> addSupport(Support support){
        boolean result = false;

        long typeId = support.getTypeId();//文黯的id或者评论的id
        int type = support.getType();
        boolean existFlag;

        //给文章点赞
        if(type == SupportType.SUPPORT_ARTICLE.ordinal()){
            //判断文章是否存在
            existFlag = articleService.getById(typeId) != null;

        }else{//给评论点赞
//            todo 只能点一次
            //判断评论是否存在
            existFlag = supportService.getById(typeId) != null;
        }

        //如果存在改文黯或者评论
        if(existFlag){
            //获取用户的id
            User user = userInfoContext.getUserInfoByOpenId(OpenIdContext.OPENID.get());
            //设置用户id
            support.setUserId(user.getId());

            //判断是否点过赞
            Support dbSupport = supportService.getBaseMapper()
                    .selectOne(
                            Wrappers.<Support>lambdaQuery()
                                    .eq(Support::getType, type)
                                    .eq(Support::getTypeId, typeId)
                                    .eq(Support::getUserId, user.getId())
                    );

            //已经点过赞
            if(dbSupport != null && dbSupport.getStatus() != 0){
                //取消点赞
                result = supportService.update(
                        new Support(),//这样写会更新updatetime
                        Wrappers.<Support>lambdaUpdate()
                                .eq(Support::getId,dbSupport.getId())
//                                .eq(Support::getTypeId,typeId)
//                                .eq(Support::getUserId,user.getId())
                                .set(Support::getStatus,0));
                return result ? Result.ok("已经点过赞，已经取消点赞","cancel") : Result.fail("已经点过赞，取消点赞失败");


                  //已经点过赞，又取消了
            }else if (dbSupport != null && dbSupport.getStatus() == 0){
                result = supportService.update(
                                new Support(),//这样写会更新updatetime
                                Wrappers.<Support>lambdaUpdate()
                                        .eq(Support::getId,dbSupport.getId())
                                        .set(Support::getStatus, 1));
                return result ? Result.okData("ok") :Result.fail("点赞失败");
            }else {//没有点过赞（数据库就没数据）
                result = supportService.save(support);
                return result ? Result.okData("ok") :Result.fail("点赞失败");
            }

        }else{
            return Result.fail("不存在该文黯或评论");
        }


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
