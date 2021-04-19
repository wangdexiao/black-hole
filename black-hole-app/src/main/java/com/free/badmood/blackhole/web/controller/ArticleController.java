package com.free.badmood.blackhole.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.annotations.RequireAuthentication;
import com.free.badmood.blackhole.base.controller.BaseController;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.config.redisconfig.*;
import com.free.badmood.blackhole.context.UnionIdContext;
import com.free.badmood.blackhole.context.UserInfoContext;
import com.free.badmood.blackhole.web.entity.*;
import com.free.badmood.blackhole.web.service.IArticleResService;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.free.badmood.blackhole.web.service.ITopicService;
import com.free.badmood.blackhole.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class ArticleController extends BaseController {

    @Value("${upload-res-photo-dir}")
    private String resPhotDir;

    @Value("${project-url}")
    private String projectUrl;

    private final IArticleService articleService;

    private final IArticleResService articleResService;

    private final ITopicService topicService;

    private final IUserService userService;

    private final RedisAritcleSupport redisAritcleSupport;

    private final RedisAritcleCollect redisAritcleCollect;

    private final RedisUserSupport redisUserSupport;

    private final RedisUserFocus redisUserFocus;

    private final UserInfoContext userInfoContext;

    private final RedisUserComment redisUserComment;

    public ArticleController(IArticleService articleService, IArticleResService articleResService,
                             IUserService userService, RedisAritcleSupport redisAritcleSupport,
                             RedisAritcleCollect redisAritcleCollect,
                             RedisUserFocus redisUserFocus, UserInfoContext userInfoContext,
                             RedisUserSupport redisUserSupport,
                             RedisUserComment redisUserComment,
                             ITopicService topicService) {
        this.articleService = articleService;
        this.articleResService = articleResService;
        this.userService = userService;
        this.redisAritcleSupport = redisAritcleSupport;
        this.redisAritcleCollect = redisAritcleCollect;
        this.redisUserFocus = redisUserFocus;
        this.userInfoContext = userInfoContext;
        this.redisUserSupport = redisUserSupport;
        this.redisUserComment = redisUserComment;
        this.topicService = topicService;
    }

    /**
     * 添加文黯
     *
     * @param articleVo 文黯实体
     * @return Article
     */
    @RequestMapping("/add")
    @RequireAuthentication
    public Result<Article> addArticle(ArticleVo articleVo) {
        log.error("添加文黯开始时间:"+System.currentTimeMillis());

        String unionId = UnionIdContext.UNIONID.get();
        log.error("查询用户开始时间:"+System.currentTimeMillis());
        User user = userService.queryUserByUnionId(unionId); //微信openid
        log.error("查询用户结束始时间:"+System.currentTimeMillis());
        articleVo.setUserId(user.getId());//用户id
        articleVo.setReadCount(0);//默认阅读数为0
//        articleVo.setLatitude("0");//设置经纬度
//        articleVo.setLongitude("0");//
        //保存文黯记录
        log.error("保存文黯开始时间:"+System.currentTimeMillis());
        boolean saved = articleService.save(articleVo);
        log.error("保存文黯结束时间:"+System.currentTimeMillis());

        //todo 保存话题 暂时不强制保存成功
        if(StringUtils.hasLength(articleVo.getTopic())){
            log.error("查询话题开始时间:"+System.currentTimeMillis());
            Topic dbTopic = topicService.getOne(Wrappers.<Topic>lambdaQuery().eq(Topic::getText, articleVo.getTopic()));
            log.error("查询话题结束时间:"+System.currentTimeMillis());
            if(dbTopic != null){
                Topic topic = new Topic();
                topic.setText(articleVo.getTopic());
                topic.setArticleCount(0L);//相关文黯数量
                topic.setCreateUserId(user.getId());//
                log.error("保存话题开始时间:"+System.currentTimeMillis());
                boolean saveTopicFlag = topicService.save(topic);
                log.error("保存话题结束时间:"+System.currentTimeMillis());
                log.error("保存话题成功"+ saveTopicFlag);
            }
        }

        //组装资源记录
        List<ArticleRes> photoList = articleVo.getResList();
//        final List<ArticleRes> articleResList = new ArrayList<>();
        if (saved) {
            long articleId = articleVo.getId();
            photoList.forEach(item -> {
                String url = item.getUrl();
                int startIndex = url.lastIndexOf("/") == -1 ? 0 : url.lastIndexOf("/");
                item.setFilepath(resPhotDir + url.substring(startIndex));
                item.setUrl(projectUrl + url);
                item.setArticleId(articleId);
            });

            // 保存文黯资源记录
            log.error("保存文黯资源开始时间:"+System.currentTimeMillis());
            boolean savedRes = articleResService.saveBatch(photoList);
            log.error("保存文黯资源结束时间:"+System.currentTimeMillis());
            if (savedRes) {
                return Result.okData(articleVo);
            } else {
                log.error("删除文黯开始时间:"+System.currentTimeMillis());
                articleService.removeById(articleVo.getId());
                log.error("删除文黯结束时间:"+System.currentTimeMillis());
                log.error("添加文黯结束时间:"+System.currentTimeMillis());
                return Result.fail("发布文黯失败,保存资源文黯失败！", articleVo);
            }
        } else {
            log.error("添加文黯结束时间:"+System.currentTimeMillis());
            return Result.fail("发布文黯失败,保存文黯记录失败", articleVo);
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
     *
     * @param page  页码数
     * @param count 每页数量
     * @param type  //1-代表视频 0-代表普通文黯
     * @param scope //1-公开 0-仅自己可见
     * @return Page<Article>
     */
    @RequestMapping("/get")
    public Result<Page<ArticleVo>> queryArticleByPage(int count, int page, int type, int scope,int tag,String topic) {

        log.error("首页查询文黯开始时间:"+System.currentTimeMillis());
        log.error("首页获取数据"+topic);
        log.error("首页分页查询文黯开始时间:"+System.currentTimeMillis());
        Page<ArticleVo> aritcleByPage = articleService.queryIndexArticle(count, page, type, scope,tag,topic);
        log.error("首页分页查询文黯结束时间:"+System.currentTimeMillis());
        List<ArticleVo> records = aritcleByPage.getRecords();


        records.forEach(it -> {
            long aritcleId = it.getId();
            long userId = it.getUserId();

            log.error("首页redis查询文黯点赞个数开始时间:"+System.currentTimeMillis());
            long supportCount = redisAritcleSupport.sizeArticleSupport(aritcleId);
            log.error("首页redis查询文黯点赞个数结束时间:"+System.currentTimeMillis());
            it.setSupportCount(supportCount);

            log.error("首页redis查询文黯当前用户是否点赞开始时间:"+System.currentTimeMillis());
            boolean currentUserSupport = redisAritcleSupport.existArticleSupport(userId, aritcleId);
            log.error("首页redis查询文黯当前用户是否点赞结束时间:"+System.currentTimeMillis());
            //当前用户是否点赞了
            it.setCurrentUserSupport(currentUserSupport);

            log.error("首页redis查询文黯被收藏个数开始时间:"+System.currentTimeMillis());
            boolean currentUserCollect = redisAritcleCollect.existCollectArticle(userId, aritcleId);
            log.error("首页redis查询文黯被收藏个数结束时间:"+System.currentTimeMillis());
            it.setCurrentUserCollect(currentUserCollect);

            if (StringUtils.hasLength(UnionIdContext.UNIONID.get())) {
                log.error("首页查询当前用户信息开始时间:"+System.currentTimeMillis());
                User currentUser = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
                log.error("首页查询当前用户信息结束时间:"+System.currentTimeMillis());
                long currentUserId = currentUser.getId();
                log.error("首页redis查询当前用户是否关注了该文黯的用户开始时间:"+System.currentTimeMillis());
                it.setHasFocusUser(redisUserFocus.existUserFocus(currentUserId, userId));
                log.error("首页redis查询当前用户是否关注了该文黯的用户结束时间:"+System.currentTimeMillis());
            }

            it.setCommentCount(10);
        });
        log.error("首页查询文黯结束时间:"+System.currentTimeMillis());
        return aritcleByPage != null ? Result.okData(aritcleByPage) : Result.fail("获取文黯失败！", null);
    }

    /**
     * 查询我点过赞和收藏的文黯
     *
     * @param count
     * @param page
     * @return
     */
    @PostMapping("/personal/support/collect/get")
    @RequireAuthentication
    public Result<Page<ArticleVo>> querySupportAndCollectArticles(int count, int page) {
        //合并点过赞的文黯id 和 收藏的文黯id

        User user = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        long userId = user.getId();
        Set<Object> articleIds = redisUserSupport.supportAndCollectArticleIds(userId);

        Page<ArticleVo> articleVoPage = articleService.querySupportAndCollectArticles(count, page, userId, articleIds);
        return articleVoPage != null ? Result.okData(articleVoPage) : Result.fail("获取文黯失败！", null);
    }

    /**
     * 查询我评论过的文黯
     *
     * @param count
     * @param page
     * @return
     */
    @PostMapping("/personal/comment/get")
    @RequireAuthentication
    public Result<Page<ArticleVo>> queryCommnetArticles(int count, int page) {
        //合并点过赞的文黯id 和 收藏的文黯id

        User user = userInfoContext.getUserInfoByUnionId(UnionIdContext.UNIONID.get());
        long userId = user.getId();
        Set<Object> articleIds = redisUserComment.memberUserCommentArticleId(userId);
        if (CollectionUtils.isEmpty(articleIds)) {
            return Result.okData(new Page<>(page, count, 0, true));
        }

        Page<ArticleVo> articleVoPage = articleService.queryCommentArticles(count, page, articleIds);
        return articleVoPage != null ? Result.okData(articleVoPage) : Result.fail("获取文黯失败！", null);
    }

    @PostMapping("/support/userlist")
    public Result<IPage<User>> userListSupportArticle(int current, int size, long articleId) {
        IPage<User> userListSupportArticle = userService.getUserListSupportArticle(current, size, articleId);
        return userListSupportArticle != null ? Result.okData(userListSupportArticle) : Result.fail("获取赞列表失败！");
    }

}
