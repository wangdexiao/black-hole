package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.context.MyContext;
import com.free.badmood.blackhole.web.entity.TFArticle;
import com.free.badmood.blackhole.web.entity.TFArticleRes;
import com.free.badmood.blackhole.web.entity.TFUser;
import com.free.badmood.blackhole.web.service.ITFArticleResService;
import com.free.badmood.blackhole.web.service.ITFArticleService;
import com.free.badmood.blackhole.web.service.ITFUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-03-02
 */
@Slf4j
@RestController
@RequestMapping("/article")
public class TFArticleController extends BaseController {

    @Value("${upload-res-photo-dir}")
    private String resPhotDir;

    @Value("${project-url}")
    private String projectUrl;

    @Autowired
    private ITFArticleService articleService;

    @Autowired
    private ITFArticleResService articleResService;

    @Autowired
    private ITFUserService userService;

    /**
     * 添加文黯
     * @param article 文黯实体
     * @return
     */
    @RequestMapping("/add")
    public Result<TFArticle> addArticle(TFArticle article){
        String openid = MyContext.OPENID.get();
        TFUser tfUser = userService.queryUserByOpenId(openid); //微信openid
        article.setUserId(tfUser.getId());//用户id
        article.setReadCount(0);//默认阅读数为0
        article.setLatitude("0");//设置经纬度
        article.setLongitude("0");//
        //保存文黯记录
        boolean saved = articleService.save(article);


        //组装资源记录
        List<String> photoList = article.getPhotoArray();
        final List<TFArticleRes> articleResList = new ArrayList<>();
        if(saved){
            long articleId = article.getId();
            photoList.forEach(url -> {
                TFArticleRes tfArticleRes = new TFArticleRes();
                int startIndex = url.lastIndexOf("/") == -1 ? 0 : url.lastIndexOf("/");
                tfArticleRes.setFilepath(resPhotDir + url.substring(startIndex));
                tfArticleRes.setUrl(projectUrl + url);
                tfArticleRes.setArticleId(articleId);
                articleResList.add(tfArticleRes);
            });

            // 保存文黯资源记录
            boolean savedRes = articleResService.saveBatch(articleResList);
            if(savedRes){
                return Result.okData(article);
            }else {
                articleService.removeById(article.getId());
                return Result.fail("发布文黯失败,保存资源文黯失败！",article);
            }
        }else {
            return Result.fail("发布文黯失败,保存文黯记录失败",article);
        }

    }


    /**
     * 获取文黯
     * @param page 页码数
     * @param count 每页数量
     * @return
     */
    @RequestMapping("/get")
    public Result<Page<TFArticle>> getArticleByPage(int count,int page){
        Page<TFArticle> aritcleByPage = articleService.getAritcleByPage(count,page);
        return aritcleByPage != null ? Result.okData(aritcleByPage) : Result.fail("获取文黯失败！", null);
    }

}
