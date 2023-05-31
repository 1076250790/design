package com.zjl.service.impls;

import com.zjl.dao.EvaluationDao;
import com.zjl.entity.Evaluation;
import com.zjl.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    EvaluationDao evaluationDao;

    @Override
    public List<Evaluation> getAllEvaluationByUser(String query, int pageNum, int pageSize) {
        return evaluationDao.getAllEvaluationByUser(query, pageNum, pageSize);
    }

    @Override
    public List<Evaluation> getAllEvaluationByFood(String query, int pageNum, int pageSize) {
        return evaluationDao.getAllEvaluationByFood(query, pageNum, pageSize);
    }

    @Override
    public int getEvaluationTotal() {
        return evaluationDao.getEvaluationTotal();
    }

    @Override
    public void addFoodEvaluation(int food_id, String user_id, String place_time, String content) {
        evaluationDao.addFoodEvaluation(food_id, user_id, place_time, content);
    }

    @Override
    public void addEvaluationByAdmin(String admin_name, int food_id, String user_id, String place_time, String complete_time, String content) {
        evaluationDao.addEvaluationByAdmin(admin_name, food_id, user_id, place_time, complete_time, content);
    }

    @Override
    public void deleteEvaluation(String user_id, String place_time) {
        evaluationDao.deleteEvaluation(user_id, place_time);
    }
}
