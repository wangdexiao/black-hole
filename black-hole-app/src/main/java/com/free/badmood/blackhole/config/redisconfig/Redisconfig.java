package com.free.badmood.blackhole.config.redisconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class Redisconfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }
}
