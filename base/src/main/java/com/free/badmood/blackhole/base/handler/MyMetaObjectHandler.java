//package com.free.badmood.blackhole.base.handler;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class MyMetaObjectHandler implements MetaObjectHandler {
//
//    @Autowired
//    private UserInfoC
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        log.info("start insert fill ....");
//        Date date = new Date();
//        this.strictInsertFill(metaObject, "createTime", Date.class, date); // 起始版本 3.3.0(推荐使用)
//        this.strictInsertFill(metaObject, "updateTime", Date.class, date);
//        // 起始版本 3.3.0(推荐使用)
//        this.strictInsertFill(metaObject, "createUserId", Long.class, date); // 起始版本 3.3.0(推荐使用)
////        this.fillStrategy(metaObject, "createTime", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug请升级到之后的版本如`3.3.1.8-SNAPSHOT`)
//        /* 上面选其一使用,下面的已过时(注意 strictInsertFill 有多个方法,详细查看源码) */
//        //this.setFieldValByName("operator", "Jerry", metaObject);
//        //this.setInsertFieldValByName("operator", "Jerry", metaObject);
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        log.info("start update fill ....");
//        Date date = new Date();
//        this.strictUpdateFill(metaObject, "updateTime", Date.class, date); // 起始版本 3.3.0(推荐使用)
////        this.fillStrategy(metaObject, "updateTime", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug请升级到之后的版本如`3.3.1.8-SNAPSHOT`)
//        /* 上面选其一使用,下面的已过时(注意 strictUpdateFill 有多个方法,详细查看源码) */
//        //this.setFieldValByName("operator", "Tom", metaObject);
//        //this.setUpdateFieldValByName("operator", "Tom", metaObject);
//    }
//
//
//
//}
