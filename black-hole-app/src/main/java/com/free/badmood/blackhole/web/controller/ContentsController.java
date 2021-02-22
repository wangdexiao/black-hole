package com.free.badmood.blackhole.web.controller;

import com.free.badmood.blackhole.web.entity.ContentVo;
import entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ContentsController {


    /**
     * @param start 起始位置
     * @param num   数量
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/getContents")
    public Result<List<ContentVo>> getContents(int start, int num) {

        log.error("start:" + start);
        log.error("num:" + num);

        List<ContentVo> contentVoList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            ContentVo contentVo = new ContentVo();
            contentVo.setAvatarUrl("http://www.wadexi.xyz/black-hole/" + (i % 9 + 1) + ".jpg");
            contentVo.setNickName("张三");
            contentVo.setContent("生活不像你想象的那么美好，哪那么多正能量，生活也不像你想象的那么槽，来这里卸下你的负面物质。");
            List<String> imageUrls = new ArrayList<>();
            imageUrls.add("http://www.wadexi.xyz/black-hole/1.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/2.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/3.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/4.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/5.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/6.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/7.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/8.jpg");
            imageUrls.add("http://www.wadexi.xyz/black-hole/9.jpg");
            contentVo.setImageUrls(imageUrls);
            contentVoList.add(contentVo);
        }

        return Result.ok(contentVoList);
    }
}
