package com.IT.liuJia.job;

import com.IT.liuJia.constant.RedisConstant;
import com.IT.liuJia.dao.OrderSettingDao;
import com.IT.liuJia.util.DateUtils;
import com.IT.liuJia.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Set;

/**
 * 包名:com.itheima.job
 * 作者:LiuR
 * 日期:2019-09-26 20:03
 */

public class MyJob {

    @Autowired
    public JedisPool jedisPool;

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 定时清理七牛云上的垃圾图片
     */
    public void doAbc(){
        Jedis jedis = jedisPool.getResource();
        //1. 取出Redis中所有图片集合 - 保存到数据库的图片集合
        Set<String> need2Delete = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //2. 调用QiNiuUtil删除服务器上的图片
        QiNiuUtil.removeFiles(need2Delete.toArray(new String[] {}));
        //3. 成功要删除Redis中的缓存:所有图片集合,保存到数据库的图片集合
        jedis.del(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);

    }

    /**
     * 定时清理预约设置的历史数据
     */
    public void clearOrderSetting(){
        // 当前时间的日期
        // String currentTime = DateUtils.date2String(new Date(), DateUtils.YMD);

        // 测试代码
        String currentTime = "2019-03-07";

        orderSettingDao.clearOrderSetting(currentTime);
    }

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-job.xml");
    }
}
