package com.zjl.service.impls;

import com.zjl.dao.FoodDao;
import com.zjl.entity.Evaluation;
import com.zjl.entity.Food;
import com.zjl.entity.FoodIntro;
import com.zjl.entity.FoodType;
import com.zjl.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodDao foodDao;

    @Override
    public List<Integer> getTypeId() {
        return foodDao.getTypeId();
    }

    @Override
    public String getTypeName(int type_id) {
        return foodDao.getTypeName(type_id);
    }

    @Override
    public String getTypePinYin(int type_id) {
        return foodDao.getTypePinYin(type_id);
    }

    @Override
    public List<Food> getFood(int type_id) {
        return foodDao.getFood(type_id);
    }

    @Override
    public FoodIntro getFoodMsg(int food_id) {
        return foodDao.getFoodMsg(food_id);
    }

    @Override
    public List<Evaluation> getFoodEva(int food_id, String user_id) {
        return foodDao.getFoodEva(food_id, user_id);
    }

    @Override
    public String getFoodPic(String food_name) {
        return foodDao.getFoodPic(food_name);
    }

    @Override
    public List<Food> getFoodData(String query, int pageNum, int pageSize) {
        return foodDao.getFoodData(query, pageNum, pageSize);
    }

    @Override
    public int getFoodTotal() {
        return foodDao.getFoodTotal();
    }

    @Override
    public void updateFood(int food_id, String food_pic, int food_price) {
        foodDao.updateFood(food_id, food_pic, food_price);
    }

    @Override
    public Food getUpdateFood(int food_id) {
        return foodDao.getUpdateFood(food_id);
    }

    @Override
    public List<FoodType> getFoodType() {
        return foodDao.getFoodType();
    }

    @Override
    public int getFoodId() {
        return foodDao.getFoodId();
    }

    @Override
    public void addFood(int food_id, String food_name, String food_pic, String food_price, int type_id) {
        foodDao.addFood(food_id, food_name, food_pic, food_price, type_id);
    }

    @Override
    public int getTypeIdByName(String type_name) {
        return foodDao.getTypeIdByName(type_name);
    }

    @Override
    public void removeFoodById(int food_id) {
        foodDao.removeFoodById(food_id);
    }

    @Override
    public void removeFoodIntroById(int food_id) {
        foodDao.removeFoodIntroById(food_id);
    }

    @Override
    public void createFoodIntro(int food_id, String yuanliao, String fenliang, String hunsu, String fuliao, String kouwei) {
        foodDao.createFoodIntro(food_id, yuanliao, fenliang, hunsu, fuliao, kouwei);
    }

    @Override
    public void updateFoodIntro(int food_id, String yuanliao, String fenliang, String hunsu, String fuliao, String kouwei) {
        foodDao.updateFoodIntro(food_id, yuanliao, fenliang, hunsu, fuliao, kouwei);
    }

    @Override
    public List<Integer> getTypeIdByTypeName(String query, int pageNum, int pageSize) {
        return foodDao.getTypeIdByTypeName(query, pageNum, pageSize);
    }

    @Override
    public int getTypeTotal() {
        return foodDao.getTypeTotal();
    }

    @Override
    public void updateFoodType(int food_id, int type_id) {
        foodDao.updateFoodType(food_id, type_id);
    }

    @Override
    public int getFoodTypeId() {
        return foodDao.getFoodTypeId();
    }

    @Override
    public void addFoodType(int type_id, String type_name, String type_pinyin) {
        foodDao.addFoodType(type_id, type_name, type_pinyin);
    }

    @Override
    public int selectFoodType(String type_name, String type_pinyin) {
        return foodDao.selectFoodType(type_name, type_pinyin);
    }

    @Override
    public void deleteType(String type_pinyin) {
        foodDao.deleteType(type_pinyin);
    }

    @Override
    public int getTypeIdByTypePinYin(String type_pinyin) {
        return foodDao.getTypeIdByTypePinYin(type_pinyin);
    }

    @Override
    public void updateTypeMsg(int id, String type_name, String type_pinyin) {
        foodDao.updateTypeMsg(id, type_name, type_pinyin);
    }
}
