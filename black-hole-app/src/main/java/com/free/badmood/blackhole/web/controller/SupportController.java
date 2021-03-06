package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.MsgRunnable;
import com.free.badmood.blackhole.config.redisconfig.PublisherService;
import com.free.badmood.blackhole.config.redisconfig.RedisAritcleSupport;
import com.free.badmood.blackhole.config.redisconfig.RedisCommentSupport;
import com.free.badmood.blackhole.config.redisconfig.RedisUserSupport;
import com.free.badmood.blackhole.constant.SupportType;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Like;
import com.free.badmood.blackhole.web.entity.Msg;
import com.free.badmood.blackhole.web.entity.Support;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

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
     * 用于记录点赞过哪些文黯
     */
    private final ILikeService likeService;

    /**
     * 用于记录浏览过哪些文黯
     */
    private final IBrowseService browseService;


    /**
     * 文黯表service
     */
    private final IArticleService articleService;


//    private final IUserService userService;

    private final UserInfoContext userInfoContext;

    private RedisAritcleSupport redisAritcleSupport;

    private RedisCommentSupport redisCommentSupport;

    private final RedisUserSupport redisUserSupport;

    private final PublisherService publisherService;

    private ICommentService commentService;


    private IMsgService msgService;


    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private IUserService userService;

    public SupportController(
             ISupportService supportService,
             IArticleService articleService,
             UserInfoContext userInfoContext,
             ILikeService likeService,
             IBrowseService browseService,
             RedisAritcleSupport redisAritcleSupport,
             RedisUserSupport redisUserSupport,
             IMsgService msgService,
             PublisherService publisherService,
             RedisCommentSupport redisCommentSupport,
             ICommentService commentService) {

        this.supportService = supportService;
        this.articleService = articleService;
        this.likeService = likeService;
        this.userInfoContext = userInfoContext;
        this.browseService = browseService;
        this.redisAritcleSupport = redisAritcleSupport;
        this.redisUserSupport = redisUserSupport;
        this.msgService = msgService;
        this.publisherService = publisherService;
        this.redisCommentSupport = redisCommentSupport;
        this.commentService = commentService;
    }


    /**
     * 添加点赞
     * question： 点赞只能点一次
     */
    @PostMapping("/add")
    @RequireAuthentication
    public Result<String> redisAddSupport(Support support){
        boolean result = false;
        long articleId = 0;

        long typeId = support.getTypeId();//文黯的id或者评论的id
        int type = support.getType();
        boolean existFlag;

        //给文章点赞
        if(type == SupportType.SUPPORT_ARTICLE.ordinal()){
            //判断文章是否存在
            existFlag = articleService.getById(typeId) != null;
            articleId = typeId;
        }else{//给评论点赞
//            todo 只能点一次
            //判断评论是否存在
            existFlag = commentService.getById(typeId) != null;
        }

        //如果存在改文黯或者评论
        if(!existFlag){
            return Result.fail("不存在该文黯或评论");
        }
        //获取用户的id
        User user = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        long userId = user.getId();
        //设置用户id
        support.setUserId(userId);
        if(articleId > 0){
            return supportArticle(support);
        }else {
            return supportComment(support);
        }

    }

    private Result<String> supportArticle(Support support) {

        long articleId = support.getTypeId();
        long userId = support.getUserId();
        //已经点过赞
        if(redisAritcleSupport.existArticleSupport(userId, articleId)){

            redisAritcleSupport.canleArticleSupport(articleId,userId);
            redisUserSupport.canleUserSuport(userId,articleId);

            threadPoolExecutor.submit(new MsgRunnable(publisherService,userService,articleService,0,articleId, userId));

            return Result.ok("已经点过赞，已经取消点赞","cancel");

            //已经点过赞，又取消了 =》 点赞
        }else {//没有点过赞（redis数据库就没数据）=》点赞

            redisAritcleSupport.addArticleSupport(articleId,userId);
            redisUserSupport.addUserSupport(userId,articleId);
            threadPoolExecutor.submit(new MsgRunnable(publisherService,userService,articleService,1,articleId, userId));
            return Result.okData("ok") ;
        }
    }

    private Result<String> supportComment(Support support) {
        long commentId = support.getTypeId();
        long userId = support.getUserId();
        //已经点过赞
        if(redisCommentSupport.existCommentSupport(userId, commentId)){
            redisAritcleSupport.canleArticleSupport(commentId,userId);
            //todo 暂时不发送消息
//            threadPoolExecutor.submit(new MsgRunnable(publisherService,userService,articleService,0,commentId, userId));
            return Result.ok("已经点过赞，已经取消点赞","cancel");

            //已经点过赞，又取消了 =》 点赞
        }else {//没有点过赞（redis数据库就没数据）=》点赞
            redisAritcleSupport.addArticleSupport(commentId,userId);
            //todo 暂时不发送消息
//            threadPoolExecutor.submit(new MsgRunnable(publisherService,userService,articleService,1,articleId, userId));
            return Result.okData("ok") ;
        }

    }


    /**
     * 添加点赞
     * question： 点赞只能点一次
     */
    @PostMapping("/add2")
    @RequireAuthentication
    public Result<String> addSupport(Support support){
        boolean result = false;
        long articleId = 0;

        long typeId = support.getTypeId();//文黯的id或者评论的id
        int type = support.getType();
        boolean existFlag;

        //给文章点赞
        if(type == SupportType.SUPPORT_ARTICLE.ordinal()){
            //判断文章是否存在
            existFlag = articleService.getById(typeId) != null;
            articleId = typeId;
        }else{//给评论点赞
//            todo 只能点一次
            //判断评论是否存在
            existFlag = supportService.getById(typeId) != null;
        }

        //如果存在改文黯或者评论
        if(existFlag){
            //获取用户的id
            User user = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
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
                //=》取消点赞
                result = supportService.update(
                        new Support(),//这样写会更新updatetime
                        Wrappers.<Support>lambdaUpdate()
                                .eq(Support::getId,dbSupport.getId())
//                                .eq(Support::getTypeId,typeId)
//                                .eq(Support::getUserId,user.getId())
                                .set(Support::getStatus,0));

                cancelLikeList(articleId, user.getId());
                return result ? Result.ok("已经点过赞，已经取消点赞","cancel") : Result.fail("已经点过赞，取消点赞失败");


                  //已经点过赞，又取消了 =》 点赞
            }else if (dbSupport != null && dbSupport.getStatus() == 0){
                result = supportService.update(
                                new Support(),//这样写会更新updatetime
                                Wrappers.<Support>lambdaUpdate()
                                        .eq(Support::getId,dbSupport.getId())
                                        .set(Support::getStatus, 1));
                //todo 这里改成异步，暂未同步
                addLikeList(articleId, user.getId());
                return result ? Result.okData("ok") :Result.fail("点赞失败");
            }else {//没有点过赞（数据库就没数据）=》点赞
                result = supportService.save(support);
                //todo 这里改成异步，暂未同步
                addLikeList(articleId, user.getId());
                return result ? Result.okData("ok") :Result.fail("点赞失败");
            }

        }else{
            return Result.fail("不存在该文黯或评论");
        }


    }


    /**
     * 添加到我赞的列表中
     */
    private void addLikeList(long articleId,long userId){

        if(articleId <= 0 ){
            log.error("这是给评论点的赞，就不处理了");
            return ;
        }

        Like like = likeService.getOne(Wrappers.<Like>lambdaQuery()
                .eq(Like::getArticleId, articleId)
                .eq(Like::getUserId, userId));
        if(like != null){
            if(!like.isStatus()){//
                 likeService.update(
                        new Like(),
                        Wrappers.<Like>lambdaUpdate()
                                .set(Like::isStatus, true)
                                .eq(Like::getId, like.getId())
                );

            }
        }else {
            like = new Like(userId,articleId,true);
            likeService.save(like);

        }

    }


    /**
     * 从点赞列表中取消了赞
     * @param articleId 文黯id
     * @param userId 用户id
     * @return
     */
    private void cancelLikeList(long articleId,long userId){
        if(articleId <= 0 ){
            log.error("这是给评论点的赞，就不处理了");
            return ;
        }


        Like like = likeService.getOne(Wrappers.<Like>lambdaQuery()
                .eq(Like::getArticleId, articleId)
                .eq(Like::getUserId, userId));
        if(like != null){
            if(!like.isStatus()){//
                likeService.update(
                        new Like(),
                        Wrappers.<Like>lambdaUpdate()
                                .set(Like::isStatus, false)
                                .eq(Like::getId, like.getId())
                );
            }
        }
    }



}
