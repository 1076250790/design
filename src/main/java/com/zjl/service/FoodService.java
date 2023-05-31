package com.zjl.service;

import com.zjl.entity.Evaluation;
import com.zjl.entity.Food;
import com.zjl.entity.FoodIntro;
import com.zjl.entity.FoodType;

import java.util.List;

public interface FoodService {
    // 获取菜品分类 id
    public List<Integer> getTypeId();
    // 根据菜品分类 id获取分类名
    public String getTypeName(int type_id);
    // 根据菜品分类 id获取分类名拼音
    public String getTypePinYin(int type_id);
    // 获取商品
    public List<Food> getFood(int type_id);
    // 根据菜品 id查询原料等信息
    public FoodIntro getFoodMsg(int food_id);
    // 根据菜品 id查询商品评价
    public List<Evaluation> getFoodEva(int food_id, String user_id);
    // 根据菜品名查询图片
    public String getFoodPic(String food_name);




    // 获取菜品列表
    public List<Food> getFoodData(String query, int pageNum, int pageSize);
    // 获取菜品表总条数
    public int getFoodTotal();
    // 修改菜品信息
    public void updateFood(int food_id, String food_pic, int food_price);
    // 根据 id获取菜品
    public Food getUpdateFood(int food_id);
    // 获取菜品分类
    public List<FoodType> getFoodType();
    // 获取最新的菜品 id
    public int getFoodId();
    // 添加菜品
    public void addFood(int food_id, String food_name, String food_pic, String food_price, int type_id);
    // 根据 type_name获取对应的 type_id
    public int getTypeIdByName(String type_name);
    // 删除 id对应菜品的信息和其原料等信息
    public void removeFoodById(int food_id);
    public void removeFoodIntroById(int food_id);
    // 根据 id新建原料等信息
    public void createFoodIntro(int food_id, String yuanliao, String fenliang, String hunsu, String fuliao, String kouwei);
    // 根据 id更新菜品原料等信息
    public void updateFoodIntro(int food_id, String yuanliao, String fenliang, String hunsu, String fuliao, String kouwei);
    // 分页获取菜品分类 id
    public List<Integer> getTypeIdByTypeName(String query, int pageNum, int pageSize);
    // 查询菜单分类总条数
    public int getTypeTotal();
    // 更新菜品的分类
    public void updateFoodType(int food_id, int type_id);
    // 获取菜品最新的 id
    public int getFoodTypeId();
    // 添加分类
    public void addFoodType(int type_id, String type_name, String type_pinyin);
    // 检查分类名或分类 id是否重复
    public int selectFoodType(String type_name, String type_pinyin);
    // 删除分类
    public void deleteType(String type_pinyin);
    // 根据分类 id获取 id
    public int getTypeIdByTypePinYin(String type_pinyin);
    // 修改分类信息
    public void updateTypeMsg(int id, String type_name, String type_pinyin);
}
