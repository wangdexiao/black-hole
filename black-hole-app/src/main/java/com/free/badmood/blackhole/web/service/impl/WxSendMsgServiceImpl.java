package com.free.badmood.blackhole.web.service.impl;

import com.free.badmood.blackhole.cos.util.JsonUtils;
import com.free.badmood.blackhole.web.entity.WxAccessTokenEntity;
import com.free.badmood.blackhole.web.entity.WxNotifyMsgEntity;
import com.free.badmood.blackhole.web.entity.WxSendMsgResEntity;
import com.free.badmood.blackhole.web.service.IWxSendMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class WxSendMsgServiceImpl implements IWxSendMsgService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Override
    public boolean sendMsg() {
        String wxAppid = env.getProperty("WX_APPID");
        String wxSecret = env.getProperty("WX_APPSECRET");

        WxAccessTokenEntity accessTokenEntity = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential" +
                "&appid=" + wxAppid +
                "&secret=" + wxSecret, WxAccessTokenEntity.class);

        String accessToken = accessTokenEntity.getAccessToken();



        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;


        WxNotifyMsgEntity wxNotifyMsgEntity = new WxNotifyMsgEntity();
        wxNotifyMsgEntity.setTouser("op6xP5E02ekPJxU0phsbZwezzuaw");//todo 动态获取用户openid
        wxNotifyMsgEntity.setTemplateId("CthRNu4x3wWroo8xZtopXP-jNHm0wQnXS_pn9nKXnqA"); //todo 动态选在模板id
        wxNotifyMsgEntity.setPage("/message");
        WxNotifyMsgEntity.DataDTO dataDTO = new WxNotifyMsgEntity.DataDTO();
        WxNotifyMsgEntity.DataDTO.Name1DTO name1DTO = new WxNotifyMsgEntity.DataDTO.Name1DTO();
        name1DTO.setValue("我的名称");
        WxNotifyMsgEntity.DataDTO.Date2DTO date2DTO = new WxNotifyMsgEntity.DataDTO.Date2DTO();
        date2DTO.setValue("2021-04-23 15:30:00");
        WxNotifyMsgEntity.DataDTO.Thing3DTO thing3DTO = new WxNotifyMsgEntity.DataDTO.Thing3DTO();
        thing3DTO.setValue("点赞成功");
        WxNotifyMsgEntity.DataDTO.CharacterString4DTO characterString4DTO = new WxNotifyMsgEntity.DataDTO.CharacterString4DTO();
        characterString4DTO.setValue(8888);
        dataDTO.setName1(name1DTO);
        dataDTO.setDate2(date2DTO);
        dataDTO.setThing3(thing3DTO);
        dataDTO.setCharacterString4(characterString4DTO);


        wxNotifyMsgEntity.setData(dataDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(JsonUtils.objectToJson(wxNotifyMsgEntity), headers);


        ResponseEntity<WxSendMsgResEntity> responseEntity =  restTemplate.postForEntity(url, request, WxSendMsgResEntity.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if(statusCodeValue == 200){
            WxSendMsgResEntity body = responseEntity.getBody();
            if(body.getErrcode() == 0){
                long msgId = body.getMsgid();
                log.error("msgid为：" + msgId);
                return true;
            }else {
                log.error("发送消息通知失败");
                return false;
            }
        }else {
            log.error("发送消息失败：" );
        }


        return false;
    }
}
