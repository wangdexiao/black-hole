package com.free.badmood.blackhole.context;

import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserInfoContext {


    @Autowired
    private IUserService userService;

    private ConcurrentHashMap<String, User> userInfoMap = new ConcurrentHashMap<>();

    public boolean addUserInfo(User user){
        removeUserInfo(user.getOpenId());
        userInfoMap.put(user.getOpenId(), user);
        return true;
    }

    public boolean removeUserInfo(String openId){
        userInfoMap.remove(openId);
        return true;
    }

    public boolean existUserInfo(String openId){
        if(StringUtils.hasLength(openId)){

            User user = userInfoMap.get(openId);
            if(user == null ){
                user = userService.queryUserByOpenId(openId);
                if(user != null){
                    addUserInfo(user);
                    return true;
                }
            }else {
                if(user.getId() <=0){
                    user = userService.queryUserByOpenId(openId);
                    if(user != null){
                        addUserInfo(user);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * getUserInfoByOpenId
     * @param openId
     * @return
     */
    public User getUserInfoByOpenId(String openId){
        if(StringUtils.hasLength(openId)){
            return userInfoMap.get(openId);
        }else {
            return null;
        }

    }

}
