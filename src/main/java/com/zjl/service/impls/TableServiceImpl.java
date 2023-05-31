package com.zjl.service.impls;

import com.zjl.dao.TableDao;
import com.zjl.entity.Table;
import com.zjl.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService {
    @Autowired
    TableDao tableDao;

    @Override
    public List<Table> getAllTable(String query, int pageNum, int pageSize) {
        return tableDao.getAllTable(query, pageNum, pageSize);
    }

    @Override
    public List<Table> getTableList(String table_category, Boolean state) {
        return tableDao.getTableList(table_category, state);
    }

    @Override
    public int tableTotal() {
        return tableDao.tableTotal();
    }

    @Override
    public String selectLastTableId() {
        return tableDao.selectLastTableId();
    }

    @Override
    public void addTable(String table_id, String table_category, String person, Boolean state) {
        tableDao.addTable(table_id, table_category, person, state);
    }

    @Override
    public void deleteTable(String table_id) {
        tableDao.deleteTable(table_id);
    }

    @Override
    public void editTable(int table_id, Boolean state) {
        tableDao.editTable(table_id, state);
    }
}
