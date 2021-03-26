package com.free.badmood.blackhole;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.ArticleVo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.entity.UserCommentVo;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.ICommentService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@SpringBootTest()
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private ICommentService commentService;


    @Autowired
    private IArticleService articleService;

    @org.junit.Test
    public void test(){

//        IPage<UserCommentVo> userCommentVoIPage = commentService.queryUserComment(1, 10, 90);
//        log.error("===================");
//        log.error(userCommentVoIPage.toString());


//        Page<ArticleVo> articleVoPage = articleService.queryIndexArticle(10, 1,0,1);
//        log.error(articleVoPage.toString());

        Set<Object> articles = new HashSet<>();
        articles.add(123L);
        articles.add(124L);
        articles.add(125L);
        articles.add(126L);
        articles.add(127L);
        Page<ArticleVo> articleVoPage = articleService.querySupportAndCollectArticles(10, 1, 24, articles);
        log.error(articleVoPage.toString());
    }



}
