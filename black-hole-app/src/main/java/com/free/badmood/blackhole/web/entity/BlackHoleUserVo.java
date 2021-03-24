package com.free.badmood.blackhole.web.entity;


import lombok.Data;

@Data
public class BlackHoleUserVo {

    /**
     * 发布文黯数量
     */
    private long articleCount;


    /**
     * 视频发布数量
     */
    private long videoCount;


    /**
     * 关注用户的数量
     */
    private long focusCount;


    /**
     * 粉丝的数量
     */
    private long fansCount;


}
