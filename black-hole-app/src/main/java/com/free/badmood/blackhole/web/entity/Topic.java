package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 话题表
 * </p>
 *
 * @author wadexi
 * @since 2021-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Topic extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 话题内容
     */
    private String name;

    /**
     * 相关话题文黯的数量
     */
    private Long articleCount;

    /**
     * 话题创建人的用户id
     */
    private Long createUserId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
