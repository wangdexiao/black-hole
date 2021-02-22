package com.free.badmood.blackhole.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ContentVo {

    private String avatarUrl;
    private String nickName;
    private String userId;
    private String content;
    private List<String> imageUrls;

}
