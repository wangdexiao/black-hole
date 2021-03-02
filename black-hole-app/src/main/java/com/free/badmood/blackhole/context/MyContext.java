package com.free.badmood.blackhole.context;

import lombok.Data;


public class MyContext {


    public static final ThreadLocal<String> OPENID = new ThreadLocal<>();


}
