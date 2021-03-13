package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.Article;
import com.free.badmood.blackhole.web.entity.ArticleRes;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.mapper.ArticleMapper;
import com.free.badmood.blackhole.web.mapper.ArticleResMapper;
import com.free.badmood.blackhole.web.mapper.UserMapper;
import com.free.badmood.blackhole.web.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-02
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleResMapper articleResMapper;


    @Autowired
    private UserMapper userMapper;


    public Page<Article> getAritcleByPage(int count, int page){
        Page<Article> tfArticlePage = articleMapper.selectPage(new Page<>(page, count), Wrappers.<Article>lambdaQuery().orderByDesc(Article::getUpdateTime));
        //todo 临时这么写

        List<Article> records = tfArticlePage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            Article articleVo = records.get(i);
            //获取文黯相关的资源
            long id = articleVo.getId();
            List<ArticleRes> articleRes = articleResMapper.selectList(Wrappers.<ArticleRes>lambdaQuery().eq(ArticleRes::getArticleId, id));
            List<String> photoArray = new ArrayList<>(articleRes.size());
            articleRes.forEach(it-> photoArray.add(it.getUrl()));
            articleVo.setPhotoArray(photoArray);

            //获取文黯发布者的用户信息
            long userId = articleVo.getUserId();
            User user = userMapper.selectById(userId);
            String nickName = user.getNickName();
            String avatarUrl = user.getAvatarUrl();
            articleVo.setNickName(nickName);
            articleVo.setAvatarUrl(avatarUrl);
        }
        return tfArticlePage;
    }
}