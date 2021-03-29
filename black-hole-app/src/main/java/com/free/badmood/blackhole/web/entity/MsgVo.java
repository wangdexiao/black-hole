package com.free.badmood.blackhole.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MsgVo extends Msg{

    private User fromUserInfo;

    private ArticleVo articleVo;
}
