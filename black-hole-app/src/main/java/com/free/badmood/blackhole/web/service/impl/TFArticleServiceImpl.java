package com.free.badmood.blackhole.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.TFArticle;
import com.free.badmood.blackhole.web.mapper.TFArticleMapper;
import com.free.badmood.blackhole.web.service.ITFArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Page<TFArticle> getAritcleByPage(int count, int page){
        Page<TFArticle> tfArticlePage = tfArticleMapper.selectPage(new Page<>(page, count), Wrappers.emptyWrapper());
        return tfArticlePage;
    }
}
