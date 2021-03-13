package com.free.badmood.blackhole.web.entity;

import java.time.LocalDateTime;
import com.free.badmood.blackhole.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wadexi
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleRes extends BaseEntity {


    /**
     * 资源路径
     */
    private String url;

    /**
     * 本地文件绝对路径
     */
    private String filepath;

    /**
     * 该资源关联的文章id
     */
    private Long articleId;




}
