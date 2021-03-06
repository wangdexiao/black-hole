package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Reply;
import com.free.badmood.blackhole.web.entity.ReplyVo;
import com.free.badmood.blackhole.web.mapper.ReplyMapper;
import com.free.badmood.blackhole.web.service.IReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 回复表 服务实现类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public IPage<ReplyVo> queryReplyInfo(int current,int size,long commentId) {
        return replyMapper.queryReplyInfo(new Page(current,size),commentId);
    }
}
