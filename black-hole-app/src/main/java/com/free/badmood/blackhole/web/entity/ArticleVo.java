package com.free.badmood.blackhole.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.free.badmood.blackhole.web.converter.ResListConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleVo extends Article{



    @Convert(converter = ResListConverter.class)
    private List<ArticleRes> resList;


    private User user;


    /**
     * 当前文黯获赞的数量
     */
    private long supportCount;


    /**
     * 当前文黯获的评论的数量
     */
    private long commentCount;

    /**
     * 当前当前用户是否已经点赞了该用户
     */
    private boolean currentUserSupport;


}
