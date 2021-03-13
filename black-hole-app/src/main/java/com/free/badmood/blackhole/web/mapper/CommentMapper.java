package com.free.badmood.blackhole.web.mapper;

import com.free.badmood.blackhole.web.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
