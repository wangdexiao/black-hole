package com.free.badmood.blackhole.web.entity;

import lombok.Data;

@Data
public class ReplyVo extends Reply{


    /**
     * 用户头像url
     */
    private String userAvatarUrl;

    /**
     * 用户昵称
     */
    private String userNickName;


}
