package com.free.badmood.blackhole.web.service;

import com.free.badmood.blackhole.web.entity.TokenInfo;
import com.free.badmood.blackhole.web.entity.User;

public interface ITokenService {

    String SECRET = "1stonhdeb0uduckcolese2l.r2ebeuscoa";

    TokenInfo getToken(User user);
}
