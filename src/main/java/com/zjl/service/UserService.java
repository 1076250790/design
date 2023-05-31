package com.zjl.service;

import com.zjl.entity.User;

import java.util.List;

public interface UserService {
    // 获取用户列表
    public List<User> getUser(String query, int pageNum, int pageSize);
    // 获取用户总数
    public int getUserNum();
    // 添加用户
    public void addUser(User user);
    // 用户名和账号检验
    public int checkUserNameAndUserId(String userid, String username);
    // 检测账号与密码是否匹配
    public int checkUserIdAndPwd(String userid, String password);
    // 根据 id查找用户
    public User selectUserById(String userid);
    // 更新用户会员状态
    public void updateUserVip(String user_id, Boolean vip);
}
