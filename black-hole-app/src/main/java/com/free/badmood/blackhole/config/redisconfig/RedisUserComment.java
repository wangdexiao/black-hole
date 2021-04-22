package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.free.badmood.blackhole.constant.CommonConstant.COMMENT_LIST_ARTICLE_PREFIX;

/**
 * 统计给用户评论的文黯
 */
@Component
public class RedisUserComment {


//    @Autowired
//    private SetOperations<String, Object> setOperations;

    @Autowired
    private SetOperations<String, Object> setOperations;


    /**
     * 添加用户评论的文黯id
     * @param articleId 文黯id
     * @param userId 用户id
     */
    public void addUserCommentArticleId(long articleId, long userId){
        String key = COMMENT_LIST_ARTICLE_PREFIX + userId;
        setOperations.add(key, articleId);
    }

    /**
     * 删除用户评论的文黯id
     * @param articleId 文黯id
     * @param userId 用户id
     */
    public void removeUserCommentArticleId(long articleId, long userId){
        String key = COMMENT_LIST_ARTICLE_PREFIX + userId;
        setOperations.remove(key, articleId);
    }

    /**
     * 用户评论了哪些文黯
     * @param userId 用户id
     */
    public Set<Object> memberUserCommentArticleId(long userId){
        String key = COMMENT_LIST_ARTICLE_PREFIX + userId;
        Set<Object> members = setOperations.members(key);
        return members;
    }



}
