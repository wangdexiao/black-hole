package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.web.entity.TFUser;
import com.free.badmood.blackhole.web.mapper.TFUserMapper;
import com.free.badmood.blackhole.web.service.ITFUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wadexi
 * @since 2021-02-26
 */
@Service
public class TFUserServiceImpl extends ServiceImpl<TFUserMapper, TFUser> implements ITFUserService {


    @Autowired
    private TFUserMapper userMapper;

    @Override
    public TFUser queryUserByUnionId(String unionId) {
        return userMapper.selectOne(new LambdaQueryWrapper<TFUser>().eq(TFUser::getUnionid, unionId).eq(TFUser::getDel,0)) ;
    }

    @Override
    public TFUser queryUserByOpenId(String openId) {
        return userMapper.selectOne(new LambdaQueryWrapper<TFUser>().eq(TFUser::getOpenId, openId).eq(TFUser::getDel,0)) ;
    }
}
