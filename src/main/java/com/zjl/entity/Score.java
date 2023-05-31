package com.zjl.entity;

import lombok.Data;

@Data
public class Score {
    // 用户 id
    private String user_id;
    // 订单的下单时间
    private String place_time;
    // 评分
    private double score;
}
