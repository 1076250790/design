package com.zjl.controller;

import com.alibaba.fastjson.JSONArray;
import com.zjl.entity.FirstMenu;
import com.zjl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping(value = "getMenu")
    @ResponseBody
    public String getMenu() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<FirstMenu> firstMenu = menuService.getFirstMenu();
        for (FirstMenu menu : firstMenu) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", menu.getFirst_id());
            map.put("autoName", menu.getAutoName());
            map.put("el_class", menu.getEl_class());
            System.out.println(menu.getEl_class());
            map.put("children", menuService.getSecondMenu(menu.getFirst_id()));
            mapList.add(map);
        }
        return JSONArray.toJSONString(mapList);
    }
}