package com.zjl.entity;

import lombok.Data;

@Data
public class Table {
    // 餐桌编号
    private Integer table_id;
    // 餐桌类别
    private String table_category;
    // 可容纳人数范围
    private String person;
    // 是否为空位
    private Boolean state;
}
