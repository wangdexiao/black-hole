package com.free.badmood.blackhole.web.service;

import com.free.badmood.blackhole.web.entity.TokenInfo;
import com.free.badmood.blackhole.web.entity.User;

public interface ITokenService {

    TokenInfo getToken(User user);
}
