package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 回复表
 * </p>
 *
 * @author wadexi
 * @since 2021-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Reply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 回复的那条评论的id
     */
    private Long commentId;

    /**
     * 回复目标id-文黯id
     */
    private Integer replayId;

    /**
     * 回复类型 0-对评论进行回复 1-对回复进行回复
     */
    private Integer replayType;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复内容的制造者id
     */
    private Long fromUserId;

    /**
     * 回复的那条内容的制作者id，目标用户id 这条评论或这条回复的用户id，
     */
    private Long toUserId;




}
