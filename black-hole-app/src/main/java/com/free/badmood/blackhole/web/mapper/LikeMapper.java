package com.free.badmood.blackhole.web.mapper;

import com.free.badmood.blackhole.web.entity.Like;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户点赞了哪些文黯表 Mapper 接口
 * </p>
 *
 * @author wadexi
 * @since 2021-03-17
 */
@Mapper
public interface LikeMapper extends BaseMapper<Like> {

}
