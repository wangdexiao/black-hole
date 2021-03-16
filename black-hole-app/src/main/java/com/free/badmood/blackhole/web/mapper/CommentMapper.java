package com.free.badmood.blackhole.web.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.free.badmood.blackhole.web.entity.UserCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<UserCommentVo> queryUserComment(Page<Comment> page, @Param("articleId") long articleId);

}
