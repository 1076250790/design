package com.zjl.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Evaluation {
    // 菜品 id
    private int food_id;
    // 用户 id
    private String user_id;
    // 评论日期
    private String place_date;
    // 内容
    private String content;
    // 点赞数
    private int likes;
}
