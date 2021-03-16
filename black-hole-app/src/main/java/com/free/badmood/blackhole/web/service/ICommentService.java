package com.free.badmood.blackhole.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.free.badmood.blackhole.web.entity.UserCommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
public interface ICommentService extends IService<Comment> {


    IPage<UserCommentVo> queryUserComment(int page,int count, @Param("articleId") long articleId);
}
