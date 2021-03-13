package com.free.badmood.blackhole.context;

import lombok.Data;


public class OpenIdContext {


    public static final ThreadLocal<String> OPENID = new ThreadLocal<>();


}
