package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文黯id
     */
    private Long articleId;

    /**
     * 文黯的类型
     */
    private String articleType;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户id
     */
    private Integer fromUserId;



}
