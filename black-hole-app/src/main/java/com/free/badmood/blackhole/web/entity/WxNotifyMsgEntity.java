package com.free.badmood.blackhole.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxNotifyMsgEntity {


    /**
     * touser : op6xP5E02ekPJxU0phsbZwezzuaw
     * template_id : CthRNu4x3wWroo8xZtopXP-jNHm0wQnXS_pn9nKXnqA
     * page : /message
     * data : {"name1":{"value":"我的名称"},"date2":{"value":"2020年01月01日 12:00:00"},"thing3":{"value":"点赞成功"},"character_string4":{"value":8888}}
     */

    @JsonProperty("touser")
    private String touser;
    @JsonProperty("template_id")
    private String templateId;
    @JsonProperty("page")
    private String page;
    @JsonProperty("data")
    private DataDTO data;



    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataDTO {
        /**
         * name1 : {"value":"我的名称"}
         * date2 : {"value":"2020年01月01日 12:00:00"}
         * thing3 : {"value":"点赞成功"}
         * character_string4 : {"value":8888}
         */

        @JsonProperty("name1")
        private Name1DTO name1;
        @JsonProperty("date2")
        private Date2DTO date2;
        @JsonProperty("thing3")
        private Thing3DTO thing3;
        @JsonProperty("character_string4")
        private CharacterString4DTO characterString4;



        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Name1DTO {
            /**
             * value : 我的名称
             */

            @JsonProperty("value")
            private String value;


        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Date2DTO {
            /**
             * value : 2020年01月01日 12:00:00
             */

            @JsonProperty("value")
            private String value;


        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Thing3DTO {
            /**
             * value : 点赞成功
             */

            @JsonProperty("value")
            private String value;


        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CharacterString4DTO {
            /**
             * value : 8888
             */

            @JsonProperty("value")
            private int value;


        }
    }
}
