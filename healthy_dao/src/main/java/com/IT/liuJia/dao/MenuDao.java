package com.IT.liuJia.dao;



import com.IT.liuJia.pojo.Menu;

import java.util.List;

/**
 * 包名:com.itheima.dao
 * 作者:金智伟
 * 日期2019-10-13  10:00
 */
public interface MenuDao {
    /**
    *@Method 查询主菜单集合
    *@param  
    *@Return 
    *@Date   17:27
    */
    List<Menu> findParentMenus(String username);

    /**
    *@Method 查询子菜单
    *@param  
    *@Return 
    *@Date   17:28
    */
    
    List<Menu> findchildrenMenus(Integer parentMenuId);
}
