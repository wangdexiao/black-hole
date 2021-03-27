package com.free.badmood.blackhole.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class MsgThreadPool {


    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 7, 30, TimeUnit.SECONDS, blockingQueue);
        return threadPoolExecutor;
    }

}
