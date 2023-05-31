package com.zjl.controller;

import com.alibaba.fastjson.JSONArray;
import com.zjl.entity.*;
import com.zjl.service.FoodService;
import com.zjl.service.ReplyService;
import com.zjl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

    @Autowired
    ReplyService replyService;

    // 获取菜品列表
    @RequestMapping(value = "getFood")
    @ResponseBody
    public String getFood() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 获取 type_id的 List数组
        List<Integer> type_id = foodService.getTypeId();
        Meta meta = new Meta();
        if (type_id == null) {
            meta.setMsg("分类获取失败");
            meta.setStatus(500);
        } else {
            // 遍历 List数组
            for (Integer i:type_id) {
                // 新建 Map容器
                Map<String, Object> msg = new HashMap<>();
                // 根据 type_id查询 type_name
                String typeName = foodService.getTypeName(i);
                // 根据 type_id查询 type_pinyin
                String typePinYin = foodService.getTypePinYin(i);
                // 存入 Map容器
                msg.put("id", typePinYin);
                msg.put("food_name", typeName);
                msg.put("children", foodService.getFood(i));
                mapList.add(msg);
            }
        }
        // 返回 List数据
        return JSONArray.toJSONString(mapList);
    }

    // 根据 id获取每道菜的介绍（包括原料、配料等信息）
    @RequestMapping(value = "getFoodMsg")
    @ResponseBody
    public Map<String, Object> getFoodMsg(@RequestParam("food_id") int food_id) {
        Map<String, Object> map = new HashMap<>();
        FoodIntro foodIntro = foodService.getFoodMsg(food_id);
        if (foodIntro != null) {
            map.put("result", foodIntro);
        } else {
            // 没有查询到信息则根据 id新建一条信息
            foodService.createFoodIntro(food_id, "", "", "", "", "");
            FoodIntro foodIntro1 = foodService.getFoodMsg(food_id);
            map.put("result", foodIntro1);
        }
        return map;
    }

    // 根据 id获取每道菜的用户评论
    @RequestMapping(value = "getFoodEvaluation")
    @ResponseBody
    public List<Map<String, Object>> getFoodEvaluation(@RequestParam("food_id") int food_id,
                                                       @RequestParam("userId") String userid) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Evaluation> evaluationList = foodService.getFoodEva(food_id, userid);
        for (Evaluation evaluation : evaluationList) {
            // 新建 Map容器
            Map<String, Object> map = new HashMap<>();
            // 根据用户 id获取对应的用户信息
            User user = userService.selectUserById(evaluation.getUser_id());
            // 存入 map容器
            map.put("food_id", food_id);
            map.put("user_id", evaluation.getUser_id());
            if (evaluation.getUser_id().equals("0")) {
                map.put("username", "匿名用户");
            } else {
                map.put("username", user.getUsername());
            }
            map.put("head_portrait", user.getHead_portrait());
            // 由于 java采用毫秒计算，所以当时间戳是 10位时，需要换算单位
            Date date = new Date(Long.parseLong(evaluation.getPlace_date()));
            // 设置时间戳转换日期的格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("place_date", sdf.format(date));
            map.put("content", evaluation.getContent());
            map.put("likes", evaluation.getLikes());
            Reply reply = replyService.getReplyByUserId(evaluation.getUser_id(), food_id, evaluation.getPlace_date());
            if (reply != null) {
                // 由于 java采用毫秒计算，所以当时间戳是 10位时，需要换算单位
                Date date1 = new Date(Long.parseLong(reply.getPlace_time()));
                Date date2 = new Date(Long.parseLong(reply.getComplete_time()));
                reply.setPlace_time(sdf.format(date1));
                reply.setComplete_time(sdf.format(date2));
                // 设置时间戳转换日期的格式
                map.put("reply", reply);
            }
            // 把 map添加进 List数组
            mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "getFoodPic")
    @ResponseBody
    public Map<String, Object> getFoodPic(@RequestParam("food") String food) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        String food_pic = foodService.getFoodPic(food);
        if (food_pic == null) {
            meta.setMsg("获取失败");
            meta.setStatus(500);
        } else {
            map.put("pic", food_pic);
            meta.setMsg("获取成功");
            meta.setStatus(200);
        }
        map.put("meta", meta);
        return map;
    }

    // 分页查询获取菜品总数和菜品列表（包括条件查询）
    @RequestMapping(value = "getFoodList")
    @ResponseBody
    public Map<String, Object> getFoodList(@RequestParam("query") String query,
                                           @RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        // 分页查询
        List<Food> foodList = foodService.getFoodData(query, (pageNum - 1) * pageSize, pageSize);
        if (Objects.equals(query, "")) {
            // 总条数查询
            int total = foodService.getFoodTotal();
            map.put("total", total);
        } else {
            map.put("total", foodList.size());
        }
        if (foodList == null) {
            meta.setMsg("菜品列表获取失败");
            meta.setStatus(500);
        } else {
            meta.setMsg("菜品列表获取成功");
            meta.setStatus(200);
            for (Food food: foodList) {
                food.setType_name(foodService.getTypeName(food.getType_id()));
            }
        }
        map.put("foodList", foodList);
        map.put("meta", meta);
        return map;
    }

    // 添加菜品
    @RequestMapping(value = "addFormFood")
    @ResponseBody
    public Map<String, Object> addFood(@RequestBody HashMap<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        System.out.println(data);
        // 从 data中获取文件名和添加菜品的表单内容
        String file = (String) data.get("file");
        int food_id = (int) data.get("food_id");
        System.out.println(file);
        // 新建 Object对象用于存放表单数据
        Object form = data.get("form");
        Map entity = (Map)form;
        String food_name = String.valueOf(entity.get("food_name"));
        String food_price = String.valueOf(entity.get("food_price"));
        String type_name = String.valueOf(entity.get("type_name"));
        int type_id = foodService.getTypeIdByName(type_name);
        System.out.println(data);
        foodService.addFood(food_id, food_name, file, food_price, type_id);
        meta.setMsg("菜品修改成功");
        meta.setStatus(201);
        map.put("meta", meta);
        return map;
    }

    // 根据 id获取需要修改的菜品的信息
    @RequestMapping(value = "editFood")
    @ResponseBody
    public Map<String, Object> editFood(@RequestParam("food_id") int food_id) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        Food food = foodService.getUpdateFood(food_id);
        map.put("food", food);
        if (food != null) {
            meta.setMsg("菜品信息获取成功");
            meta.setStatus(200);
        } else {
            meta.setMsg("菜品信息获取失败");
            meta.setStatus(500);
        }
        map.put("meta", meta);
        return map;
    }

    // 修改菜品信息
    @RequestMapping(value = "updateFood")
    @ResponseBody
    public Map<String, Object> updateFood(@RequestBody HashMap<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        System.out.println(data);
        String file = (String) data.get("file");
        System.out.println(file);
        Object form = data.get("form");
        Map entity = (Map)form;
        int food_id = (int) entity.get("food_id");
        String price = String.valueOf(entity.get("food_price"));
        int food_price = Integer.parseInt(price);
        foodService.updateFood(food_id, file, food_price);
        meta.setMsg("菜品修改成功");
        meta.setStatus(201);
        map.put("meta", meta);
        return map;
    }

    // 删除菜品信息
    @RequestMapping(value = "removeFoodById")
    @ResponseBody
    public Map<String, Object> removeFoodById(@RequestParam("food_id") int food_id) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        String str = food_id + "";
        if (str.equals("")) {
            meta.setMsg("删除菜品信息失败");
            meta.setStatus(500);
        } else {
            foodService.removeFoodById(food_id);
            foodService.removeFoodIntroById(food_id);
            meta.setMsg("删除菜品信息成功");
            meta.setStatus(200);
        }
        map.put("food", null);
        map.put("meta", meta);
        return map;
    }

    // 获取菜品分类信息返回到添加菜品对话框内
    @RequestMapping(value = "getFoodType")
    @ResponseBody
    public Map<String, Object> getFoodType() {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        List<FoodType> foodTypeList = foodService.getFoodType();
        int food_id = foodService.getFoodId();
        if (foodTypeList == null) {
            meta.setMsg("分类获取失败");
            meta.setStatus(500);
        } else {
            meta.setMsg("分类获取成功");
            meta.setStatus(200);
        }
        map.put("foodType", foodTypeList);
        map.put("food_id", food_id);
        map.put("meta", meta);
        return map;
    }

    // 更新菜品原料等信息
    @RequestMapping(value = "updateFoodIntro")
    @ResponseBody
    public Map<String, Object> updateFoodIntro(@RequestBody HashMap<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (data == null) {
            meta.setMsg("更新菜品原料信息失败");
            meta.setStatus(500);
        } else {
            int food_id = (int) data.get("food_id");
            String yuanliao = (String) data.get("yuanliao");
            String fenliang = (String) data.get("fenliang");
            String hunsu = (String) data.get("hunsu");
            String fuliao = (String) data.get("fuliao");
            String kouwei = (String) data.get("kouwei");
            foodService.updateFoodIntro(food_id, yuanliao, fenliang, hunsu, fuliao, kouwei);
            meta.setMsg("更新菜品原料信息成功");
            meta.setStatus(201);
            map.put("meta", meta);
        }
        return map;
    }

    // 获取菜品分类
    @RequestMapping(value = "getCateList")
    @ResponseBody
    public Map<String, Object> getCateList(@RequestParam("query") String query,
                                          @RequestParam("pageNum") int pageNum,
                                          @RequestParam("pageSize") int pageSize) {
        Map<String, Object> mapList = new HashMap<>();
        // 获取 type_id的 List数组
        List<Integer> type_id = foodService.getTypeIdByTypeName(query, (pageNum - 1) * pageSize, pageSize);
        Meta meta = new Meta();
        if (type_id == null) {
            meta.setMsg("分页分类获取失败");
            meta.setStatus(500);
        } else {
            List<Map<String, Object>> maps = new ArrayList<>();
            int total = foodService.getTypeTotal();
            mapList.put("total", total);
            // 遍历 List数组
            for (Integer i:type_id) {
                // 新建 Map容器
                Map<String, Object> msg = new HashMap<>();
                // 根据 type_id查询 type_name
                String typeName = foodService.getTypeName(i);
                // 根据 type_id查询 type_pinyin
                String typePinYin = foodService.getTypePinYin(i);
                // 存入 Map容器
                msg.put("id", typePinYin);
                msg.put("food_name", typeName);
                msg.put("type", "分类");
                msg.put("children", foodService.getFood(i));
                maps.add(msg);
            }
            mapList.put("msg", maps);
        }
        // 返回 List数据
        return mapList;
    }

    // 菜品的移动
    @RequestMapping(value = "moveFoodType")
    @ResponseBody
    public Map<String, Object> moveFoodType(@RequestParam("food_id") int food_id, @RequestParam("type_name") String type_name) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        // 如果 type_name为空则直接返回移动失败
        if (type_name.equals("")) {
            meta.setMsg("菜品移动失败");
            meta.setStatus(500);
            map.put("meta", meta);
        } else {
            // 不为空则根据 type_name获取对应 id, 再修改 food的 type_id
            int type_id = foodService.getTypeIdByName(type_name);
            foodService.updateFoodType(food_id, type_id);
            meta.setMsg("菜品移动成功");
            meta.setStatus(201);
            map.put("meta", meta);
        }
        return map;
    }

    // 获取最后的菜品分类 id
    @RequestMapping(value = "getLastFoodTypeId")
    @ResponseBody
    public Map<String, Object> getLastFoodTypeId() {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        int type_id = foodService.getFoodTypeId();
        String test = String.valueOf(type_id);
        if (test.equals("")) {
            meta.setMsg("获取id失败");
            meta.setStatus(500);
            map.put("meta", meta);
        } else {
            meta.setMsg("获取id成功");
            meta.setStatus(200);
            map.put("type_id", type_id);
            map.put("meta", meta);
        }
        return map;
    }

    // 添加菜品分类
    @RequestMapping(value = "addFoodType")
    @ResponseBody
    public Map<String, Object> addFoodType(@RequestBody HashMap<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (data == null) {
            meta.setMsg("添加分类失败");
            meta.setStatus(500);
        } else {
            int type_id = (int) data.get("id");
            String type_pinyin = (String) data.get("type_pinyin");
            String type_name = (String) data.get("type_name");
            int i = foodService.selectFoodType(type_name, type_pinyin);
            if (i != 1) {
                foodService.addFoodType(type_id, type_name, type_pinyin);
                meta.setMsg("添加分类成功");
                meta.setStatus(200);
            } else {
                meta.setMsg("添加分类重复");
                meta.setStatus(405);
            }
        }
        map.put("meta", meta);
        return map;
    }

    // 删除菜品分类
    @RequestMapping(value = "deleteType")
    @ResponseBody
    public Map<String, Object> deleteType(@RequestParam("type_pinyin") String type_pinyin) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (type_pinyin == null) {
            meta.setMsg("删除分类失败");
            meta.setStatus(500);
        } else {
            foodService.deleteType(type_pinyin);
            meta.setMsg("删除分类成功");
            meta.setStatus(201);
        }
        map.put("meta", meta);
        return map;
    }

    // 根据分类 id获取 id
    @RequestMapping(value = "getTypeIdByTypePinYin")
    @ResponseBody
    public Map<String, Object> getTypeIdByTypePinYin(@RequestParam("type_pinyin") String type_pinyin) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (type_pinyin == null) {
            meta.setMsg("获取失败");
            meta.setStatus(500);
        } else {
            int type_id = foodService.getTypeIdByTypePinYin(type_pinyin);
            meta.setMsg("获取成功");
            meta.setStatus(200);
            map.put("type_id", type_id);
        }
        map.put("meta", meta);
        return map;
    }

    // 修改菜品分类信息
    @RequestMapping(value = "editTypeMsg")
    @ResponseBody
    public Map<String, Object> editTypeMsg(@RequestBody HashMap<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (data == null) {
            meta.setMsg("修改失败");
            meta.setStatus(500);
        } else {
            int id = (int) data.get("id");
            String type_name = (String) data.get("type_name");
            String type_pinyin = (String) data.get("type_pinyin");
            foodService.updateTypeMsg(id, type_name, type_pinyin);
            meta.setMsg("修改成功");
            meta.setStatus(200);
        }
        map.put("meta", meta);
        return map;
    }
}
