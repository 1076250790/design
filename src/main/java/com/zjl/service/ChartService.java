package com.zjl.service;

import com.zjl.entity.Chart;
import com.zjl.entity.Orders;

import java.util.List;

public interface ChartService {
    // 获取订单内的菜色列表
    public List<Orders> getAllOrderFood(String date);
}
