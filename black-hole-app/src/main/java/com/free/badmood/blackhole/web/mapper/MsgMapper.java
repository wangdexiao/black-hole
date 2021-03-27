package com.free.badmood.blackhole.web.mapper;

import com.free.badmood.blackhole.web.entity.Msg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 站内信消息表 Mapper 接口
 * </p>
 *
 * @author wadexi
 * @since 2021-03-27
 */
@Mapper
public interface MsgMapper extends BaseMapper<Msg> {

}
