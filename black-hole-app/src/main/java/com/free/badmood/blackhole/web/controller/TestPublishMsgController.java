package com.free.badmood.blackhole.web.controller;

import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.PublisherService;
import com.free.badmood.blackhole.web.entity.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/publish/msg")
public class TestPublishMsgController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/now")
    public Result<Boolean> publish(Msg msg) {
        publisherService.sendMessage(msg);
        return Result.okData(true);
    }
}
