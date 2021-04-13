package com.free.badmood.blackhole.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends User{


    private String phoneEncryptedData;
    private String phoneIv;
}
