package com.IT.liuJia.service;

import com.IT.liuJia.dao.PermissionDao;
import com.IT.liuJia.entity.PageResult;
import com.IT.liuJia.entity.QueryPageBean;
import com.IT.liuJia.exception.MyException;
import com.IT.liuJia.pojo.Permission;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 包名:com.itheima.service.impl
 * 作者:金智伟
 * 日期2019-10-13  15:40
 */
@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
   private PermissionDao permissionDao;
    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public PageResult<Permission> findPage(QueryPageBean queryPageBean) {
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //查询语句会被分页
        Page<Permission> page = permissionDao.findPage(queryPageBean.getQueryString());
        PageResult<Permission> permissionPageResult = new PageResult<Permission>(page.getTotal(),page.getResult());
        return permissionPageResult;

    }

    @Override
    public Permission findPermissionById(int id) {
        return  permissionDao.findPermissionById(id);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }

    @Override
    public void deletePermissionById(int id) throws MyException {
        long  count =  permissionDao.findCountPermissionByid(id);
        if (count > 0) {
            //   int a=10 /0;
            throw  new MyException("该权限被使用不能删除");
        }
        permissionDao.deleteById(id);
    }


}
