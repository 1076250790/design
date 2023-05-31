package com.zjl.entity;

import lombok.Data;

@Data
public class Reply {
    // 管理员名称
    private String admin_name;
    // 菜品 id
    private int food_id;
    // 用户 id
    private String user_id;
    // 用户评论的评论时间
    private String place_time;
    // 管理员回复评论的事件
    private String complete_time;
    // 回复的内容
    private String content;
}
