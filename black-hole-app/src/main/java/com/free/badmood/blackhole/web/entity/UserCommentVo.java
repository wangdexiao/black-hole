package com.free.badmood.blackhole.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCommentVo extends Comment{

    private static final long serialVersionUID = 1L;



    private String nickName;

    private String avatarUrl;

}
