package com.free.badmood.blackhole.web.entity;

import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wadexi
 * @since 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {


    /**
     * unionid
     */
    private String unionid;

    private String openId;

    private String sessionKey;

    private String nickName;

    private String avatarUrl;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String phoneNum;




}
