package com.zjl.controller;

import com.zjl.entity.Meta;
import com.zjl.entity.Table;
import com.zjl.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TableController {
    @Autowired
    TableService tableService;

    @RequestMapping(value = "getAllTable")
    @ResponseBody
    public Map<String, Object> getAllTable(@RequestParam("query") String query,
                                           @RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        List<Table> tableList = tableService.getAllTable(query, (pageNum - 1) * pageSize, pageSize);
        if (tableList != null) {
            int total = tableService.tableTotal();
            meta.setMsg("获取成功");
            meta.setStatus(200);
            map.put("tableList", tableList);
            map.put("total", total);
        } else {
            meta.setMsg("获取失败");
            meta.setStatus(500);
        }
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "getTableList")
    @ResponseBody
    public Map<String, Object> getTableList(@RequestParam("table_category") String table_category) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        List<Table> tableList = tableService.getTableList(table_category, true);
        if (tableList == null) {
            meta.setMsg("获取餐桌数据失败");
            meta.setStatus(500);
        } else {
            meta.setMsg("获取餐桌数据成功");
            meta.setStatus(200);
            map.put("tableList", tableList);
        }
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "getLastTableId")
    @ResponseBody
    public Map<String, Object> getLastTableId() {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        int table_id = Integer.parseInt(tableService.selectLastTableId());
        if (table_id == 0) {
            meta.setMsg("查询失败");
            meta.setStatus(500);
        } else {
            meta.setMsg("查询成功");
            meta.setStatus(200);
        }
        map.put("table_id", table_id);
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "addTable")
    @ResponseBody
    public Map<String, Object> addTable(@RequestBody HashMap<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        String table_id = data.get("table_id");
        String table_category = data.get("table_category");
        String person = data.get("person");
        Boolean state = Boolean.valueOf(data.get("state"));
        if (table_id.equals("")) {
            meta.setMsg("添加失败");
            meta.setStatus(500);
        } else {
            tableService.addTable(table_id, table_category, person, state);
            meta.setMsg("添加成功");
            meta.setStatus(200);
        }
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "deleteTable")
    @ResponseBody
    public Map<String, Object> deleteTable(@RequestParam("table_id") String table_id) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (table_id.equals("")) {
            meta.setMsg("删除失败");
            meta.setStatus(500);
        } else {
            tableService.deleteTable(table_id);
            meta.setMsg("删除成功");
            meta.setStatus(200);
        }
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "changeTableState")
    @ResponseBody
    public Map<String, Object> changeTableState(@RequestParam("table_id") int table_id,
                                                @RequestParam("state") Boolean state) {
        Map<String, Object> map = new HashMap<>();
        tableService.editTable(table_id, state);
        map.put("msg", "修改成功");
        return map;
    }
}
