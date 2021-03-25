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

    //key 為unionID
    private ConcurrentHashMap<String, User> userInfoMap = new ConcurrentHashMap<>();

    public boolean addUserInfo(User user){
        if (StringUtils.hasLength(user.getUnionid())) {
            removeUserInfo(user.getUnionid());
            userInfoMap.put(user.getUnionid(), user);
        }
        return true;
    }

    public boolean removeUserInfo(String unionId){
        userInfoMap.remove(unionId);
        return true;
    }



    public boolean existUserInfo(String unionId){
        if(StringUtils.hasLength(unionId)){

            User user = userInfoMap.get(unionId);
            if(user == null ){
                user = userService.queryUserByUnionId(unionId);
                if(user != null){
                    addUserInfo(user);
                    return true;
                }
            }else {
                if(user.getId() <=0){
                    user = userService.queryUserByUnionId(unionId);
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
     * getUserInfoByUnionId
     * @param unionId
     * @return
     */
    public User getUserInfoByUnionId(String unionId){
        User user;
        if(StringUtils.hasLength(unionId)){
            user = userInfoMap.get(unionId);
            if (user == null){
                user = userService.queryUserByUnionId(unionId);
                addUserInfo(user);
            }
        }else {
            return null;
        }
        return user;
    }





    //key為openid
    private ConcurrentHashMap<String, User> openIdUserInfoMap = new ConcurrentHashMap<>();

    public boolean addUserInfoByOpenId(User user){
        if (StringUtils.hasLength(user.getOpenId())) {
            removeUserInfoByOpenId(user.getOpenId());
            openIdUserInfoMap.put(user.getUnionid(), user);
        }
        return true;
    }


    public boolean removeUserInfoByOpenId(String openId){
        openIdUserInfoMap.remove(openId);
        return true;
    }
}
