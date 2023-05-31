package com.zjl.service.impls;

import com.zjl.dao.ScoreDao;
import com.zjl.entity.Score;
import com.zjl.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreDao scoreDao;

    @Override
    public void addScore(String user_id, String place_time, double score) {
        scoreDao.addScore(user_id, place_time, score);
    }

    @Override
    public List<Score> getScoreList() {
        return scoreDao.getScoreList();
    }

    @Override
    public Boolean checkScore(String user_id, String place_time) {
        return scoreDao.checkScore(user_id, place_time);
    }

    @Override
    public Score getScoreByOrder(String user_id, String place_time) {
        return scoreDao.getScoreByOrder(user_id, place_time);
    }
}
