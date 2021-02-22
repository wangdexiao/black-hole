package com.free.badmood.blackhole.context;

import com.free.badmood.blackhole.web.entity.WxCreditInfoEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginStateContext {

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
            return loinStateMap.containsKey(openId);
        }else {
            return false;
        }

    }
}
