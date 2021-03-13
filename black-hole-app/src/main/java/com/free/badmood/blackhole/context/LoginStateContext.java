package com.free.badmood.blackhole.context;

import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.entity.WxCreditInfoEntity;
import com.free.badmood.blackhole.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginStateContext {


    @Autowired
    private IUserService userService;

    private ConcurrentHashMap<String, WxCreditInfoEntity> loinStateMap = new ConcurrentHashMap<>();

    public boolean addLoginState(WxCreditInfoEntity wxCreditInfoEntity){
        removeLoginState(wxCreditInfoEntity.getOpenid());
        loinStateMap.put(wxCreditInfoEntity.getOpenid(), wxCreditInfoEntity);
        return true;
    }

    public boolean removeLoginState(String openId){
        loinStateMap.remove(openId);
        return true;
    }

    public boolean existLoginState(String openId){
        if(StringUtils.hasLength(openId)){

            boolean existUser = loinStateMap.containsKey(openId);
            if(!existUser){
                User user = userService.queryUserByOpenId(openId);
                if(user != null){
                    WxCreditInfoEntity creditInfoEntity = new WxCreditInfoEntity();
                    creditInfoEntity.setOpenid(user.getOpenId());
                    creditInfoEntity.setSessionKey(user.getSessionKey());
                    addLoginState(creditInfoEntity);
                    return true;
                }
            }else {
                return true;
            }
        }
        return false;
    }

    public WxCreditInfoEntity getWxCreditInfoByOpenId(String openId){
        if(StringUtils.hasLength(openId)){
            return loinStateMap.get(openId);
        }else {
            return null;
        }

    }

}
