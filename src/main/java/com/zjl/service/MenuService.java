package com.zjl.service;

import com.zjl.entity.FirstMenu;
import com.zjl.entity.SecondMenu;

import java.util.List;

public interface MenuService {
    // 获取第一级菜单信息
    public List<FirstMenu> getFirstMenu();
    // 获取第二级菜单信息
    public List<SecondMenu> getSecondMenu(int first_id);
}
