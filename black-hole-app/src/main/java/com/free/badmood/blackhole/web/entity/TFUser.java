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
public class TFUser extends BaseEntity {


    /**
     * unionid
     */
    private String unionid;

    private String openId;

    private String sessionKey;

    private String nickName;

    private Integer gender;

    private String country;

    private String province;

    private String city;




}
