package com.free.badmood.blackhole.web.service.impl;

import com.free.badmood.blackhole.web.entity.Like;
import com.free.badmood.blackhole.web.mapper.LikeMapper;
import com.free.badmood.blackhole.web.service.ILikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户点赞了哪些文黯表 服务实现类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-17
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {

}
