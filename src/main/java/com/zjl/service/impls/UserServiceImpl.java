package com.zjl.service.impls;

import com.zjl.dao.UserDao;
import com.zjl.entity.User;
import com.zjl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUser(String query, int pageNum, int pageSize) {
        return userDao.getUser(query, pageNum, pageSize);
    }

    @Override
    public int getUserNum() {
        return userDao.getUserNum();
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public int checkUserNameAndUserId(String userid, String username) {
        return userDao.checkUserNameAndUserId(userid, username);
    }

    @Override
    public int checkUserIdAndPwd(String userid, String password) {
        return userDao.checkUserIdAndPwd(userid, password);
    }

    @Override
    public User selectUserById(String userid) {
        return userDao.selectUserById(userid);
    }

    @Override
    public void updateUserVip(String user_id, Boolean vip) {
        userDao.updateUserVip(user_id, vip);
    }
}
