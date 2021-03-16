package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Comment;
import com.free.badmood.blackhole.web.entity.UserCommentVo;
import com.free.badmood.blackhole.web.mapper.CommentMapper;
import com.free.badmood.blackhole.web.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    public IPage<UserCommentVo> queryUserComment(int page,int count, long articleId){
        return getBaseMapper().queryUserComment(new Page<>(page, count), articleId);
    }

}
