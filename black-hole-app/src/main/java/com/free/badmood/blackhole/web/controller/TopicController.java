package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.web.entity.Topic;
import com.free.badmood.blackhole.web.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.free.badmood.blackhole.base.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 话题表 前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/topic")
public class TopicController extends BaseController {


    @Autowired
    private ITopicService topicService;

    /**
     * 根据用户输入模糊匹配和用户输入相关的话题
     */
    @PostMapping("/dimquery")

    public Result<List<Topic>> dimQueryTopics(String topicString){
        List<Topic> topicList = null;
        if(StringUtils.hasLength(topicString)){
            topicList = topicService.list(
                    Wrappers.<Topic>lambdaQuery()
                            .like(Topic::getText, topicString)
                            .last("limit 0,10"));
        }else {
            topicList = topicService.list(
                    Wrappers.<Topic>lambdaQuery()
                            .last("limit 0,10"));
        }

        return CollectionUtils.isEmpty(topicList) ? Result.fail(-200,"没有相关的话题") : Result.okData(topicList);
    }


    /**
     * 查询推荐的话题(todo 现在就返回最新的话题吧)
     */
    @PostMapping("/recommend")
    public Result<List<Topic>> recommendQueryTopics(){
        List<Topic> topicList = topicService.list(
                Wrappers.<Topic>lambdaQuery()
                        .last("limit 0,10"));
        return Result.okData(topicList);
    }
}
