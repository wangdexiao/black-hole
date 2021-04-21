package com.free.badmood.blackhole.web.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.free.badmood.blackhole.web.entity.ArticleRes;
import com.free.badmood.blackhole.web.entity.ArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wadexi
 * @since 2021-03-02
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


    Page<ArticleVo> queryIndexArticle(Page page,int type,int scope,int tag,long userId,long topicId);


    ArticleVo queryArticleDetailById(long articleId);

    /**
     *
     * @param articleId 文黯id
     */

    List<ArticleRes> getResUrls(long articleId);

    Page<ArticleVo> querySupportAndCollectArticles(Page page, long userId, Set<Object> articleIds);
//    long querySupportCount();

    Page<ArticleVo> queryCommentArticles(Page page, Set<Object> articleIds);

}



