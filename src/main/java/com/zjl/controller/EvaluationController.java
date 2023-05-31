package com.zjl.controller;

import com.zjl.entity.Evaluation;
import com.zjl.entity.Meta;
import com.zjl.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EvaluationController {
    @Autowired
    EvaluationService evaluationService;

    @RequestMapping(value = "getAllEvaluation")
    @ResponseBody
    public Map<String, Object> getAllEvaluation(@RequestParam("query") String query,
                                                @RequestParam("select") String select,
                                                @RequestParam("pageNum") int pageNum,
                                                @RequestParam("pageSize") int pageSize) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (query == null) {
            List<Evaluation> evaluationList = evaluationService.getAllEvaluationByUser(null, (pageNum - 1) * pageSize, pageSize);
            map.put("total", evaluationService.getEvaluationTotal());
            map.put("evaluationList", evaluationList);
            meta.setMsg("用户评论获取成功");
            meta.setStatus(200);
        } else {
            if (select.equals("user")) {
                List<Evaluation> evaluationList = evaluationService.getAllEvaluationByUser(query, (pageNum - 1) * pageSize, pageSize);
                List<Evaluation> evaluationList1 = evaluations(evaluationList);
                map.put("total", evaluationService.getEvaluationTotal());
                map.put("evaluationList", evaluationList1);
                meta.setMsg("用户评论获取成功");
                meta.setStatus(200);
            } else if(select.equals("food")) {
                List<Evaluation> evaluationList = evaluationService.getAllEvaluationByFood(query, (pageNum - 1) * pageSize, pageSize);
                List<Evaluation> evaluationList1 = evaluations(evaluationList);
                map.put("total", evaluationService.getEvaluationTotal());
                map.put("evaluationList", evaluationList1);
                meta.setMsg("用户评论获取成功");
                meta.setStatus(200);
            }
        }
        map.put("meta", meta);
        return map;
    }

    // 添加用户评论
    @RequestMapping(value = "addFoodEvaluation")
    @ResponseBody
    public Map<String, Object> addFoodEvaluation(@RequestParam("food_id") int food_id,
                                                 @RequestParam("user_id") String user_id,
                                                 @RequestParam("place_time") String place_time,
                                                 @RequestParam("content") String content) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (user_id == null) {
            evaluationService.addFoodEvaluation(food_id, "0", place_time, content);
        } else {
            evaluationService.addFoodEvaluation(food_id, user_id, place_time, content);
        }
        meta.setMsg("添加成功");
        meta.setStatus(200);
        map.put("meta", meta);
        return map;
    }

    // 添加回复评论
    @RequestMapping(value = "addEvaluation")
    @ResponseBody
    public Map<String, Object> addEvaluation(@RequestBody HashMap<String, Object> data) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        System.out.println(data);
        Meta meta = new Meta();
        String admin_name = String.valueOf(data.get("admin_name"));
        System.out.println(admin_name);
        String user_id = String.valueOf(data.get("user_id"));
        System.out.println(user_id);
        int food_id = (int) data.get("food_id");
        System.out.println(food_id);
        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) data.get("place_time"));
        // 转化为 13位时间戳
        String place_time = (time.getTime() / 1000) + "000";
        // 获取当前的时间
        Date date = new Date();
        String complete_time = (date.getTime() / 1000) + "000";
        String content = (String) data.get("content");
        // 回复评论添加
        evaluationService.addEvaluationByAdmin(admin_name, food_id, user_id, place_time, complete_time, content);
        meta.setMsg("回复成功");
        meta.setStatus(200);
        map.put("meta", meta);
        return map;
    }

    // 删除用户评论
    @RequestMapping(value = "deleteEvaluation")
    @ResponseBody
    public Map<String, Object> deleteEvaluation(@RequestParam("user_id") String user_id,
                                                @RequestParam("place_time") String place_time) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        // 把输入时间字符串转化日期类型
        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(place_time);
        // 转化为 10位时间戳
        long timestamp = time.getTime() / 1000L;
        evaluationService.deleteEvaluation(user_id, String.valueOf(timestamp));
        meta.setMsg("删除成功");
        meta.setStatus(200);
        map.put("meta", meta);
        return map;
    }


    // 遍历评论列表，将时间戳转换为日期格式
    public List<Evaluation> evaluations(List<Evaluation> evaluationList) throws ParseException {
        List<Evaluation> evaluationList1 = new ArrayList<>();
        for (Evaluation i: evaluationList) {
            // 新建 evaluation容器
            Evaluation evaluation = new Evaluation();
            String user_id = i.getUser_id();
            int food_id = i.getFood_id();
            String ptime = i.getPlace_date();
            // 设置时间的格式
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 将时间转换指定格式的日期
            String place_time = f.format(Long.valueOf(ptime));
            System.out.println(place_time);
            String content = i.getContent();
            int likes = i.getLikes();
            // 设计返回值
            evaluation.setUser_id(user_id);
            evaluation.setFood_id(food_id);
            evaluation.setPlace_date(place_time);
            evaluation.setContent(content);
            evaluation.setLikes(likes);
            // 存入 List数组
            evaluationList1.add(evaluation);
        }
        return evaluationList1;
    }
}
