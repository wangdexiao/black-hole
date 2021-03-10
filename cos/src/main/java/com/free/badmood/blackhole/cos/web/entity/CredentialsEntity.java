package com.free.badmood.blackhole.cos.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class CredentialsEntity {


    /**
     * credentials : {"tmpSecretId":"AKIDgWh0KWsg0ueKpyGeqIcMzBe8nPTqqbkuzn5ZdQr6So3EgwKs77-Dl_TsoiG5BaFN","tmpSecretKey":"vRWtP/BIxXOFazOSpAq0NNdzjCXptLuq1Hl8HgEB/ho=","sessionToken":"80mPg2ncpbLONWdYLRRuRhqI6Qvp3ylacc39de5420b73e3a83704dbf0abe4d26wbVk7qNFRlnsiJAUj1vjTTmgSOp4LB_xBPdwRqb9bCrBZedGx5ymyhZ5XzP938SwXqRlux2AgdoYlulaG1d4M97X4YHVo4ryG2UbuJG_8vUu0i1VbJbfbxmAHm2Udj49HNvofil-RnmBF8eqSlfeqEFq97Y94dO_M5pfCKimj4MfuJNSce79GI6wFAmpQQL9aiSkDceGfBFDuhoKIcgvVvd3vOqKJrNas4Ima5MrZY69Og76ZUA2YTTMNHvEt41OjbeQnFe0Leszjq3dJ_Xa5buBOf0z09bwbioHGCV9JfXYO8giHI60ZM8UGvW6rb8Jz7xpFDeCobAqKi8y5NubVTKI_ZLQQdForXd-swkjRxXNU6Y3O_lgiZdui0sx0PKf_YLTpCW2RGuInUkRTOAOrO-2gaNBjWHF3T7HBjav_8TpkiHk-viRDX3xc1rI0O7ppWFdvLcJMYVhmK_l4Tw5t7LEYC6m5eXmtXD2mYMPB66JfxzuYX82GbtP-9-j9obs0y5sCs9lcxI6M2Qii5tqdXHDoj5Sk2pjiPnC6EaneeCZ2o4PJmibnnRc9yY2fR9v"}
     * requestId : 2287c30d-ecc2-47e3-9323-ada30dbc61b4
     * expiration : 2021-03-10T07:03:17Z
     * startTime : 1615357997
     * expiredTime : 1615359797
     */

    @JsonProperty("credentials")
    private CredentialsDTO credentials;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("expiration")
    private String expiration;
    @JsonProperty("startTime")
    private int startTime;
    @JsonProperty("expiredTime")
    private int expiredTime;

    public CredentialsDTO getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsDTO credentials) {
        this.credentials = credentials;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CredentialsDTO {
        /**
         * tmpSecretId : AKIDgWh0KWsg0ueKpyGeqIcMzBe8nPTqqbkuzn5ZdQr6So3EgwKs77-Dl_TsoiG5BaFN
         * tmpSecretKey : vRWtP/BIxXOFazOSpAq0NNdzjCXptLuq1Hl8HgEB/ho=
         * sessionToken : 80mPg2ncpbLONWdYLRRuRhqI6Qvp3ylacc39de5420b73e3a83704dbf0abe4d26wbVk7qNFRlnsiJAUj1vjTTmgSOp4LB_xBPdwRqb9bCrBZedGx5ymyhZ5XzP938SwXqRlux2AgdoYlulaG1d4M97X4YHVo4ryG2UbuJG_8vUu0i1VbJbfbxmAHm2Udj49HNvofil-RnmBF8eqSlfeqEFq97Y94dO_M5pfCKimj4MfuJNSce79GI6wFAmpQQL9aiSkDceGfBFDuhoKIcgvVvd3vOqKJrNas4Ima5MrZY69Og76ZUA2YTTMNHvEt41OjbeQnFe0Leszjq3dJ_Xa5buBOf0z09bwbioHGCV9JfXYO8giHI60ZM8UGvW6rb8Jz7xpFDeCobAqKi8y5NubVTKI_ZLQQdForXd-swkjRxXNU6Y3O_lgiZdui0sx0PKf_YLTpCW2RGuInUkRTOAOrO-2gaNBjWHF3T7HBjav_8TpkiHk-viRDX3xc1rI0O7ppWFdvLcJMYVhmK_l4Tw5t7LEYC6m5eXmtXD2mYMPB66JfxzuYX82GbtP-9-j9obs0y5sCs9lcxI6M2Qii5tqdXHDoj5Sk2pjiPnC6EaneeCZ2o4PJmibnnRc9yY2fR9v
         */

        @JsonProperty("tmpSecretId")
        private String tmpSecretId;
        @JsonProperty("tmpSecretKey")
        private String tmpSecretKey;
        @JsonProperty("sessionToken")
        private String sessionToken;

        public String getTmpSecretId() {
            return tmpSecretId;
        }

        public void setTmpSecretId(String tmpSecretId) {
            this.tmpSecretId = tmpSecretId;
        }

        public String getTmpSecretKey() {
            return tmpSecretKey;
        }

        public void setTmpSecretKey(String tmpSecretKey) {
            this.tmpSecretKey = tmpSecretKey;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
        }
    }
}
