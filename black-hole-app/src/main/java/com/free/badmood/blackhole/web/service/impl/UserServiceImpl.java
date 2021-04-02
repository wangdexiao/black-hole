package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.config.redisconfig.RedisAritcleSupport;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.mapper.UserMapper;
import com.free.badmood.blackhole.web.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    @Autowired
    private RedisAritcleSupport redisAritcleSupport;

    @Override
    public User queryUserByUnionId(String unionId) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUnionid, unionId).eq(User::getDel,0)) ;
    }

    @Override
    public User queryUserByOpenId(String openId) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openId).eq(User::getDel,0)) ;
    }


    @Override
    public IPage<User> getUserListSupportArticle(int current,int size,long articleId) {
        Set<Object> userIds = redisAritcleSupport.memberArticleSupport(articleId);
        Page<User> pageUserList = page(new Page<>(current, size), Wrappers.<User>lambdaQuery().in(User::getId, userIds));
        return pageUserList;
    }
}
