package com.zjl.service.impls;

import com.zjl.dao.OrdersDao;
import com.zjl.entity.Orders;
import com.zjl.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersDao ordersDao;

    @Override
    public void addOrders(String user_id, String foodList, int total_num, int total_price, String place_date, Boolean state, int method, int table_id, String remarks) {
        ordersDao.addOrders(user_id, foodList, total_num, total_price, place_date, state, method, table_id, remarks);
    }

    @Override
    public List<Orders> getOrder(String user_id) {
        return ordersDao.getOrder(user_id);
    }

    @Override
    public List<Orders> getAllOrder(String query, int pageNum, int pageSize) {
        return ordersDao.getAllOrder(query, pageNum, pageSize);
    }

    @Override
    public void updateOrderState(String user_id, String place_time, String complete_time, Boolean state) {
        ordersDao.updateOrderState(user_id, place_time, complete_time, state);
    }

    @Override
    public int getOrderTotal() {
        return ordersDao.getOrderTotal();
    }


}
