package com.zjl.entity;

import lombok.Data;

@Data
public class Admin {
    // id
    private int admin_id;
    // 名字
    private String admin_name;
    // 密码
    private String admin_password;
    // 职位
    private String scope;
    // 状态
    private Boolean state;
    // 权限
    private int power;
}
