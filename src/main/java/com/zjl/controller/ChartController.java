package com.zjl.controller;

import com.alibaba.fastjson.JSONArray;
import com.zjl.entity.Chart;
import com.zjl.entity.Meta;
import com.zjl.entity.Orders;
import com.zjl.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ChartController {
    @Autowired
    ChartService chartService;

    // 获取全部点餐菜品
    @RequestMapping(value = "getAllOrderFood")
    @ResponseBody
    public Map<String, Object> getAllOrder(@RequestParam("date") String date) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        Meta meta = new Meta();
        List<Orders> chartsList = chartService.getAllOrderFood(date);
        for (Orders orders: chartsList) {
            Map<String, Object> map1 = new HashMap<>();
            String foodList = orders.getChildren();
            int method = orders.getMethod();
            map1.put("foodList", JSONArray.parseArray(foodList));
            map1.put("method", method);
            mapList.add(map1);
        }
        meta.setMsg("获取成功");
        meta.setStatus(200);
        map.put("meta", meta);
        map.put("orderFood", mapList);
        return map;
    }
}
