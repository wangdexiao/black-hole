package com.free.badmood.blackhole.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.free.badmood.blackhole.web.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wadexi
 * @since 2021-02-26
 */
public interface IUserService extends IService<User> {





    default User queryUserByUnionId(String unionId){
        return null;
    }


    default User queryUserByOpenId(String openId){
        return null;
    }


    IPage<User> getUserListSupportArticle(int current,int size,long articleId);

}
