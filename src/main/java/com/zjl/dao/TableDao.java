package com.zjl.dao;

import com.zjl.entity.Table;

import java.util.List;

public interface TableDao {
    // 获取餐桌信息
    public List<Table> getAllTable(String query, int pageNum, int pageSize);
    // 获取餐桌列表
    public List<Table> getTableList(String table_category, Boolean state);
    // 餐桌总数
    public int tableTotal();
    // 查询餐桌最后的 id
    public String selectLastTableId();
    // 添加餐桌
    public void addTable(String table_id, String table_category, String person, Boolean state);
    // 删除餐桌信息
    public void deleteTable(String table_id);
    // 修改餐桌的状态
    public void editTable(int table_id, Boolean state);
}
