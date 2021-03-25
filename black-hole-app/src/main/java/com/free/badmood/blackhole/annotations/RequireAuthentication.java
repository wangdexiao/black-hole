package com.free.badmood.blackhole.annotations;


import java.lang.annotation.*;

/**
 * 在controller 类上或方法上添加该注解
 * 表明需要认证成功才能调用该接口
 */

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireAuthentication {

    boolean required() default true;
}
