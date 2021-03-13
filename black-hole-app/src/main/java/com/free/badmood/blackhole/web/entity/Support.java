package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Support extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文黯的id或者评论的id
     */
    private Long typeId;

    /**
     * 0 代表作品点赞 1代表评论点赞
     */
    private Integer type;

    /**
     * 点赞用户的id
     */
    private Long userId;

    /**
     * 0代表取消点赞 1 代表有效赞
     */
    private Integer status;



}
