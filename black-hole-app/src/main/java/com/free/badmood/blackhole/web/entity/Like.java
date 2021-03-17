package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户点赞了哪些文黯表
 * </p>
 *
 * @author wadexi
 * @since 2021-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Like extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long articleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
