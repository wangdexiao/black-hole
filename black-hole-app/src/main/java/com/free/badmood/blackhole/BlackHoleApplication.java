package com.free.badmood.blackhole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.free.badmood.blackhole.filter")
public class BlackHoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackHoleApplication.class, args);
    }
}
