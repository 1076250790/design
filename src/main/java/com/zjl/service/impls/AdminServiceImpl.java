package com.zjl.service.impls;

import com.zjl.dao.AdminDao;
import com.zjl.entity.Admin;
import com.zjl.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public List<Admin> getAdminList(String query, int pageNum, int pageSize) {
        return adminDao.getAdminList(query, pageNum, pageSize);
    }

    @Override
    public int getAdminNum() {
        return adminDao.getAdminNum();
    }

    @Override
    public int selectAdmin(int admin_id) {
        return adminDao.selectAdmin(admin_id);
    }

    @Override
    public int checkAdmin(int admin_id, String admin_password) {
        return adminDao.checkAdmin(admin_id, admin_password);
    }

    @Override
    public int changeAdminState(int admin_id, Boolean state) {
        return adminDao.changeAdminState(admin_id, state);
    }

    @Override
    public Admin getAdmin(int admin_id) {
        return adminDao.getAdmin(admin_id);
    }

    @Override
    public void addAdmin(Admin admin) {
        adminDao.addAdmin(admin);
    }

    @Override
    public void editAdmin(Admin admin) {
        adminDao.editAdmin(admin);
    }

    @Override
    public void removeAdmin(int admin_id) {
        adminDao.removeAdmin(admin_id);
    }

    @Override
    public int getAdminPower(int admin_id) {
        return adminDao.getAdminPower(admin_id);
    }

    @Override
    public int getAdminState(int admin_id) {
        return adminDao.getAdminState(admin_id);
    }

    @Override
    public String getAdminName(int admin_id) {
        return adminDao.getAdminName(admin_id);
    }
}
