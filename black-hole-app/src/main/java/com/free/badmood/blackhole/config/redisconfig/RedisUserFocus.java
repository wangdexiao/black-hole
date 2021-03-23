package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import static com.free.badmood.blackhole.constant.CommonConstant.FOCUS_USER_LIST_BYUSER;

/**
 * 用户关注 redis工具类
 */
@Component
public class RedisUserFocus {


    @Autowired
    private SetOperations<String, Object> setOperations;

//    @Autowired
//    private ListOperations<String, Object> listOperations;


    /**
     * 关注用户
     * @param userId 文黯id
     * @param userId 被关注用户id
     */
    public void addUserFocus(long userId,long focusUserId){
        String key = FOCUS_USER_LIST_BYUSER + userId;
        setOperations.add(key, focusUserId);
    }


    /**
     * 取消关注
     * @param userId 用户id
     * @param focusUserId 被关注用户id
     */
    public void canleUserFocus(long userId,long focusUserId){
        String key = FOCUS_USER_LIST_BYUSER + userId;
        setOperations.remove(key, focusUserId);
    }


    /**
     * 是否关注了该用户
     * @param userId 用户id
     * @param focusUserId 被关注的用户id
     */
    public boolean existUserFocus(long userId,long focusUserId){
        String key = FOCUS_USER_LIST_BYUSER + userId;
        return setOperations.isMember(key, focusUserId);
    }


    /**
     * 关注了多少用户
     * @param userId 用户id
     */
    public long sizeUserFocus(long userId){
        String key = FOCUS_USER_LIST_BYUSER + userId;
        return setOperations.size(key);
    }






}
