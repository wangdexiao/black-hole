package com.free.badmood.blackhole.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserInfoEntity {


    /**
     * openId : osLHu4iaDHoyR-1pEn5Lt7pPO3WA
     * nickName : 王德晓
     * gender : 1
     * language : zh_CN
     * city :
     * province :
     * country : China
     * avatarUrl : https://thirdwx.qlogo.cn/mmopen/vi_32/6ejYgEC8IZIDkaRQfF3J31BQRoKwbN128poRyrNStkdEicGTY7lsQqwVMIj8FbECzx6c0wa5EquR91hHbC5hlZA/132
     * unionId : oSqjy6IiBa2TwpqagC1dk2HdZk2Q
     * watermark : {"timestamp":1614218347,"appid":"wx12b97453f3499c24"}
     */



    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("gender")
    private Integer gender;

    @JsonProperty("language")
    private String language;

    @JsonProperty("city")
    private String city;

    @JsonProperty("province")
    private String province;

    @JsonProperty("country")
    private String country;

    @JsonProperty("avatarUrl")
    private String avatarUrl;


    @JsonProperty("openId")
    private String openId;

    @JsonProperty("unionId")
    private String unionId;

    private String sessionKey;


}
