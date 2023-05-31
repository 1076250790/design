package com.zjl.entity;

import lombok.Data;

@Data
public class Orders {
    // 用户 id
    private String user_id;
    // 菜品列表
    private String children;
    // 总数
    private int total_num;
    // 总价
    private int total_price;
    // 下单时间
    private String place_time;
    // 出餐时间
    private String complete_time;
    // 是否出餐
    private Boolean state;
    // 就餐方式，店内就餐：1；打包带走：2
    private int method;
    // 桌号
    private int table_id;
    // 备注
    private String remarks;
}
