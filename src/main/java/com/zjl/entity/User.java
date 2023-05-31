package com.zjl.entity;

import lombok.Data;

@Data
public class User {
    // id
    private String user_id;
    // 头像
    private String head_portrait;
    // 用户名
    private String username;
    // 性别
    private int gender;
    // 密码
    private String password;
    // 积分
    private int integral;
    // 是否是会员
    private int vip;
    // 登录方式
    private String login_method;
}
