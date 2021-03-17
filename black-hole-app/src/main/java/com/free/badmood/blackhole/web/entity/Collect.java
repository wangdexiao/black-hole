package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户收藏了哪些文黯
 * </p>
 *
 * @author wadexi
 * @since 2021-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Collect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文黯id
     */
    private Integer articleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
