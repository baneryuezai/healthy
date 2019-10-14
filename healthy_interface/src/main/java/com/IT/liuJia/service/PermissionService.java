package com.IT.liuJia.service;

import com.IT.liuJia.entity.PageResult;
import com.IT.liuJia.entity.QueryPageBean;
import com.IT.liuJia.exception.MyException;
import com.IT.liuJia.pojo.Permission;
;

/**
 * 包名:com.itheima.service
 * 作者:金智伟
 * 日期2019-10-13  15:39
 */
public interface PermissionService {
    void add(Permission permission);

    PageResult<Permission> findPage(QueryPageBean queryPageBean);


    Permission findPermissionById(int id);

    void updatePermission(Permission permission);

    void deletePermissionById(int id)  throws MyException;
}
