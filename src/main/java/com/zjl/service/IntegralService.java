package com.zjl.service;

import com.zjl.entity.Integral;

public interface IntegralService {
    // 添加用户积分
    public void addUserIntegral(String user_id, int integral);
    // 根据用户 id获取积分
    public Integer selectIntegralByUserId(String user_id);
    // 更新用户积分
    public void updateUserIntegral(String user_id, int integral);
    // 根据用户 id查询是否存在
    public Integral selectUserByUserId(String user_id);
}
