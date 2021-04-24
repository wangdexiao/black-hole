package com.free.badmood.blackhole.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxSendMsgResEntity {


    /**
     * errcode : 0
     * errmsg : ok
     * msgid : 1838289466063437832
     */

    @JsonProperty("errcode")
    private int errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("msgid")
    private long msgid;

}
