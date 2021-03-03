package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wadexi
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TFArticle extends BaseEntity {


    /**
     * 用户id，用户关联用户头像，名称
     */
    private long userId;

    /**
     * 阅读量
     */
    private Integer readCount;

    /**
     * 文黯内容
     */
    private String content;

    /**
     * 地理位置经度
     */
    private String latitude;

    /**
     * 地理位置维度
     */
    private String longitude;


    @TableField(exist = false)
    private List<String> photoArray;




}
