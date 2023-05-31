package com.zjl.service.impls;

import com.zjl.dao.MenuDao;
import com.zjl.entity.FirstMenu;
import com.zjl.entity.SecondMenu;
import com.zjl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;

    @Override
    public List<FirstMenu> getFirstMenu() {
        return menuDao.getFirstMenu();
    }

    @Override
    public List<SecondMenu> getSecondMenu(int first_id) {
        return menuDao.getSecondMenu(first_id);
    }
}
