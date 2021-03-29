package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Msg;
import com.free.badmood.blackhole.web.entity.MsgVo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.free.badmood.blackhole.base.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 站内信消息表 前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/msg")
public class MsgController extends BaseController {


    @Autowired
    private IMsgService msgService;

    @PostMapping("/get")
    @RequireAuthentication
    public Result<IPage<MsgVo>> queryMsg(int size, int current){

        IPage<MsgVo> msgListPage = msgService.queryMsg(size,current);

        return msgListPage == null ? Result.fail("获取消息失败"): Result.okData(msgListPage);
    }



}
