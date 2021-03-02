package com.free.badmood.blackhole;

import com.free.badmood.blackhole.web.entity.TFUser;
import com.free.badmood.blackhole.web.service.ITFUserService;
import com.free.badmood.blackhole.web.service.impl.TFUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@SpringBootTest()
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private ITFUserService userService;

    @org.junit.Test
    public void test(){
        TFUser userInfo = userService.queryUserByUnionId("oSqjy6IiBa2TwpqagC1dk2HdZk2Q");
        log.info(userInfo.toString());
    }
}
