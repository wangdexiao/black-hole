package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.mapper.UserMapper;
import com.free.badmood.blackhole.web.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByUnionId(String unionId) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUnionid, unionId).eq(User::getDel,0)) ;
    }

    @Override
    public User queryUserByOpenId(String openId) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openId).eq(User::getDel,0)) ;
    }
}
