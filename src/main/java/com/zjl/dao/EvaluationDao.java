package com.zjl.dao;

import com.zjl.entity.Evaluation;

import java.util.List;

public interface EvaluationDao {
    // 按用户获取所有用户评价
    public List<Evaluation> getAllEvaluationByUser(String query, int pageNum, int pageSize);
    // 按菜品获取所有用户评价
    public List<Evaluation> getAllEvaluationByFood(String query, int pageNum, int pageSize);
    // 获取评论总数
    public int getEvaluationTotal();
    // 用户添加评论
    public void addFoodEvaluation(int food_id, String user_id, String place_time, String content);
    // 管理员回复评论
    public void addEvaluationByAdmin(String admin_name, int food_id, String user_id, String place_time, String complete_time, String content);
    // 删除用户评论
    public void deleteEvaluation(String user_id, String place_time);
}
