package com.IT.liuJia.controller;
import com.IT.liuJia.constant.MessageConstant;
import com.IT.liuJia.constant.RedisConstant;
import com.IT.liuJia.entity.Result;
import com.IT.liuJia.pojo.Package;
import com.IT.liuJia.service.PackageService;
import com.IT.liuJia.util.QiNiuUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
/**
 * 包名: com.IT.liuJia.controller
 * 作者: JiaLiu
 * 日期: 2019-09-27   21:12
 */
@RestController
@RequestMapping("/package")
public class Package2Controller {
    @Reference
    private PackageService packageService;

    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/getPackage")
    public Result getPackage(){
//1.获取jedis对象
        Jedis jedis = jedisPool.getResource();
        //2.判断redis是否有套餐列表的数据
        String value = jedis.get(RedisConstant.SETMEAL_PACKAGELIST_RESOURCES);
        if(value == null || "".equals(value)){
            //3.说明redis中没有数据,那么从数据库中查询
            List<Package> list = packageService.findAll();
            if(null != list){
                list.forEach(pkg -> {
                    //取出list中的每一个元素赋值给pkg变量
                    pkg.setImg("http://" + QiNiuUtil.DOMAIN + "/" + pkg.getImg());
                });
            }
            //4.设置到redis中,把集合转换成json
            value = JSONArray.parseArray(JSON.toJSONString(list)).toString();
            jedis.set(RedisConstant.SETMEAL_PACKAGELIST_RESOURCES,value);
        }
        //5.归还连接
        jedis.close();
        //6.已存入redis,把json转换成list集合
        List<Package> packageList = JSONObject.parseArray(value,Package.class);
        //7.考虑到web层系统管理员会增删改套餐列表,所以web层的packageController的增删改都应清除redis中的套餐列表的缓存
        return new Result(true, MessageConstant.QUERY_PACKAGELIST_SUCCESS,packageList);
    }
    @RequestMapping("/findById")
    public Result findById(int id){
        //1.获取jedis
        Jedis jedis = jedisPool.getResource();
        //2.判断redis中是否存在套餐详情缓存
        String value = jedis.get(RedisConstant.SETMEAL_PACKAGE_RESOURCES + "_" + id);
        if(value == null || "".equals(value)){
            //redis中不存在
            //3.查询数据库,存入redis
            Package pkg = packageService.findById(id);
            pkg.setImg("http://" + QiNiuUtil.DOMAIN + "/" + pkg.getImg());
            //4.对象转换成json
            value = JSON.toJSONString(pkg);
            jedis.set(RedisConstant.SETMEAL_PACKAGE_RESOURCES + "_" + id,value);
        }
        //5.归还连接
        jedis.close();
        //6.已存入redis,把json转换成package对象
        JSONObject pkg = JSON.parseObject(value);
        return new Result(true,MessageConstant.QUERY_PACKAGE_SUCCESS,pkg);
    }
    @PostMapping("/findByPkgId")
    public Result findByPkgId(int id){
        Package pkg=packageService.findByPkgId(id);
        pkg.setImg("http://"+QiNiuUtil.DOMAIN +"/"+pkg.getImg());
        return new Result(true,MessageConstant.QUERY_PACKAGE_SUCCESS,pkg);
    }
}
