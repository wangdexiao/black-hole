package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import static com.free.badmood.blackhole.constant.CommonConstant.COLLECT_ARTICLE_USER_LIST;

/**
 * 用户收藏redis 工具类
 */
@Component
public class RedisAritcleCollect {


    @Autowired
    private SetOperations<String, Object> setOperations;

//    @Autowired
//    private ListOperations<String, Object> listOperations;


    /**
     * 收藏文黯
     * @param articleId 文黯id
     * @param userId 用户id
     */
    public void collectArticle(long userId,long articleId){
        String key = COLLECT_ARTICLE_USER_LIST + userId;
        setOperations.add(key, articleId);
    }


    /**
     * 取消收藏该文黯
     * @param articleId 文黯id
     * @param userId 用户id
     */
    public void canleCollectArticle(long userId,long articleId){
        String key = COLLECT_ARTICLE_USER_LIST + userId;
        setOperations.remove(key, articleId);
    }


    /**
     * 该用户是否收藏了该文黯
     * @param userId 用户id
     * @param articleId 文黯id
     */
    public boolean existCollectArticle(long userId,long articleId){
        String key = COLLECT_ARTICLE_USER_LIST + userId;
        return setOperations.isMember(key, articleId);
    }


    /**
     * 该用户收藏了多少文黯
     * @param userId 用户id
     */
    public long sizeCollectArticle(long userId){
        String key = COLLECT_ARTICLE_USER_LIST + userId;
        return setOperations.size(key);
    }






}
