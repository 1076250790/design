package com.zjl.entity;

import lombok.Data;

@Data
public class Food {
    // 菜品 id
    private int food_id;
    // 菜品名
    private String food_name;
    // 图片
    private String food_pic;
    // 价格
    private double food_price;
    // 菜品分类 id
    private int type_id;
    // 菜品分类名
    private String type_name;
}
