package com.free.badmood.blackhole.web.entity;


import lombok.Data;

@Data
public class TokenInfo {

    /**
     * 可以用openid 或 unionid替代
     */
    private String loginName;

    private String token;

    /**
     * 有效期
     */
    private long expiresIn = 60 * 60 * 1000;

    /**
     * token生成时间
     */
    private long tokenUpdateTime;



}
