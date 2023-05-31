package com.zjl.entity;

import lombok.Data;

@Data
public class FoodIntro {
    // 菜品 id
    private int food_id;
    // 原料
    private String yuanliao;
    // 份量
    private String fenliang;
    // 荤素
    private String hunsu;
    // 辅料
    private String fuliao;
    // 口味
    private String kouwei;
}
