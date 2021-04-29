package com.free.badmood.blackhole.constant;

/**
 * 常用的一些常量
 */
public interface CommonConstant {

    String WX_APPID = "WX_APPID";
    String MSG_TOPIC = "TOPIC_MSG";

    String USER_LIST_SUPPORT_ARTICLE_PREFIX = "USER_SET_SUPPORT_ARTICLE"; //（文黯被点赞的用户列表key）前缀
    String USER_LIST_SUPPORT_COMMENT_PREFIX = "USER_SET_SUPPORT_ARTICLE"; //（评论被点赞的用户列表key）前缀
    String COMMENT_LIST_ARTICLE_PREFIX = "COMMENT_LIST_ARTICLE"; //文黯评论列表key前缀
    String COLLECT_ARTICLE_USER_LIST = "COLLECT_ARTICLE_LIST_BYUSER"; //（（用户收藏的文章列表）key） 前缀
    String SUPPORT_ARTICLE_LIST_BYUSER = "SUPPORT_ARTICLE_LIST_BYUSER"; //（（用户点赞的文章列表）key） 前缀
    String FOCUS_USER_LIST_BYUSER = "FOCUS_USER_LIST_BYUSER"; //（用户关注的用户列表）key 前缀
    String FANS_USER_LIST_TOUSER = "FANS_USER_LIST_TOUSER"; //（关注用户的粉丝列表）key 前缀
    String ARTICLE_COMMENT_COUNT = "ARTICLE_COMMENT_COUNT"; //文黯评论的数量
    String OBJECT_USERINFO = "OBJECT_USERINFO"; //用户对象
}
