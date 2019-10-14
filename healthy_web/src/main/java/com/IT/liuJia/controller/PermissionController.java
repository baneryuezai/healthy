package com.IT.liuJia.controller;

import com.IT.liuJia.entity.PageResult;
import com.IT.liuJia.entity.QueryPageBean;
import com.IT.liuJia.entity.Result;
import com.IT.liuJia.pojo.Permission;
import com.IT.liuJia.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 包名:com.itheima.controller
 * 作者:金智伟
 * 日期2019-10-13  15:37
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    /**
     * @param
     * @Method 添加权限
     * @Return
     * @Date 15:41
     */
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission) {
        permissionService.add(permission);
        return new Result(true, "添加权限成功");
    }

    @PostMapping("/findpage")
    public Result findpage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Permission> pageResult = permissionService.findPage(queryPageBean);
        return new Result(true,"获得权限列表成功", pageResult);

    }

    @PostMapping("/findPermissionById")
    public Result findPermissionById(int id) {
        Permission permission = permissionService.findPermissionById(id);
        return new Result(true,null,permission);

    }

    @PostMapping("/updatePermission")
    public  Result  updatePermission(@RequestBody Permission permission){
        permissionService.updatePermission(permission);
        return new Result(true,"修改权限成功");
    }
    @PostMapping("/deleteById")
    public Result deletePermissionById(int id){
        permissionService.deletePermissionById(id);
        return new Result(true, "删除权限成功");

    }

}
