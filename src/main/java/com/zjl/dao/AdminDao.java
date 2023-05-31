package com.zjl.dao;

import com.zjl.entity.Admin;

import java.util.List;

public interface AdminDao {
    // 获取管理员列表
    public List<Admin> getAdminList(String query, int pageNum, int pageSize);
    // 获取管理员数量
    public int getAdminNum();
    // 查询管理员账号是否存在
    public int selectAdmin(int admin_id);
    // 验证管理员账号
    public int checkAdmin(int admin_id, String admin_password);
    // 修改管理员启用状态
    public int changeAdminState(int admin_id, Boolean state);
    // 根据 id获取管理员数据
    public Admin getAdmin(int admin_id);
    // 添加管理员
    public void addAdmin(Admin admin);
    // 修改管理员信息
    public void editAdmin(Admin admin);
    // 删除管理员账号
    public void removeAdmin(int admin_id);
    // 获取管理员权限
    public int getAdminPower(int admin_id);
    // 获取管理员是否启用
    public int getAdminState(int admin_id);
    // 获取管理员名称
    public String getAdminName(int admin_id);
}
