package com.zjl.controller;

import com.zjl.entity.Admin;
import com.zjl.entity.Meta;
import com.zjl.service.AdminService;
import com.zjl.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    // 添加用户
    @RequestMapping(value = "addAdmin")
    @ResponseBody
    public Map<String, Object> addAdmin(@RequestBody HashMap<String, String> data) {
        // 新建 Map容器
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        Admin admin = new Admin();
        admin.setAdmin_id(Integer.parseInt(data.get("adminId")));
        admin.setAdmin_name(data.get("adminName"));
        admin.setAdmin_password(data.get("adminPassword"));
        admin.setScope(data.get("scope"));
        admin.setState(false);
        if (data.get("scope").equals("管理员")) {
            admin.setPower(0);
        } else {
            admin.setPower(1);
        }
        adminService.addAdmin(admin);
        meta.setMsg("管理员添加成功");
        meta.setStatus(201);
        map.put("meta", meta);
        return map;
    }

    // 修改管理员信息
    @RequestMapping(value = "editAdmin")
    @ResponseBody
    public Map<String, Object> editAdmin(@RequestBody HashMap<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        Admin admin = new Admin();
        admin.setAdmin_id(Integer.parseInt(data.get("admin_id")));
        admin.setAdmin_name(data.get("admin_name"));
        admin.setAdmin_password(data.get("admin_password"));
        admin.setScope(data.get("scope"));
        admin.setState(false);
        if (data.get("scope").equals("管理员")) {
            admin.setPower(0);
        } else {
            admin.setPower(1);
        }
        adminService.editAdmin(admin);
        meta.setMsg("管理员信息修改成功");
        meta.setStatus(201);
        map.put("meta", meta);
        return map;
    }

    // 删除管理员账号
    @RequestMapping(value = "removeAdminById")
    @ResponseBody
    public Map<String, Object> removeAdminById(@RequestParam("admin_id") int admin_id) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        String str = admin_id + "";
        if (str.equals("")) {
            meta.setMsg("删除管理员账号失败");
            meta.setStatus(500);
        }
        else {
            adminService.removeAdmin(admin_id);
            meta.setMsg("删除管理员账号成功");
            meta.setStatus(200);
        }
        map.put("admin", null);
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "updateAdmin")
    @ResponseBody
    public Map<String, Object> updateAdmin(@RequestParam("admin_id") int admin_id) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        String str = admin_id + "";
        if (str.equals("")) {
            meta.setMsg("获取管理员信息失败");
            meta.setStatus(500);
        }
        else {
            meta.setMsg("获取管理员信息成功");
            meta.setStatus(200);
        }
        map.put("admin", adminService.getAdmin(admin_id));
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "getAdminList")
    @ResponseBody
    public Map<String, Object> getAdminList(@RequestParam("query") String query,
                               @RequestParam("pageNum") int pageNum,
                               @RequestParam("pageSize") int pageSize) {
        // 新建 Map容器
        Map<String, Object> map = new HashMap<>();
        // 新建 Meta状态属性
        Meta meta = new Meta();
        // 存入键值对
        map.put("admin", adminService.getAdminList(query, (pageNum - 1) * pageSize, pageSize));
        map.put("total", adminService.getAdminNum());
        meta.setMsg("管理员信息获取成功");
        meta.setStatus(200);
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public Map<String, Object> adminLogin(@RequestBody HashMap<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        int admin_id = Integer.parseInt(data.get("admin_id"));
        String admin_password = data.get("password");
        int check = adminService.selectAdmin(admin_id);
        if (check == 1) {
            int result = adminService.checkAdmin(admin_id, admin_password);
            if (result == 1) {
                String token = JWTUtils.sign(String.valueOf(admin_id), admin_password);
                int power = adminService.getAdminPower(admin_id);
                int state = adminService.getAdminState(admin_id);
                String admin_name = adminService.getAdminName(admin_id);
                meta.setMsg("登录成功");
                meta.setStatus(200);
                map.put("admin_name", admin_name);
                map.put("token", token);
                map.put("power", power);
                map.put("state", state);
            } else {
                meta.setMsg("账号密码不匹配失败");
                meta.setStatus(405);
            }
        } else {
            meta.setMsg("账号不存在");
            meta.setStatus(500);
        }
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "changeAdminState")
    @ResponseBody
    public Map<String, Object> changeAdminState(@RequestParam("adminId") int admin_id, @RequestParam("state") Boolean state) {
        int result = adminService.changeAdminState(admin_id, state);
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (result == 1) {
            meta.setMsg("状态设置成功");
            meta.setStatus(200);
            map.put("data", adminService.getAdmin(admin_id));
            map.put("meta", meta);
        } else {
            meta.setMsg("状态设置失败");
            meta.setStatus(500);
            map.put("meta", meta);
        }
        return map;
    }
}
