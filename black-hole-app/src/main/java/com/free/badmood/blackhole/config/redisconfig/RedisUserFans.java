package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import static com.free.badmood.blackhole.constant.CommonConstant.FANS_USER_LIST_TOUSER;

/**
 * 用户的粉丝 redis工具类
 */
@Component
public class RedisUserFans {


    @Autowired
    private SetOperations<String, Object> setOperations;

//    @Autowired
//    private ListOperations<String, Object> listOperations;


    /**
     * 粉丝关注了用户
     * @param userId 文黯id
     * @param fansUserId 粉丝用户的id
     */
    public void addUserFans(long userId,long fansUserId){
        String key = FANS_USER_LIST_TOUSER + userId;
        setOperations.add(key, fansUserId);
    }


    /**
     * 取消关注用户
     * @param userId 用户id
     * @param fansUserId 粉丝用户id
     */
    public void canleUserFans(long userId,long fansUserId){
        String key = FANS_USER_LIST_TOUSER + userId;
        setOperations.remove(key, fansUserId);
    }


    /**
     * 是否是否该用户的粉丝
     * @param userId 用户id
     * @param fansUserId 粉丝用户id
     */
    public boolean existUserFans(long userId,long fansUserId){
        String key = FANS_USER_LIST_TOUSER + userId;
        return setOperations.isMember(key, fansUserId);
    }


    /**
     * 用户拥有多少粉丝
     * @param userId 用户id
     */
    public long sizeUserFans(long userId){
        String key = FANS_USER_LIST_TOUSER + userId;
        return setOperations.size(key);
    }






}
