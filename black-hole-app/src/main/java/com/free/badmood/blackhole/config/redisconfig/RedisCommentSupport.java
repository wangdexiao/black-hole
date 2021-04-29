package com.free.badmood.blackhole.config.redisconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.free.badmood.blackhole.constant.CommonConstant.USER_LIST_SUPPORT_COMMENT_PREFIX;

@Slf4j
@Component
public class RedisCommentSupport {


    @Autowired
    private SetOperations<String, Object> setOperations;

//    @Autowired
//    private ListOperations<String, Object> listOperations;


    /**
     * 给评论点赞
     * @param commentId 文黯id
     * @param userId 点赞用户id
     */
    public void addCommentSupport(long commentId,long userId){
        String key = USER_LIST_SUPPORT_COMMENT_PREFIX + commentId;
        long flag = setOperations.add(key, userId);
        log.error("redis点赞返回结果" + flag);
    }


    /**
     * 用户取消点赞
     * @param commentId 文黯id
     * @param userId 用户id
     */
    public void canleCommentSupport(long commentId,long userId){
        String key = USER_LIST_SUPPORT_COMMENT_PREFIX + commentId;
        setOperations.remove(key, userId);
    }


    /**
     * 该用户是否赞了该评论
     * @param userId 用户id
     * @param commentId 文黯id
     */
    public boolean existCommentSupport(long userId,long commentId){
        String key = USER_LIST_SUPPORT_COMMENT_PREFIX + commentId;
        return setOperations.isMember(key, userId);
    }


    /**
     * 该评论的点赞数量
     * @param commentId 文黯id
     */
    public long sizeCommentSupport(long commentId){
        String key = USER_LIST_SUPPORT_COMMENT_PREFIX + commentId;
        return setOperations.size(key);
    }


    /**
     * 该评论的用户列表
     * @param commentId 文黯id
     * @return
     */
    public Set<Object> memberCommentSupport(long commentId){
        String key = USER_LIST_SUPPORT_COMMENT_PREFIX + commentId;
        return setOperations.members(key);
    }








}
