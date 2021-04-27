package com.free.badmood.blackhole.config.redisconfig;

import com.free.badmood.blackhole.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import static com.free.badmood.blackhole.constant.CommonConstant.OBJECT_USERINFO;


@Component
public class RedisUserInfo {

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    /**
     * 缓存用户
     * @param unionid unionid
     * @param user 用户信息
     */
    public void addUserInfo(String unionid, User user){
        String key = OBJECT_USERINFO + unionid;
        valueOperations.set(key, user);
    }


    /**
     * 删除缓存的用户信息
     * @param unionid 用户id
     */
    public boolean delUserInfo(String unionid){
        String key = OBJECT_USERINFO + unionid;
        return valueOperations.getOperations().delete(key);
    }


    /**
     * 关注用户
     * @param unionid 用户id
     */
    public User queryUserInfo(String unionid){
        String key = OBJECT_USERINFO + unionid;
        return (User)valueOperations.get(key);
    }
}
