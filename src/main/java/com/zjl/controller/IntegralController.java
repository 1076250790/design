package com.zjl.controller;

import com.zjl.entity.Meta;
import com.zjl.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IntegralController {
    @Autowired
    IntegralService integralService;

    @RequestMapping(value = "getIntegral")
    @ResponseBody
    public Map<String, Object> getIntegral(@RequestParam("userId") String user_id) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (user_id == null) {
            meta.setMsg("获取失败");
            meta.setStatus(500);
        } else {
            meta.setMsg("获取成功");
            meta.setStatus(200);
            Integer integral = integralService.selectIntegralByUserId(user_id);
            if (integral == null) {
                map.put("integral", 0);
            } else {
                map.put("integral", integral);
            }
        }
        map.put("meta", meta);
        return map;
    }
}
