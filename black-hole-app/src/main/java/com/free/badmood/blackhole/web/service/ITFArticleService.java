package com.free.badmood.blackhole.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.free.badmood.blackhole.web.entity.TFArticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wadexi
 * @since 2021-03-02
 */
public interface ITFArticleService extends IService<TFArticle> {


    default Page<TFArticle> getAritcleByPage(int count, int page){
        return null;
    }

}
