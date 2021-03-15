package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Comment;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.ICommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    private final ICommentService commentService;


    private final UserInfoContext userInfoContext;

    public CommentController(ICommentService commentService, UserInfoContext userInfoContext) {
        this.commentService = commentService;
        this.userInfoContext = userInfoContext;
    }


    /**
     * 添加评论
     * @param comment 评论实体
     * @return Boolean
     */
    @PostMapping("/add")
    @RequireAuthentication
    public Result<Boolean> addCommnet(Comment comment){
        User userInfo = userInfoContext.getUserInfoByOpenId(OpenIdContext.OPENID.get());
        comment.setFromUserId(userInfo.getId());
        boolean savedFlag = commentService.save(comment);

        return savedFlag ? Result.okData(true) : Result.fail(-1, "添加评论失败！");
    }


    /**
     * 分页查询评论
     * @param articleId 文黯id
     * @return Page Comment
     */
    @PostMapping("/query")
    public Result<Page<Comment>> queryByPage(long articleId,int page,int count){

        Page<Comment> commentPage = commentService.getBaseMapper()
                .selectPage(new Page<>(page, count),
                        Wrappers.<Comment>lambdaQuery()
                                .eq(Comment::getArticleId,articleId)
                                .orderByDesc(Comment::getUpdateTime));

        return commentPage != null ? Result.okData(commentPage) : Result.fail("获取评论失败！", null);
    }


    /**
     * 删除评论
     * @param id 评论id
     * @return Boolean
     */
    @PostMapping("/del")
    @RequireAuthentication
    public Result<Boolean> delCommnet(long id){
        boolean delFlag = commentService.removeById(id);

        return delFlag ? Result.okData(true) : Result.fail(-1, "添加评论失败！");
    }

}
