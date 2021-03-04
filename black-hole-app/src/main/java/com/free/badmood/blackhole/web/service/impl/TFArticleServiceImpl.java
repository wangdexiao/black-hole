package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.TFArticle;
import com.free.badmood.blackhole.web.entity.TFArticleRes;
import com.free.badmood.blackhole.web.entity.TFUser;
import com.free.badmood.blackhole.web.mapper.TFArticleMapper;
import com.free.badmood.blackhole.web.mapper.TFArticleResMapper;
import com.free.badmood.blackhole.web.mapper.TFUserMapper;
import com.free.badmood.blackhole.web.service.ITFArticleService;
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
public class TFArticleServiceImpl extends ServiceImpl<TFArticleMapper, TFArticle> implements ITFArticleService {

    @Autowired
    private TFArticleMapper tfArticleMapper;

    @Autowired
    private TFArticleResMapper articleResMapper;


    @Autowired
    private TFUserMapper userMapper;


    public Page<TFArticle> getAritcleByPage(int count, int page){
        Page<TFArticle> tfArticlePage = tfArticleMapper.selectPage(new Page<>(page, count), Wrappers.emptyWrapper());
        //todo 临时这么写

        List<TFArticle> records = tfArticlePage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            TFArticle articleVo = records.get(i);
            //获取文黯相关的资源
            long id = articleVo.getId();
            List<TFArticleRes> articleRes = articleResMapper.selectList(Wrappers.<TFArticleRes>lambdaQuery().eq(TFArticleRes::getArticleId, id));
            List<String> photoArray = new ArrayList<>(articleRes.size());
            articleRes.forEach(it-> photoArray.add(it.getUrl()));
            articleVo.setPhotoArray(photoArray);

            //获取文黯发布者的用户信息
            long userId = articleVo.getUserId();
            TFUser tfUser = userMapper.selectById(userId);
            String nickName = tfUser.getNickName();
            String avatarUrl = tfUser.getAvatarUrl();
            articleVo.setNickName(nickName);
            articleVo.setAvatarUrl(avatarUrl);
        }
        return tfArticlePage;
    }
}
