package com.free.badmood.blackhole.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.free.badmood.blackhole.constant.CommonConstant;
import com.free.badmood.blackhole.context.LoginStateContext;
import com.free.badmood.blackhole.context.MyContext;
import com.free.badmood.blackhole.web.entity.TFUser;
import com.free.badmood.blackhole.web.entity.WxCreditInfoEntity;
import com.free.badmood.blackhole.web.service.ITFUserService;
import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.base.utils.wxdecode.WXCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;

import com.free.badmood.blackhole.base.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wadexi
 * @since 2021-02-26
 */

@Slf4j
@RestController
public class TFUserController extends BaseController {


    @Autowired
    private Environment environment;

    @Autowired
    private LoginStateContext loginStateContext;


    @Autowired
    private ITFUserService tfUserService;

    /**
     * 解密用户信息（通过session_key（不能放到前端））
     * @param iv
     * @param rawData 加密的用户数据
     * @return
     */
    @PostMapping(value = "/userinfo")
    public Result<TFUser> decodeUserInfo(String iv, String rawData){
        String wxAppId = environment.getProperty(CommonConstant.WX_APPID);
        String openid = MyContext.OPENID.get();
        WxCreditInfoEntity wxCreditInfoByOpenId = loginStateContext.getWxCreditInfoByOpenId(openid);
        String decrypt = WXCore.decrypt(wxAppId, rawData, wxCreditInfoByOpenId.getSessionKey(), iv);
        log.info("解密得到的用户信息为：" + decrypt);
        TFUser userInfo = JSONObject.parseObject(decrypt, TFUser.class);
        userInfo.setSessionKey(wxCreditInfoByOpenId.getSessionKey());
        TFUser dbUser = tfUserService.queryUserByUnionId(userInfo.getUnionid());
        //已经存在该用户信息
        if (dbUser != null){
            userInfo.setId(dbUser.getId());
        }
        //添加或更新用户信息
        boolean saved = tfUserService.saveOrUpdate(userInfo);



        return saved ? Result.okData(userInfo) : Result.fail("解密，磁盘化过程失败！",userInfo);
    }
}
