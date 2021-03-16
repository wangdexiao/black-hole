package com.free.badmood.blackhole;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.entity.UserCommentVo;
import com.free.badmood.blackhole.web.service.ICommentService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@SpringBootTest()
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private ICommentService commentService;

    @org.junit.Test
    public void test(){

        IPage<UserCommentVo> userCommentVoIPage = commentService.queryUserComment(1, 10, 90);
        log.error("===================");
        log.error(userCommentVoIPage.toString());
    }
}
