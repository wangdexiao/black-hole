package com.free.badmood.blackhole.web.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.free.badmood.blackhole.web.entity.TokenInfo;
import com.free.badmood.blackhole.web.entity.User;
import com.free.badmood.blackhole.web.service.ITokenService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
public class TokenServiceImpl implements ITokenService {



    public TokenInfo getToken(User user) {
        Date start = new Date();
        //一小时有效时间
        long expiresIn = 30L * 24 * 3600 * 1000;
//        long expiresIn =   5 * 1000;
        long currentTime = System.currentTimeMillis() + expiresIn;
        Date end = new Date(currentTime);
        String token;
        //以用户的云信id做唯一标识
        token = JWT.create()
                .withJWTId(UUID.randomUUID().toString())//随机生成一个id
                .withIssuer("blacksoundhole.com")//发行者
                .withAudience(user.getUnionid())//观众（不知道啥意思）
                .withIssuedAt(start)//发行时间
                .withExpiresAt(end)//过期时间
                .sign(Algorithm.HMAC256(SECRET));
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setLoginName(user.getUnionid());
        tokenInfo.setToken(token);
        tokenInfo.setExpiresIn(expiresIn);
        tokenInfo.setTokenUpdateTime(start.getTime());
        return tokenInfo;
    }

    public static void main(String[] args) throws InterruptedException {
        TokenServiceImpl tokenService = new TokenServiceImpl();
        //生成token 解密token
        User user = new User();
        user.setUnionid("osLHu4iaDHoyR-1pEn5Lt7pPO3WA");
        TokenInfo token = tokenService.getToken(user);
        System.out.println(token.toString());


        String userId = JWT.decode(token.getToken()).getAudience().get(0);
        System.out.println("userId:" + userId);

        Thread.sleep(2000);

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token.getToken());
            System.out.println("第一次验证通过");
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new RuntimeException("401");
        }

        Thread.sleep(4000);
        try {
            jwtVerifier.verify(token.getToken());
            System.out.println("第2次验证通过");
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }
    }

}
