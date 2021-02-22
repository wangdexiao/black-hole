package com.free.badmood.blackhole.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WxCreditInfoEntity {


    /**
     * session_key : f43t6ky+5LERsPIQKAWOMA==
     * openid : osLHu4iaDHoyR-1pEn5Lt7pPO3WA
     */

    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("openid")
    private String openid;


    /**
     * errcode : 40163
     * errmsg : code been used, hints: [ req_id: 3gKAxIore-HG8dTa ]
     */

    @JsonProperty("errcode")
    private int errcode;
    @JsonProperty("errmsg")
    private String errmsg;
}
