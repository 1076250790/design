package com.zjl.dao;

import com.zjl.entity.Score;

import java.util.List;

public interface ScoreDao {
    // 添加评分
    public void addScore(String user_id, String place_time, double score);
    // 获取全部评分
    public List<Score> getScoreList();
    // 检查用户订单是否评分过
    public Boolean checkScore(String user_id, String place_time);
    // 根据订单查看用户评分情况
    public Score getScoreByOrder(String user_id, String place_time);
}
