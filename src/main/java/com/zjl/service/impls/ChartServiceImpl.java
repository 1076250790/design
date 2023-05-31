package com.zjl.service.impls;

import com.zjl.dao.ChartDao;
import com.zjl.entity.Chart;
import com.zjl.entity.Orders;
import com.zjl.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    ChartDao chartDao;

    @Override
    public List<Orders> getAllOrderFood(String date) {
        return chartDao.getAllOrderFood(date);
    }
}
