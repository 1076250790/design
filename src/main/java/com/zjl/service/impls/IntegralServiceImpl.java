package com.zjl.service.impls;

import com.zjl.dao.IntegralDao;
import com.zjl.entity.Integral;
import com.zjl.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegralServiceImpl implements IntegralService {
    @Autowired
    IntegralDao integralDao;

    @Override
    public void addUserIntegral(String user_id, int integral) {
        integralDao.addUserIntegral(user_id, integral);
    }

    @Override
    public Integer selectIntegralByUserId(String user_id) {
        return integralDao.selectIntegralByUserId(user_id);
    }

    @Override
    public void updateUserIntegral(String user_id, int integral) {
        integralDao.updateUserIntegral(user_id, integral);
    }

    @Override
    public Integral selectUserByUserId(String user_id) {
        return integralDao.selectUserByUserId(user_id);
    }
}
