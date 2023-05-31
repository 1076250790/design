package com.zjl.controller;

import com.zjl.entity.Meta;
import com.zjl.entity.Reply;
import com.zjl.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReplyController {
    @Autowired
    ReplyService replyService;

    @RequestMapping(value = "getAllReply")
    @ResponseBody
    public Map<String, Object> getAllReply(@RequestParam("query") String query,
                                           @RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(pageSize);
        Meta meta = new Meta();
        List<Reply> replyList = replyService.getAllReply(query, (pageNum - 1) * pageSize, pageSize);
        if (replyList != null) {
            List<Reply> replies = new ArrayList<>();
            for (Reply r: replyList) {
                Reply reply = new Reply();
                String ptime = r.getPlace_time();
                String ctime = r.getComplete_time();
                // 设置时间的格式
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 将时间转换指定格式的日期
                String place_time = f.format(Long.valueOf(ptime));
                String complete_time = f.format(Long.valueOf(ctime));
                reply.setAdmin_name(r.getAdmin_name());
                reply.setUser_id(r.getUser_id());
                reply.setPlace_time(place_time);
                reply.setComplete_time(complete_time);
                reply.setContent(r.getContent());
                replies.add(reply);
            }
            meta.setMsg("获取成功");
            meta.setStatus(200);
            map.put("replyList", replies);
            map.put("total", replyService.getReplyTotal());
        } else {
            meta.setMsg("获取失败");
            meta.setStatus(500);
        }
        map.put("meta", meta);
        return map;
    }

    @RequestMapping(value = "deleteReply")
    @ResponseBody
    public Map<String, Object> deleteReply(@RequestParam("admin_name") String admin_name,
                                           @RequestParam("complete_time") String complete_time) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        System.out.println(admin_name);
        System.out.println(complete_time);
        if (admin_name.equals("")) {
            meta.setMsg("删除失败");
            meta.setStatus(500);
        } else {
            Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(complete_time);
            // 转化为 13位时间戳
            String ctime = String.valueOf(time.getTime());
            replyService.deleteReply(admin_name, ctime);
            meta.setMsg("删除成功");
            meta.setStatus(200);
        }
        map.put("meta", meta);
        return map;
    }
}
