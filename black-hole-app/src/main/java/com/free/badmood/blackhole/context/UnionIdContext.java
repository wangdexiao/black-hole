package com.free.badmood.blackhole.context;

import lombok.Data;


public class UnionIdContext {


    public static final ThreadLocal<String> UNIONID = new ThreadLocal<>();


}
