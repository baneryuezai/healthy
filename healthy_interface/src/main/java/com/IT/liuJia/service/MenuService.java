package com.IT.liuJia.service;

import com.IT.liuJia.pojo.Menu;
import java.util.LinkedHashSet;

/**
 * 包名:com.itheima.service
 * 作者:金智伟
 * 日期2019-10-13  09:27
 */
public interface MenuService {
    LinkedHashSet<Menu>  getmenuByusername(String username);
}
