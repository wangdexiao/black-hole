package com.free.badmood.blackhole;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.ArticleVo;
import com.free.badmood.blackhole.web.entity.MsgVo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.entity.UserCommentVo;
import com.free.badmood.blackhole.web.mapper.ArticleMapper;
import com.free.badmood.blackhole.web.mapper.MsgMapper;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.ICommentService;
import com.free.badmood.blackhole.web.service.IUserService;
import com.free.badmood.blackhole.web.service.IWxSendMsgService;
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


    @Autowired
    private MsgMapper msgMapper;


    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private IWxSendMsgService wxSendMsgService;

    @org.junit.Test
    public void test(){

//        IPage<UserCommentVo> userCommentVoIPage = commentService.queryUserComment(1, 10, 90);
//        log.error("===================");
//        log.error(userCommentVoIPage.toString());


//        Page<ArticleVo> articleVoPage = articleService.queryIndexArticle(10, 1,0,1);
//        log.error(articleVoPage.toString());

//        Set<Object> articles = new HashSet<>();
//        articles.add(123L);
//        articles.add(124L);
//        articles.add(125L);
//        articles.add(126L);
//        articles.add(127L);
//        Page<ArticleVo> articleVoPage = articleService.querySupportAndCollectArticles(10, 1, 24, articles);
//        log.error(articleVoPage.toString());
//        ArticleVo articleVo = articleMapper.queryArticleDetailById(143L);
//
//        IPage<MsgVo> msgVoIPage = msgMapper.queryMsg(new Page(1, 10), 34);
//        log.error("单元测试结果：" + msgVoIPage.getRecords().toString());

        boolean b = wxSendMsgService.sendMsg();
        log.error("发送消息是否成功：" + b);
    }



}
