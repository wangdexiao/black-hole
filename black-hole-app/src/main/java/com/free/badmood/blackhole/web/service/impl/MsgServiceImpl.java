package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Msg;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.mapper.MsgMapper;
import com.free.badmood.blackhole.web.service.IMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 站内信消息表 服务实现类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-27
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

    @Autowired
    private UserInfoContext userInfoContext;


    @Override
    public IPage<Msg> queryMsg(int size,int current) {
        User userInfo = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        long userId = userInfo.getId();
        Page<Msg> page = page(new Page<>(current, size),
                Wrappers.<Msg>lambdaQuery()
                        .eq(Msg::getStatus, 0)
                        .eq(Msg::getDestUserId, userId)
                        .orderByDesc(Msg::getUpdateTime));
        return page;
    }
}
