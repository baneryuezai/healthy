package com.IT.liuJia.controller;

import com.IT.liuJia.constant.MessageConstant;
import com.IT.liuJia.entity.Result;
import com.IT.liuJia.pojo.Menu;
import com.IT.liuJia.service.MenuService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;

/**
 * 包名:com.itheima.controller
 * 作者:金智伟
 * 日期2019-10-13  09:25
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

     @GetMapping("/findmenu")
    public Result getmenuByusername(){
         String username = SecurityContextHolder.getContext().getAuthentication().getName();
         LinkedHashSet<Menu> menus  = menuService.getmenuByusername(username);

         LinkedHashSet<Menu> menus1 = new LinkedHashSet<Menu>();

         if (menus != null) {
             Menu menu = new Menu();
             menu.setPath("1");
             menu.setName("工作台");
             menu.setIcon("fa-dashboard");
             menus1.add(menu);
             for (Menu menu1 : menus) {
                 menus1.add(menu1);
             }
         }

         return  new Result(true, MessageConstant.GET_MENU_SUCCESS,menus1);

     }
}
