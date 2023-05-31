package com.zjl.entity;

import lombok.Data;

@Data
public class FoodType {
    // 分类 id
    private int type_id;
    // 分类名
    private String type_name;
    // 分类拼音（当作前端的 id）
    private String type_pinyin;
}
