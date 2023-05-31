package com.zjl.entity;

import lombok.Data;

@Data
public class SecondMenu {
    // 第二菜单
    // 所属第一菜单 id
    private int first_id;
    // id
    private int second_id;
    // 菜单名
    private String autoName;
    // 图标
    private String el_class;
    // 路径
    private String path;
}
