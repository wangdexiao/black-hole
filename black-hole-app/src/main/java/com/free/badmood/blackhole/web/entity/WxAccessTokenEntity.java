package com.free.badmood.blackhole.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxAccessTokenEntity {


    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     */

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("errcode")
    private int errcode = 0;

    @JsonProperty("errmsg")
    private int errmsg = 0;


}
