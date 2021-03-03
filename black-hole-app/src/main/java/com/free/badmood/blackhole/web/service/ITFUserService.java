package com.free.badmood.blackhole.web.service;

import com.free.badmood.blackhole.web.entity.TFUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wadexi
 * @since 2021-02-26
 */
public interface ITFUserService extends IService<TFUser> {





    default TFUser queryUserByUnionId(String unionId){
        return null;
    }


    default TFUser queryUserByOpenId(String openId){
        return null;
    }

}
