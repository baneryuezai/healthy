package com.IT.liuJia.dao;

import com.IT.liuJia.pojo.Permission;
import com.github.pagehelper.Page;
/**
 * 包名:com.itheima.dao
 * 作者:金智伟
 * 日期2019-10-13  15:40
 */
public interface PermissionDao {
    void add(Permission permission);

    Page<Permission> findPage(String queryString);


    Permission findPermissionById(int id);

    void updatePermission(Permission permission);

    long findCountPermissionByid(int id);

    void deleteById(int id);
}
