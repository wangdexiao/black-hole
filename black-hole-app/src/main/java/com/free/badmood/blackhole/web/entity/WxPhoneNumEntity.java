package com.free.badmood.blackhole.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WxPhoneNumEntity {


    /**
     * phoneNumber : 15965647774
     * purePhoneNumber : 15965647774
     * countryCode : 86
     * watermark : {"timestamp":1618283047,"appid":"wxdd7d7c923be115bf"}
     */

    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("purePhoneNumber")
    private String purePhoneNumber;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("watermark")
    private WatermarkDTO watermark;

    @NoArgsConstructor
    @Data
    public static class WatermarkDTO {
        /**
         * timestamp : 1618283047
         * appid : wxdd7d7c923be115bf
         */

        @JsonProperty("timestamp")
        private Integer timestamp;
        @JsonProperty("appid")
        private String appid;
    }
}
