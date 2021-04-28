package com.free.badmood.blackhole.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.free.badmood.blackhole.web.entity.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.free.badmood.blackhole.web.entity.ReplyVo;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 回复表 服务类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
public interface IReplyService extends IService<Reply> {

    IPage<ReplyVo> queryReplyInfo(int current,int size,long commentId);

}
