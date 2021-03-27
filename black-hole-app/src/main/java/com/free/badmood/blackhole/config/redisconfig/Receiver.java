package com.free.badmood.blackhole.config.redisconfig;

import com.free.badmood.blackhole.web.entity.Msg;
import com.free.badmood.blackhole.web.service.IMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver extends MessageListenerAdapter {

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private IMsgService msgService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<Msg> valueSerializer = redisTemplate.getValueSerializer();
        Msg msg =  valueSerializer.deserialize(message.getBody());

        boolean save = msgService.save(msg);
        log.info("收到的redis mq消息",msg);
        log.info("是否保存成功",save);
    }

}
