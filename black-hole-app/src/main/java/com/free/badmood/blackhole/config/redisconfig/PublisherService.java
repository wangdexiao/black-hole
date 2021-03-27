package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.free.badmood.blackhole.constant.CommonConstant.MSG_TOPIC;

@Service
public class PublisherService {

    @Autowired
    private RedisTemplate redisTemplate;



    public String sendMessage(Object msg) {
        try {
            redisTemplate.convertAndSend(MSG_TOPIC, msg);
            return "消息发送成功了";

        } catch (Exception e) {
            e.printStackTrace();
            return "消息发送失败了";
        }
    }


}
