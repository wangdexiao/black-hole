package com.free.badmood.blackhole.web.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.RedisAritcleSupport;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisAritcleSupport redisAritcleSupport;


    @Autowired
    private IUserService userService;

    @PostMapping("/list/support/article")
    public Result<List<User>> supportArticleUserList(long articleId){
        Set<Object> userIds = redisAritcleSupport.memberArticleSupport(articleId);
        List<User> userList = userService.list(Wrappers.<User>lambdaQuery().in(User::getId, userIds));
        return Result.okData(userList);
    }
}
