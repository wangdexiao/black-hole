package com.free.badmood.blackhole.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.free.badmood.blackhole.web.entity.Msg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.free.badmood.blackhole.web.entity.MsgVo;

import java.util.List;

/**
 * <p>
 * 站内信消息表 服务类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-27
 */
public interface IMsgService extends IService<Msg> {

    IPage<MsgVo> queryMsg(int size, int current);
}
