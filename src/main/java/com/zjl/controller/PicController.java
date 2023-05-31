package com.zjl.controller;

import com.zjl.entity.HomePic;
import com.zjl.entity.ListPic;
import com.zjl.entity.Meta;
import com.zjl.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PicController {
    @Autowired
    PicService picService;

    // 获取主页的轮播图
    @RequestMapping(value = "getAllHomePic")
    @ResponseBody
    public Map<String, Object> getAllHomePic() {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        List<HomePic> homePicList = picService.getAllHomePic();
        System.out.println(homePicList);
        if (homePicList == null) {
            meta.setMsg("获取失败");
            meta.setStatus(300);
        } else {
            map.put("homePicList", homePicList);
            meta.setMsg("获取成功");
            meta.setStatus(200);
        }
        map.put("meta", meta);
        return map;
    }

    // 添加主页的轮播图
    @RequestMapping(value = "addHomePic")
    @ResponseBody
    public Map<String, Object> addHomePic(@RequestBody List<String> pic) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        picService.deleteHomePic();
        // 循环图片数组
        for (int i = 0; i < pic.size(); i++) {
            // 检查是否存在该图片 id
            Boolean result = picService.selectHomePicId(i + 1);
            System.out.println(result);
            // 不存在则新增，存在则更新
            if (!result) {
                picService.addHomePic(i + 1, pic.get(i));
                meta.setMsg("上传成功");
                meta.setStatus(200);
            } else {
                picService.updateHomePic(i + 1, pic.get(i));
                meta.setMsg("更新成功");
                meta.setStatus(201);
            }
        }
        map.put("meta", meta);
        return map;
    }

    // 获取列表页的轮播图
    @RequestMapping(value = "getAllListPic")
    @ResponseBody
    public Map<String, Object> getAllListPic() {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        List<ListPic> listPicList = picService.getAllListPic();
        if (listPicList == null) {
            meta.setMsg("获取失败");
            meta.setStatus(300);
        } else {
            map.put("listPicList", listPicList);
            meta.setMsg("获取成功");
            meta.setStatus(200);
        }
        map.put("meta", meta);
        return map;
    }

    // 添加列表页的轮播图
    @RequestMapping(value = "addListPic")
    @ResponseBody
    public Map<String, Object> addListPic(@RequestBody List<String> pic) {
        Map<String, Object> map = new HashMap<>();
        Meta meta = new Meta();
        picService.deleteListPic();
        for (int i = 0; i < pic.size(); i++) {
            Boolean result = picService.selectListPicId(i + 1);
            System.out.println(result);
            if (!result) {
                picService.addListPic(i + 1, pic.get(i));
                meta.setMsg("上传成功");
                meta.setStatus(200);
            } else {
                picService.updateListPic(i + 1, pic.get(i));
                meta.setMsg("更新成功");
                meta.setStatus(201);
            }
        }
        map.put("meta", meta);
        return map;
    }
}
