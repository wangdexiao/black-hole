package com.free.badmood.blackhole;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = "com.free.badmood.blackhole.filter")
@SpringBootApplication(exclude= {DruidDataSourceAutoConfigure.class})
public class BlackHoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackHoleApplication.class, args);
    }
}
