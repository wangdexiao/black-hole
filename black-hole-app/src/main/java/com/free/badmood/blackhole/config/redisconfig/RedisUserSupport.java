package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.free.badmood.blackhole.constant.CommonConstant.COLLECT_ARTICLE_USER_LIST;
import static com.free.badmood.blackhole.constant.CommonConstant.SUPPORT_ARTICLE_LIST_BYUSER;

/**
 * 用户关注的文黯列表
 */
@Component
public class RedisUserSupport {


    @Autowired
    private SetOperations<String, Object> setOperations;

//    @Autowired
//    private ListOperations<String, Object> listOperations;


    /**
     * 关注用户
     * @param userId 用户id
     * @param articleId 文黯id
     */
    public void addUserSupport(long userId,long articleId){
        String key = SUPPORT_ARTICLE_LIST_BYUSER + userId;
        setOperations.add(key, articleId);
    }


    /**
     * 取消点赞该文黯
     * @param userId 用户id
     * @param articleId 文黯id
     */
    public void canleUserSuport(long userId,long articleId){
        String key = SUPPORT_ARTICLE_LIST_BYUSER + userId;
        setOperations.remove(key, articleId);
    }


    /**
     * 是否关注了该文黯
     * @param userId 用户id
     * @param articleId 文黯id
     */
    public boolean existUserSupport(long userId,long articleId){
        String key = SUPPORT_ARTICLE_LIST_BYUSER + userId;
        return setOperations.isMember(key, articleId);
    }


    /**
     * 关注了多少用户
     * @param userId 用户id
     */
    public long sizeUserSupport(long userId){
        String key = SUPPORT_ARTICLE_LIST_BYUSER + userId;
        return setOperations.size(key);
    }


    /**
     * 用户点赞了哪些文黯
     * @param userId 用户id
     * @return
     */
    public Set<Object> memberArticleIds(long userId){
        String key = SUPPORT_ARTICLE_LIST_BYUSER + userId;
        return setOperations.members(key);
    }


    /**
     * 点赞和收藏了哪些文黯
     * @param userId 用户id
     * @return
     */
    public Set<Object> supportAndCollectArticleIds(long userId){
        String supportKey = SUPPORT_ARTICLE_LIST_BYUSER + userId;
        String collectKey = COLLECT_ARTICLE_USER_LIST + userId;
        return setOperations.union(supportKey,collectKey);
    }




}
