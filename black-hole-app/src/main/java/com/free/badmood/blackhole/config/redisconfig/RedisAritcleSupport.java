package com.free.badmood.blackhole.config.redisconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.free.badmood.blackhole.constant.CommonConstant.USER_LIST_SUPPORT_ARTICLE_PREFIX;

@Slf4j
@Component
public class RedisAritcleSupport {


    @Autowired
    private SetOperations<String, Object> setOperations;

//    @Autowired
//    private ListOperations<String, Object> listOperations;


    /**
     * 给文黯点赞
     * @param articleId 文黯id
     * @param userId 点赞用户id
     */
    public void addArticleSupport(long articleId,long userId){
        String key = USER_LIST_SUPPORT_ARTICLE_PREFIX + articleId;
        long flag = setOperations.add(key, userId);
        log.error("redis点赞返回结果" + flag);
    }


    /**
     * 用户取消点赞
     * @param articleId 文黯id
     * @param userId 用户id
     */
    public void canleArticleSupport(long articleId,long userId){
        String key = USER_LIST_SUPPORT_ARTICLE_PREFIX + articleId;
        setOperations.remove(key, userId);
    }


    /**
     * 该用户是否赞了该文黯
     * @param userId 用户id
     * @param articleId 文黯id
     */
    public boolean existArticleSupport(long userId,long articleId){
        String key = USER_LIST_SUPPORT_ARTICLE_PREFIX + articleId;
        return setOperations.isMember(key, userId);
    }


    /**
     * 该文黯的点赞数量
     * @param articleId 文黯id
     */
    public long sizeArticleSupport(long articleId){
        String key = USER_LIST_SUPPORT_ARTICLE_PREFIX + articleId;
        return setOperations.size(key);
    }


    /**
     * 该文黯的用户李彪
     * @param articleId 文黯id
     * @return
     */
    public Set<Object> memberArticleSupport(long articleId){
        String key = USER_LIST_SUPPORT_ARTICLE_PREFIX + articleId;
        return setOperations.members(key);
    }








}
