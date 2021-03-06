package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.constant.ReplyType;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Comment;
import com.free.badmood.blackhole.web.entity.Reply;
import com.free.badmood.blackhole.web.entity.ReplyVo;
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
        User userInfo = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        long userId = userInfo.getId();
        reply.setFromUserId(userId);

        if(ReplyType.REPLY_COMMENT.ordinal() == reply.getReplyType()){
//            Comment comment = commentService.getById(reply.getCommentId());
//            long toUserId = comment.getFromUserId();
//            reply.setToUserId(toUserId);
//            reply.setCommentId(comment.getId());
        }else {
            //根据什么查询出要回复的这条回复的内容，根据什么字段根据目标id replyId
            Reply replyReply = replyService.getById(reply.getReplyId());
            long commentId = replyReply.getCommentId();
            reply.setCommentId(commentId);
            reply.setToUserId(replyReply.getFromUserId());
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
    public Result<IPage<ReplyVo>> queryReplyByPage(int page,int count,long commentId){

        IPage<ReplyVo> replyVoIPage = replyService.queryReplyInfo(page, count, commentId);
//        Page<Reply> replyPage = replyService.getBaseMapper()
//                .selectPage(new Page<>(page, count),
//                        Wrappers.<Reply>lambdaQuery()
//                                .eq(Reply::getCommentId, commentId));

        return replyVoIPage != null ? Result.okData(replyVoIPage) : Result.fail("查询评论回复失败！");
    }


}
