package com.free.badmood.blackhole.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.free.badmood.blackhole.web.entity.ArticleVo;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-02
 */
public interface IArticleService extends IService<Article> {


//    default Page<Article> getAritcleByPage(int count, int page){
//        return null;
//    }


    Page<ArticleVo> queryIndexArticle(int count,int page,int type,int scope,int tag,String topic);


    Page<ArticleVo> querySupportAndCollectArticles(int size,int current,long userId, Set<Object> articleIds);


    Page<ArticleVo> queryCommentArticles(int size,int current, Set<Object> articleIds);


//    IPage<User> getUserListSupportArticle(long articleId);

}
