package com.free.badmood.blackhole.cos.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CredentialsEntity {


    /**
     * credentials : {"tmpSecretId":"AKID_-89BXX8YdidSSQ-vNagr_ekAsBCowBz0NaHkbqqpiBDoYqVz0e1FIdPoP7JcPrA","tmpSecretKey":"WrL+5sbJvXnJl3l6QUbe7SBihG2IBfBo6H8xx7djgnU=","sessionToken":"FIyJjAQPsX3gG2fR4R0sYxenlTT4eLzac2a5efed711f7cf2331203f991c907a2rNPvvdIWccJIp7pv2RaGEmIO2Tl-vDid8CSpePn8qyfp8CT-vkZH0OBREj9MkuIIi4MLwV-fyZtbxZSWORSl-V415jP-FDOCGCwpz7I2_FS2p5ZXJ0vPvd8rmpZ8onbBHHGb5Oi9uqWmHTturOB9dwZIZ4AK_sNgJnI5LXWod5XtKTdmF-dNA7sVl7KgH12aAfHH0B8nMO2JUNnAEfQD2F_toIY55Sx-Lpu-pcJw4dEZE4AOAq5w-X9Asc-aZWbPqoS6CedO5EaPKRG9pjKPnd-gZFB-qcfJkxkSHjD39pe1fLU3f_tP2KW2dc6Lds0PE-MjVqD9FwjwquPrSoQTc4x0bRYx6e70tvzStlmQy2A7D1BONPNNNbZjHAoa4qMm6ZnGbpdmb8Tall8XEcdWYnb2zmapy5nkXciRHYlFu2MdqWqFdeZi3xQ51_Kly3Y2FXjaDfrtiVVMmsyjzniFqb_WzTvRuIuYdc4qxrZIo_jEeqjP-s9eLRv3A33KKsk_REvGR7l6vAD-dbhKbLckRT1JIh4p_qesEtEzMsE-B7SCFTwWuVqc3NqRiN4rf9zSkFsuKTqXLZNI94rOc_ysfQ"}
     * requestId : 6f247c0a-6909-410f-8885-60cd350b8572
     * expiration : 2021-03-10T02:40:24Z
     * startTime : 1615342224
     * expiredTime : 1615344024
     */

    @JsonProperty("credentials")
    private CredentialsDTO credentials;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("expiration")
    private String expiration;
    @JsonProperty("startTime")
    private Integer startTime;
    @JsonProperty("expiredTime")
    private Integer expiredTime;

    @NoArgsConstructor
    @Data
    public static class CredentialsDTO {
        /**
         * tmpSecretId : AKID_-89BXX8YdidSSQ-vNagr_ekAsBCowBz0NaHkbqqpiBDoYqVz0e1FIdPoP7JcPrA
         * tmpSecretKey : WrL+5sbJvXnJl3l6QUbe7SBihG2IBfBo6H8xx7djgnU=
         * sessionToken : FIyJjAQPsX3gG2fR4R0sYxenlTT4eLzac2a5efed711f7cf2331203f991c907a2rNPvvdIWccJIp7pv2RaGEmIO2Tl-vDid8CSpePn8qyfp8CT-vkZH0OBREj9MkuIIi4MLwV-fyZtbxZSWORSl-V415jP-FDOCGCwpz7I2_FS2p5ZXJ0vPvd8rmpZ8onbBHHGb5Oi9uqWmHTturOB9dwZIZ4AK_sNgJnI5LXWod5XtKTdmF-dNA7sVl7KgH12aAfHH0B8nMO2JUNnAEfQD2F_toIY55Sx-Lpu-pcJw4dEZE4AOAq5w-X9Asc-aZWbPqoS6CedO5EaPKRG9pjKPnd-gZFB-qcfJkxkSHjD39pe1fLU3f_tP2KW2dc6Lds0PE-MjVqD9FwjwquPrSoQTc4x0bRYx6e70tvzStlmQy2A7D1BONPNNNbZjHAoa4qMm6ZnGbpdmb8Tall8XEcdWYnb2zmapy5nkXciRHYlFu2MdqWqFdeZi3xQ51_Kly3Y2FXjaDfrtiVVMmsyjzniFqb_WzTvRuIuYdc4qxrZIo_jEeqjP-s9eLRv3A33KKsk_REvGR7l6vAD-dbhKbLckRT1JIh4p_qesEtEzMsE-B7SCFTwWuVqc3NqRiN4rf9zSkFsuKTqXLZNI94rOc_ysfQ
         */

        @JsonProperty("tmpSecretId")
        private String tmpSecretId;
        @JsonProperty("tmpSecretKey")
        private String tmpSecretKey;
        @JsonProperty("sessionToken")
        private String sessionToken;
    }
}
