package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 站内信消息表
 * </p>
 *
 * @author wadexi
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Msg extends BaseEntity {

    private static final long serialVersionUID = 1L;


    private Long fromUserId;

    /**
     * 发送给那个用户的id
     */
    private Long destUserId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息是否已读0-未读 1-已读
     */
    private Integer status;

    /**
     * 消息类型：	    1 文黯被转发 	    2. 文黯被评论	    3. 文黯被赞	    4. 评论被回复	    5. 评论被赞	    6. 被关注	    7. 官方消息
     */
    private String type;

    /**
     * 根据type id是不同的含义	   文黯id	   用户id	   跳转到消息展示页
     */
    private Long linkId;




}
