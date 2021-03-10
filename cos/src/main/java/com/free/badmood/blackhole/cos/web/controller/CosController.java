package com.free.badmood.blackhole.cos.web.controller;

import com.free.badmood.blackhole.base.entity.Result;
import com.free.badmood.blackhole.cos.CosStsClient;
import com.free.badmood.blackhole.cos.util.JsonUtils;
import com.free.badmood.blackhole.cos.web.entity.CredentialsEntity;
import net.sf.json.util.JSONUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
//import java.util.Properties;
import java.util.TreeMap;


@RestController
public class CosController {

    @Value("${cos.secret-id}")
    private String cosSecretId;

    @Value("${cos.secret-key}")
    private String cosSecretKey;

    @Value("${cos.bucket}")
    private String cosBucket;

    @Value("${cos.region}")
    private String cosRegion;



    @PostMapping("/getCosAuth")
    public Result<CredentialsEntity> getCosAuth(){

        CredentialsEntity credential =null;
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {
//            Properties properties = new Properties();
//            File configFile = new File("local.properties");
//            properties.load(new FileInputStream(configFile));

            // 云 api 密钥 SecretId
            config.put("secretId", cosSecretId);
            // 云 api 密钥 SecretKey
            config.put("secretKey", cosSecretKey);

//            if (properties.containsKey("https.proxyHost")) {
//                System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
//                System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));
//            }

            // 设置域名
            //config.put("host", "sts.internal.tencentcloudapi.com");

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);

            // 换成你的 bucket
            config.put("bucket", cosBucket);
            // 换成 bucket 所在地区
            config.put("region", cosRegion);

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefix", "*");
            // 可以通过 allowPrefixes 指定前缀数组
//            config.put("allowPrefixes", new String[] {
//                    "exampleobject",
//                    "exampleobject2"
//            });

            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    "*"
                    // 简单上传
//                    "name/cos:PutObject",
//                    "name/cos:PostObject",
//                    // 分片上传
//                    "name/cos:InitiateMultipartUpload",
//                    //分块上传：List 进行中的分块上传
//                    "name/cos:ListMultipartUploads",
//                    //分块上传：List 已上传分块操作
//                    "name/cos:ListParts",
//                    //分块上传：上传分块块操作
//                    "name/cos:UploadPart",
//                    //分块上传：完成所有分块上传操作
//                    "name/cos:CompleteMultipartUpload",
//                    //取消分块上传操作
//                    "name/cos:AbortMultipartUpload"
            };
            config.put("allowActions", allowActions);

            JSONObject credentialJson = CosStsClient.getCredential(config);
            credential = JsonUtils.jsonToPojo(credentialJson.toString(),CredentialsEntity.class);

        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException("no valid secret !");
        }


        return credential == null ? Result.fail(-1,"获取cos凭证失败") : Result.okData(credential);
    }
}
