package com.zjl.dao;

import com.zjl.entity.HomePic;
import com.zjl.entity.ListPic;

import java.util.List;

public interface PicDao {
    // 获取主页轮播图
    public List<HomePic> getAllHomePic();
    // 添加主页轮播图
    public void addHomePic(int id, String homePic);
    // 修改主页轮播图
    public void updateHomePic(int id, String homePic);
    // 查找 id
    public Boolean selectHomePicId(int id);
    // 删除 home表数据
    public void deleteHomePic();

    // 获取列表页轮播图
    public List<ListPic> getAllListPic();
    // 添加列表页轮播图
    public void addListPic(int id, String listPic);
    // 修改列表页轮播图
    public void updateListPic(int id, String listPic);
    // 查找 id
    public Boolean selectListPicId(int id);
    // 删除 list表数据
    public void deleteListPic();
}
