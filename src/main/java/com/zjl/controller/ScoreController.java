package com.zjl.controller;

import com.zjl.entity.Meta;
import com.zjl.entity.Score;
import com.zjl.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @RequestMapping(value = "addScore")
    @ResponseBody
    public Map<String, Object> addScore(@RequestParam("user_id") String user_id,
                                        @RequestParam("place_time") String p_time,
                                        @RequestParam("score") double score) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        if (score >= 0) {
            Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p_time);
            // 转化为 13位时间戳
            String place_time = (time.getTime() / 1000) + "000";
            scoreService.addScore(user_id, place_time, score);
            meta.setMsg("评分成功");
            meta.setStatus(200);
        } else {
            meta.setMsg("评分失败");
            meta.setStatus(500);
        }
        return map;
    }

    @RequestMapping(value = "getScore")
    @ResponseBody
    public Map<String, Object> getScore() {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        List<Score> scores = scoreService.getScoreList();
        if (scores == null) {
            meta.setMsg("获取失败");
            meta.setStatus(500);
        } else {
            int size = scores.size();
            double score = 0.0;
            for (Score s : scores) {
                score += s.getScore();
            }
            map.put("score", new Formatter().format("%.1f", score / size).toString());
            meta.setMsg("获取成功");
            meta.setStatus(200);
        }
        return map;
    }

    @RequestMapping(value = "checkScore")
    @ResponseBody
    public Map<String, Object> checkScore(@RequestParam("user_id") String user_id,
                                          @RequestParam("place_time") String p_time) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p_time);
        // 转化为 13位时间戳
        String place_time = (time.getTime() / 1000) + "000";
        Boolean check = scoreService.checkScore(user_id, place_time);
        if (check) {
            Score score = scoreService.getScoreByOrder(user_id, place_time);
            map.put("score", score);
        }
        map.put("result", check);
        return map;
    }

}
