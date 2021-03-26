package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.*;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.Article;
import com.free.badmood.blackhole.web.entity.ArticleRes;
import com.free.badmood.blackhole.web.entity.ArticleVo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IArticleResService;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


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

    private final RedisAritcleCollect redisAritcleCollect;

    private final RedisUserSupport redisUserSupport;

    private final RedisUserFocus redisUserFocus;

    private final UserInfoContext userInfoContext;

    private final RedisUserComment redisUserComment;

    public ArticleController(IArticleService articleService, IArticleResService articleResService,
                             IUserService userService,RedisAritcleSupport redisAritcleSupport,
                             RedisAritcleCollect redisAritcleCollect,
                             RedisUserFocus redisUserFocus,UserInfoContext userInfoContext,
                             RedisUserSupport redisUserSupport,
                             RedisUserComment redisUserComment) {
        this.articleService = articleService;
        this.articleResService = articleResService;
        this.userService = userService;
        this.redisAritcleSupport = redisAritcleSupport;
        this.redisAritcleCollect = redisAritcleCollect;
        this.redisUserFocus = redisUserFocus;
        this.userInfoContext = userInfoContext;
        this.redisUserSupport = redisUserSupport;
        this.redisUserComment = redisUserComment;
    }

    /**
     * 添加文黯
     * @param articleVo 文黯实体
     * @return Article
     */
    @RequestMapping("/add")
    @RequireAuthentication
    public Result<Article> addArticle(ArticleVo articleVo){
        String unionId = UnionIdContext.UNIONID.get();
        User user = userService.queryUserByUnionId(unionId); //微信openid
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
            it.setSupportCount(supportCount);

            boolean currentUserSupport = redisAritcleSupport.existArticleSupport(userId,aritcleId);
            //当前用户是否点赞了
            it.setCurrentUserSupport(currentUserSupport);

            boolean currentUserCollect = redisAritcleCollect.existCollectArticle(userId,aritcleId);
            it.setCurrentUserCollect(currentUserCollect);

            if(StringUtils.hasLength(UnionIdContext.UNIONID.get())){
                User currentUser = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
                long currentUserId =  currentUser.getId();
                it.setHasFocusUser(redisUserFocus.existUserFocus(currentUserId, userId));
            }

            it.setCommentCount(10);
        });
        return aritcleByPage != null ? Result.okData(aritcleByPage) : Result.fail("获取文黯失败！", null);
    }

    /**
     * 查询我点过赞和收藏的文黯
     * @param count
     * @param page
     * @return
     */
    @PostMapping("/personal/support/collect/get")
    @RequireAuthentication
    public Result<Page<ArticleVo>> querySupportAndCollectArticles(int count, int page){
        //合并点过赞的文黯id 和 收藏的文黯id

        User user = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        long userId = user.getId();
        Set<Object> articleIds = redisUserSupport.supportAndCollectArticleIds(userId);

        Page<ArticleVo> articleVoPage = articleService.querySupportAndCollectArticles(count, page, userId, articleIds);
        return articleVoPage != null ? Result.okData(articleVoPage) : Result.fail("获取文黯失败！", null);
    }

    /**
     * 查询我评论过的文黯
     * @param count
     * @param page
     * @return
     */
    @PostMapping("/personal/comment/get")
    @RequireAuthentication
    public Result<Page<ArticleVo>> queryCommnetArticles(int count, int page){
        //合并点过赞的文黯id 和 收藏的文黯id

        User user = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        long userId = user.getId();
        Set<Object> articleIds = redisUserComment.memberUserCommentArticleId(userId);
        if(CollectionUtils.isEmpty(articleIds)){
            return Result.okData(new Page<>(page, count, 0, true));
        }

        Page<ArticleVo> articleVoPage = articleService.queryCommentArticles(count, page, articleIds);
        return articleVoPage != null ? Result.okData(articleVoPage) : Result.fail("获取文黯失败！", null);
    }

}
