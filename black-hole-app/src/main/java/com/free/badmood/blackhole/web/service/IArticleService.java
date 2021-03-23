package com.free.badmood.blackhole.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.free.badmood.blackhole.web.entity.ArticleVo;

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


    Page<ArticleVo> queryIndexArticle(int count,int page,int type,int scope);





}
