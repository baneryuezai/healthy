package com.IT.liuJia.service;

import com.IT.liuJia.dao.MenuDao;
import com.IT.liuJia.pojo.Menu;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * 包名:com.itheima.service.impl
 * 作者:金智伟
 * 日期2019-10-13  09:28
 */
@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
   private MenuDao menuDao;

    @Override
    public  LinkedHashSet<Menu>  getmenuByusername(String username) {

        LinkedHashSet<Menu> menus = new LinkedHashSet<Menu>();
        //查询主菜单集合
       List<Menu> parentMenus = menuDao.findParentMenus(username);
      //查询子菜单
        if (parentMenus != null && parentMenus.size() > 0) {
            for (Menu parentMenu : parentMenus) {
                //查询子菜单
                List<Menu> childrenMenus = menuDao.findchildrenMenus(parentMenu.getId());
                parentMenu.setChildren(childrenMenus);
                menus.add(parentMenu);
            }
        }

            return  menus;

    }
}
