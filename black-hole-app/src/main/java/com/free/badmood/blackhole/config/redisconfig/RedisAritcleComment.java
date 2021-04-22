package com.free.badmood.blackhole.config.redisconfig;

import com.free.badmood.blackhole.web.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.free.badmood.blackhole.constant.CommonConstant.COMMENT_LIST_ARTICLE_PREFIX;
import static com.free.badmood.blackhole.constant.CommonConstant.USER_LIST_SUPPORT_ARTICLE_PREFIX;

/**
 * redis 存储文黯有哪些评论(不太需要有个统计数据就可以了)
 */
@Component
public class RedisAritcleComment {


//    @Autowired
//    private SetOperations<String, Object> setOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;


    /**
     * 给文黯添加评论
     * @param articleId 文黯id
     * @param comment 用户的评论
     */
    public void addArticleComment(long articleId, Comment comment){
        String key = COMMENT_LIST_ARTICLE_PREFIX + articleId;
        listOperations.leftPush(key, comment);
    }


    /**
     * 从缓存中删除最早的评论
     * @param articleId 文黯id
     */
    public void rightPop(long articleId){
        String key = COMMENT_LIST_ARTICLE_PREFIX + articleId;
        listOperations.rightPop(key);
    }


    /**
     * 获取一定返回的评论
     * @param articleId
     * @param start
     * @param end
     * @return
     */
    public List<Object> getComment(long articleId,int start,int end){
        String key = COMMENT_LIST_ARTICLE_PREFIX + articleId;
        return listOperations.range(key, start, end);

    }







}
