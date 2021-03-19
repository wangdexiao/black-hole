package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户点赞了哪些文黯表
 * </p>
 *
 * @author wadexi
 * @since 2021-03-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Like extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文黯id
     */
    private Long articleId;

    /**
     * 0-又取消了赞，1-赞了该文章
     */
    private boolean status;



}
