package com.zjl.controller;

import com.alibaba.fastjson.JSONArray;
import com.zjl.entity.Integral;
import com.zjl.entity.Meta;
import com.zjl.entity.Orders;
import com.zjl.service.IntegralService;
import com.zjl.service.OrdersService;
import com.zjl.service.TableService;
import com.zjl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @Autowired
    IntegralService integralService;

    @Autowired
    TableService tableService;

    // 添加订单
    @RequestMapping(value = "addOrders")
    @ResponseBody
    public String addOrders(@RequestParam("userId") String user_id,
                            @RequestParam("foodList") String foodList,
                            @RequestParam("total_num") int total_num,
                            @RequestParam("total_price") int total_price,
                            @RequestParam("place_date") String place_date,
                            @RequestParam("method") int method,
                            @RequestParam("table_id") int table_id,
                            @RequestParam("integral") int integral,
                            @RequestParam("remarks") String remarks) {
        ordersService.addOrders(user_id, foodList, total_num, total_price, place_date, false, method, table_id, remarks);
        if (method == 1) {
            tableService.editTable(table_id, false);
        }
        // 查询是否存在积分
        Integral result = integralService.selectUserByUserId(user_id);
        // 如果没有就添加该用户和用户积分
        if (result == null) {
            integralService.addUserIntegral(user_id, integral);
        } else {
            // 否则则拿出用户积分加上传入的新积分重新存入数据库
            int integralPlus = result.getIntegral() + integral;
            integralService.updateUserIntegral(user_id, integralPlus);
        }
        return "订单提交成功";
    }

    // 根据用户 id获取订单
    @RequestMapping(value = "getOrder")
    @ResponseBody
    public List<Map<String, Object>> getOrder(@RequestParam("userId") String user_id) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Orders> ordersList = ordersService.getOrder(user_id);
        for (Orders orders : ordersList) {
            Map<String, Object> map = new HashMap<>();
            map.put("food_list", orders.getChildren());
            map.put("total_num", orders.getTotal_num());
            map.put("total_price", orders.getTotal_price());
            String ptime = orders.getPlace_time();
            // 设置时间的格式
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 将时间转换指定格式的日期
            String place_time = f.format(Long.valueOf(ptime));
            map.put("place_date", place_time);
            String ctime = orders.getComplete_time();
            if (ctime != null) {
                // 将时间转换指定格式的日期
                String complete_time = f.format(Long.valueOf(ctime));
                map.put("complete_time", complete_time);
            } else {
                map.put("complete_time", null);
            }
            map.put("state", orders.getState());
            map.put("method", orders.getMethod());
            map.put("remarks", orders.getRemarks());
            mapList.add(map);
        }
        return mapList;
    }

    // 获取全部订单
    @RequestMapping(value = "getAllOrder")
    @ResponseBody
    public Map<String, Object> getAllOrder(@RequestParam("query") String query,
                                           @RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        Meta meta = new Meta();
        List<Orders> ordersList = ordersService.getAllOrder(query, (pageNum - 1) * pageSize, pageSize);
        if (ordersList == null) {
            meta.setMsg("获取订单失败");
            meta.setStatus(500);
        } else {
            for (Orders orders : ordersList) {
                Map<String, Object> map1 = new HashMap<>();
                String children = orders.getChildren();
                map1.put("food", orders.getUser_id());
                map1.put("children", JSONArray.parseArray(children));
                map1.put("number", orders.getTotal_num());
                map1.put("price", orders.getTotal_price());
                String ptime = orders.getPlace_time();
                // 设置时间的格式
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 将时间转换指定格式的日期
                String place_time = f.format(Long.valueOf(ptime));
                map1.put("place_time", place_time);
                String ctime = orders.getComplete_time();
                if (ctime != null) {
                    // 将时间转换指定格式的日期
                    String complete_time = f.format(Long.valueOf(ctime));
                    map1.put("complete_time", complete_time);
                } else {
                    map1.put("complete_time", null);
                }
                map1.put("state", orders.getState());
                map1.put("method", orders.getMethod());
                map1.put("table_id", orders.getTable_id());
                mapList.add(map1);
            }
            meta.setMsg("获取订单成功");
            meta.setStatus(200);
            map.put("total", ordersService.getOrderTotal());
        }
        map.put("orderList", mapList);
        map.put("meta", meta);
        return map;
    }

    // 订单出餐
    @RequestMapping(value = "editOrderState")
    @ResponseBody
    public Map<String, Object> editOrderState(@RequestParam("user_id") String user_id,
                                              @RequestParam("place_time") String place_time,
                                              @RequestParam("state") Boolean state) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (!state) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 把输入时间字符串转化日期类型
            Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(place_time);
            // 转化为 10位时间戳
//        long timestamp = time.getTime() / 1000L;
            // 转化为 13位时间戳
            long timestamp1 = time.getTime();
            // 获取当前的时间
            Date date = new Date();
            long complete_time = date.getTime();
            ordersService.updateOrderState(user_id, (timestamp1 / 1000) + "000",
                    (complete_time / 1000) + "000", true);
            meta.setMsg("出餐成功");
            meta.setStatus(201);
        } else {
            meta.setMsg("已经出餐");
            meta.setStatus(405);
        }
        map.put("meta", meta);
        return map;
    }
}
