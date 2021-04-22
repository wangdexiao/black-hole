package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import static com.free.badmood.blackhole.constant.CommonConstant.ARTICLE_COMMENT_COUNT;

/**
 * 统计该用户有多少评论
 */
@Component
public class RedisAritcleCount {


    @Autowired
    private ValueOperations valueOperations;

    /**
     * 文黯的评论数量 + 1
     * @param articleId
     * @return
     */
    public int getCount(long articleId){
        Object value = valueOperations.get(ARTICLE_COMMENT_COUNT + articleId);
        return  value == null ? 0 : (Integer)value;
    }

    /**
     * 文黯的评论数量 + 1
     * @param articleId
     * @return
     */
    public long increCount(long articleId){
        return valueOperations.increment(ARTICLE_COMMENT_COUNT + articleId);
    }


    /**
     * 文黯的评论数量 - 1
     * @param articleId
     * @return
     */
    public long decrCount(long articleId){
        return valueOperations.decrement(ARTICLE_COMMENT_COUNT + articleId);
    }

    /**
     * 删除该文黯的统计
     * @param articleId
     * @return
     */
    public boolean delCount(long articleId){
        return valueOperations.getOperations().delete(ARTICLE_COMMENT_COUNT + articleId);
    }
}
