package com.free.badmood.blackhole.web.service.impl;

import com.free.badmood.blackhole.web.entity.Comment;
import com.free.badmood.blackhole.web.mapper.CommentMapper;
import com.free.badmood.blackhole.web.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
