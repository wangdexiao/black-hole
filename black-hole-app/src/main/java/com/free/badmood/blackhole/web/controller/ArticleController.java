package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.RedisAritcleSupport;
import com.free.badmood.blackhole.context.OpenIdContext;
import com.free.badmood.blackhole.web.entity.Article;
import com.free.badmood.blackhole.web.entity.ArticleRes;
import com.free.badmood.blackhole.web.entity.ArticleVo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IArticleResService;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class ArticleController extends BaseController {

    @Value("${upload-res-photo-dir}")
    private String resPhotDir;

    @Value("${project-url}")
    private String projectUrl;

    private final IArticleService articleService;

    private final IArticleResService articleResService;

    private final IUserService userService;

    private final RedisAritcleSupport redisAritcleSupport;

    public ArticleController(IArticleService articleService, IArticleResService articleResService,
                             IUserService userService,RedisAritcleSupport redisAritcleSupport) {
        this.articleService = articleService;
        this.articleResService = articleResService;
        this.userService = userService;
        this.redisAritcleSupport = redisAritcleSupport;
    }

    /**
     * 添加文黯
     * @param articleVo 文黯实体
     * @return Article
     */
    @RequestMapping("/add")
    @RequireAuthentication
    public Result<Article> addArticle(ArticleVo articleVo){
        String openid = OpenIdContext.OPENID.get();
        User user = userService.queryUserByOpenId(openid); //微信openid
        articleVo.setUserId(user.getId());//用户id
        articleVo.setReadCount(0);//默认阅读数为0
        articleVo.setLatitude("0");//设置经纬度
        articleVo.setLongitude("0");//
        //保存文黯记录
        boolean saved = articleService.save(articleVo);


        //组装资源记录
        List<ArticleRes> photoList = articleVo.getResList();
//        final List<ArticleRes> articleResList = new ArrayList<>();
        if(saved){
            long articleId = articleVo.getId();
            photoList.forEach(item -> {
                String url = item.getUrl();
                int startIndex = url.lastIndexOf("/") == -1 ? 0 : url.lastIndexOf("/");
                item.setFilepath(resPhotDir + url.substring(startIndex));
                item.setUrl(projectUrl + url);
                item.setArticleId(articleId);
            });

            // 保存文黯资源记录
            boolean savedRes = articleResService.saveBatch(photoList);
            if(savedRes){
                return Result.okData(articleVo);
            }else {
                articleService.removeById(articleVo.getId());
                return Result.fail("发布文黯失败,保存资源文黯失败！",articleVo);
            }
        }else {
            return Result.fail("发布文黯失败,保存文黯记录失败",articleVo);
        }

    }


//    /**
//     * 获取文黯
//     * @param page 页码数
//     * @param count 每页数量
//     * @return Page<Article>
//     */
//    @RequestMapping("/get2")
//    public Result<Page<Article>> getArticleByPage(int count, int page){
//        Page<Article> aritcleByPage = articleService.getAritcleByPage(count,page);
//        return aritcleByPage != null ? Result.okData(aritcleByPage) : Result.fail("获取文黯失败！", null);
//    }



    /**
     * 获取文黯
     * @param page 页码数
     * @param count 每页数量
     * @param  type //1-代表视频 0-代表普通文黯
     * @param  scope //1-公开 0-仅自己可见
     * @return Page<Article>
     */
    @RequestMapping("/get")
    public Result<Page<ArticleVo>> queryArticleByPage(int count, int page,int type,int scope){
        Page<ArticleVo> aritcleByPage = articleService.queryIndexArticle(count,page,type,scope);
        List<ArticleVo> records = aritcleByPage.getRecords();
        records.forEach(it -> {
            long aritcleId = it.getId();
            long userId = it.getUserId();
            long supportCount = redisAritcleSupport.sizeArticleSupport(aritcleId);
            boolean currentUserSupport = redisAritcleSupport.existArticleSupport(userId,aritcleId);
            it.setSupportCount(supportCount);
            it.setCurrentUserSupport(currentUserSupport);
            it.setCommentCount(10);
        });
        return aritcleByPage != null ? Result.okData(aritcleByPage) : Result.fail("获取文黯失败！", null);
    }

}
