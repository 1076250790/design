package com.zjl.service;

import com.zjl.entity.Orders;

import java.util.List;

public interface OrdersService {
    // 增加订单
    public void addOrders(String user_id, String foodList, int total_num, int total_price, String place_date, Boolean state, int method, int table_id, String remarks);
    // 获取订单
    public List<Orders> getOrder(String user_id);
    // 获取全部订单
    public List<Orders> getAllOrder(String query, int pageNum, int pageSize);
    // 订单出餐
    public void updateOrderState(String user_id, String place_time, String complete_time, Boolean state);
    // 订单总数
    public int getOrderTotal();
}
