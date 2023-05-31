package com.zjl.controller;

import com.alibaba.fastjson.JSONArray;
import com.zjl.entity.Meta;
import com.zjl.entity.User;
import com.zjl.service.IntegralService;
import com.zjl.service.UserService;
import com.zjl.utils.JWTUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    IntegralService integralService;

    @RequestMapping(value = "addUser")
    @ResponseBody
    public Map<String, Object> addUser(@RequestParam("userId") String id,
                                       @RequestParam("username") String username,
                                       @RequestParam("head_portrait")String headUrl,
                                       @RequestParam("password") String password,
                                       @RequestParam("gender") int gender,
                                       @RequestParam("vip") int vip,
                                       @RequestParam("loginMethod") String loginMethod) {
        int result = userService.checkUserNameAndUserId(id, username);
        Map<String, Object> map = new HashMap<>();
        if (result == 0) {
            User user = new User();
            user.setUser_id(id);
            user.setUsername(username);
            if (headUrl.equals("")) {
                user.setHead_portrait(null);
            } else {
                user.setHead_portrait(headUrl);
            }
            if (password.equals("")) {
                user.setPassword(null);;
            } else {
                user.setPassword(password);
            }
            user.setGender(gender);
            user.setVip(vip);
            user.setLogin_method(loginMethod);
            userService.addUser(user);
            map.put("msg", "用户注册成功");
            map.put("status", true);
        } else {
            map.put("msg", "账号或用户名重复");
            map.put("status", false);
        }
        return map;
    }

    @RequestMapping(value = "getUserList")
    @ResponseBody
    public Map<String, Object> getUserList(@RequestParam("query") String query,
                              @RequestParam("pageNum") int pageNum,
                              @RequestParam("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        List<User> userList = userService.getUser(query, (pageNum - 1) * pageSize, pageSize);
        for (User user: userList) {
            Integer integral = integralService.selectIntegralByUserId(user.getUser_id());
            if (integral == null) {
                user.setIntegral(0);
            } else {
                user.setIntegral(integral);
            }
        }
        map.put("user", userList);
        map.put("total", userService.getUserNum());
        meta.setMsg("用户信息获取成功");
        meta.setStatus(200);
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "checkUser")
    @ResponseBody
    public Map<String, Object> checkUser(@RequestParam("userId") String id) {
        Map<String, Object> map = new HashMap<>();
        // 微信登录判断 id是否已经存在数据库中, 微信登录没有密码，所以设置 null
        int result = userService.checkUserNameAndUserId(id, null);
        // 如果没找到代表是新用户
        if (result == 0) {
            map.put("status", false);
        } else {
            map.put("status", true);
        }
        return map;
    }

    @RequestMapping(value = "checkIdPwd")
    @ResponseBody
    public Map<String, Object> checkIdPwd(@RequestParam("userId") String id, @RequestParam("password") String password) {
        Map<String, Object> map = new HashMap<>();
        int result = userService.checkUserIdAndPwd(id, password);
        if (result == 1) {
            User user = userService.selectUserById(id);
            String token = JWTUtils.sign(id, password);
            map.put("user", user);
            map.put("check", true);
            map.put("token", token);
        } else {
            map.put("user", "");
            map.put("check", false);
        }
        return map;
    }

    @RequestMapping(value = "checkToken")
    @ResponseBody
    public Boolean checkToken(@RequestParam("token") String token) {
        if (token == null) {
            return false;
        }
        return JWTUtils.verify(token);
    }

    @RequestMapping(value = "beVip")
    @ResponseBody
    public Map<String, Object> beVip(@RequestParam("user_id") String user_id, @RequestParam("vip") Boolean vip) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        userService.updateUserVip(user_id, vip);
        meta.setMsg("更新成功");
        meta.setStatus(200);
        map.put("meta", meta);
        return map;
    }

}
