package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.constant.ReplayType;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Comment;
import com.free.badmood.blackhole.web.entity.Reply;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.ICommentService;
import com.free.badmood.blackhole.web.service.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.free.badmood.blackhole.base.controller.BaseController;

/**
 * <p>
 * 回复表 前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/reply")
public class ReplyController extends BaseController {


    private IReplyService replyService;

    private UserInfoContext userInfoContext;

    private ICommentService commentService;

    @Autowired
    public ReplyController(IReplyService replyService, UserInfoContext userInfoContext, ICommentService commentService) {
        this.replyService = replyService;
        this.userInfoContext = userInfoContext;
        this.commentService = commentService;
    }

    /**
     * 添加回复
     * @param reply 回复实体
     * @return Boolean
     */
    @PostMapping("/add")
    @RequireAuthentication
    public Result<Boolean> addReply(Reply reply){
        User userInfo = userInfoContext.getUserInfoByOpenId(OpenIdContext.OPENID.get());
        long userId = userInfo.getId();
        reply.setFromUserId(userId);

        if(ReplayType.REPLAY_COMMENT.ordinal() == reply.getReplayType()){
            Comment comment = commentService.getById(reply.getReplayId());
            long toUserId = comment.getFromUserId();
            reply.setToUserId(toUserId);
            reply.setCommentId(comment.getId());
        }else {
            //根据什么查询出要回复的这条回复的内容，根据什么字段根据目标id replyId
            Reply replyReplay = replyService.getById(reply.getReplayId());
            long commentId = replyReplay.getCommentId();
            reply.setCommentId(commentId);
            reply.setToUserId(replyReplay.getFromUserId());
        }
        boolean savedFlag = replyService.save(reply);

        return savedFlag ? Result.okData(true) : Result.fail("添加回复失败");
    }


    /**
     * 查询回复
     * @param commentId 评论id
     * @return Page<Reply>
     */
    @PostMapping("/query")
    public Result<Page<Reply>> queryReplyByPage(int page,int count,long commentId){
        Page<Reply> replyPage = replyService.getBaseMapper()
                .selectPage(new Page<>(page, count),
                        Wrappers.<Reply>lambdaQuery()
                                .eq(Reply::getCommentId, commentId));

        return replyPage != null ? Result.okData(replyPage) : Result.fail("查询回复失败！");
    }


}
