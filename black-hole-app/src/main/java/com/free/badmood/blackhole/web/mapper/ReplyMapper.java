package com.free.badmood.blackhole.web.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.free.badmood.blackhole.web.entity.ReplyVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 回复表 Mapper 接口
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {

    IPage<ReplyVo> queryReplyInfo(Page page,long commentId);

}
