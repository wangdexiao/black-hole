package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.TFArticle;
import com.free.badmood.blackhole.web.entity.TFArticleRes;
import com.free.badmood.blackhole.web.mapper.TFArticleMapper;
import com.free.badmood.blackhole.web.mapper.TFArticleResMapper;
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


    public Page<TFArticle> getAritcleByPage(int count, int page){
        Page<TFArticle> tfArticlePage = tfArticleMapper.selectPage(new Page<>(page, count), Wrappers.emptyWrapper());
        //todo 临时这么写

        List<TFArticle> records = tfArticlePage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            TFArticle tfArticle = records.get(i);
            long id = tfArticle.getId();
            List<TFArticleRes> articleRes = articleResMapper.selectList(Wrappers.<TFArticleRes>lambdaQuery().eq(TFArticleRes::getArticleId, id));
            List<String> photoArray = new ArrayList<>(articleRes.size());
            articleRes.forEach(it-> photoArray.add(it.getUrl()));
            tfArticle.setPhotoArray(photoArray);
        }
        return tfArticlePage;
    }
}
