package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.context.MyContext;
import com.free.badmood.blackhole.web.entity.Article;
import com.free.badmood.blackhole.web.entity.ArticleRes;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IArticleResService;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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
    private IArticleService articleService;

    @Autowired
    private IArticleResService articleResService;

    @Autowired
    private IUserService userService;

    /**
     * 添加文黯
     * @param article 文黯实体
     * @return
     */
    @RequestMapping("/add")
    public Result<Article> addArticle(Article article){
        String openid = MyContext.OPENID.get();
        User user = userService.queryUserByOpenId(openid); //微信openid
        article.setUserId(user.getId());//用户id
        article.setReadCount(0);//默认阅读数为0
        article.setLatitude("0");//设置经纬度
        article.setLongitude("0");//
        //保存文黯记录
        boolean saved = articleService.save(article);


        //组装资源记录
        List<String> photoList = article.getPhotoArray();
        final List<ArticleRes> articleResList = new ArrayList<>();
        if(saved){
            long articleId = article.getId();
            photoList.forEach(url -> {
                ArticleRes articleRes = new ArticleRes();
                int startIndex = url.lastIndexOf("/") == -1 ? 0 : url.lastIndexOf("/");
                articleRes.setFilepath(resPhotDir + url.substring(startIndex));
                articleRes.setUrl(projectUrl + url);
                articleRes.setArticleId(articleId);
                articleResList.add(articleRes);
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
    public Result<Page<Article>> getArticleByPage(int count, int page){
        Page<Article> aritcleByPage = articleService.getAritcleByPage(count,page);
        return aritcleByPage != null ? Result.okData(aritcleByPage) : Result.fail("获取文黯失败！", null);
    }

}
