package com.free.badmood.blackhole.web.entity;

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
public class Article extends BaseEntity {


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
     * 文黯发布的地名
     */
    private String addressName;

    /**
     * 文黯发布的详细地址
     */
    private String addressDetail;

    /**
     * 地理位置经度
     */
    private String latitude;

    /**
     * 地理位置维度
     */
    private String longitude;


    /**
     * 公开范围（0代表仅自己可看，1代表公开）
     */
    private int scope;

    /**
     * 文黯类型(0文黯，1视频)
     */
    private int type;

    /**
     * 文黯标签标签：
     * 0 : 自卑
     * 1：无聊
     * 2： 丧
     * 3： 恐惧
     * 4： 失望
     * 5： 迷茫
     * 6： 嫉妒
     * 7： 自责
     * 8： 忧愁
     * 9： 穷
     */
    private int tag;


    /**
     * 话题   #坦白局 我承认我自卑，我真的很怕黑
     */
    private String topic;


//    //文章相关联的资源url地址 （vo数据，前端展示）
//    @TableField(exist = false)
//    private List<String> resList;
//
//    //文章发布者的昵称 vo数据，前端展示
//    @TableField(exist = false)
//    private String nickName;
//
//    //文章发布者的头像 vo数据，前端展示
//    @TableField(exist = false)
//    private String avatarUrl;




}
