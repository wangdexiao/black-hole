package com.free.badmood.blackhole.config;

import com.free.badmood.blackhole.config.redisconfig.PublisherService;
import com.free.badmood.blackhole.web.entity.Article;
import com.free.badmood.blackhole.web.entity.Msg;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.Data;

@Data
public class MsgRunnable implements Runnable{

    private long articleId,userId;
    private PublisherService publisherService;
    private IUserService userService;
    private IArticleService articleService;
    private int status;

    public MsgRunnable(
                        PublisherService publisherService,
                        IUserService userService,
                        IArticleService articleService,
                        int status,
                        long articleId,
                        long userId) {
        this.articleId = articleId;
        this.userId = userId;
        this.publisherService = publisherService;
        this.userService = userService;
        this.articleService = articleService;
        this.status = status;
    }

    @Override
    public void run() {
        Msg msg = new Msg();
        Article article = articleService.getById(articleId);
        long desUserId = article.getUserId();
        msg.setDestUserId(desUserId);

        User user = userService.getById(userId);
        if(status == 1){
            msg.setContent(user.getNickName() + "赞了你的文黯" + articleId);
        }else {
            msg.setContent(user.getNickName() + "取消了对你的文黯" + articleId +"的赞");
        }
        msg.setLinkId(articleId);
        msg.setType("3");


        publisherService.sendMessage(msg);
    }
}
