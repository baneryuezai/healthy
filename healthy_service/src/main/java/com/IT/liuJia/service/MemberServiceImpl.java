package com.IT.liuJia.service;
import com.IT.liuJia.dao.MemberDao;
import com.IT.liuJia.pojo.Member;
import com.IT.liuJia.util.DateUtils;
import com.IT.liuJia.util.MD5Utils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 包名: com.IT.liuJia.service
 * 作者: JiaLiu
 * 日期: 2019-10-07   07:04
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    /*
    * 手机号查询会员
    * */
    @Override
    public Member findByTelephone(String telephone) {
        Member member = memberDao.findByTelephone(telephone);
        return member;
    }
    /*
    * 新增会员
    * */
    @Override
    public void add(Member member) {
//       还要判断一下是否密码不为空
        if (member.getPassword()!=null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }else {
            memberDao.add(member);
        }
    }
    /*
    * 会员数量统计
    * */
    @Override
    public Map<String, List<Object>> getMemberReport() {
        // {flag,message,data{
//            months:[],
//            memberCount: []
// }}
        //1. 获取上一年份的数据
        //2. 循环12个月，每个月要查询一次
        //3. 再查询得到的数据封装到months list 月份,
//           memberCount list 到这个月份为止会员总数量

        //1. 获取上一年份的数据
        // 日历对象，java中来操作日期时间，当前系统时间
        Calendar car = Calendar.getInstance();
        // 回到12个月以前
        car.add(Calendar.MONTH,-12);
        //2. 循环12个月，每个月要查询一次
//        先建两个集合定义变量来接受两组集合
//        返回的月份数量集合
        List<Object> months =new ArrayList<>();
//        返回的会员数量集合
        List<Object> memberCount=new ArrayList<>();
//        由于数据库日期格式和要显示的日期数据格式不一致,要定义日期类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (int i = 1; i <=12 ; i++) {
            // 计算当前月的值(这个当前月是指在for循环里面要遍历的月份,由于之前已经减一
            // 了,也就是从系统时间月份-12等于要开始的月份,)
            car.add(Calendar.MONTH, 1);
            // 月份的字符串 2019-01
            String monthStr = sdf.format(car.getTime());
            months.add(monthStr);
            // 查询会员数量
            Integer monthCount = memberDao.findMemberCountBeforeDate(monthStr + "-31");
            memberCount.add(monthCount);
        }
            //3. 再查询得到的数据封装到months list 月份, memberCount list 到这个月份为止会员总数量
//            见一个map
            Map<String,List<Object>> map =new HashMap<>();
            map.put("months",months);
            map.put("memberCount",memberCount);
            return map;
    }

    @Override
    public Map<String, Object> findMemberOfGender() {
        List<String> sexList = new ArrayList<>();
        List<Map<String, String>> list = memberDao.findMemberOfGender();
        for (Map map : list) {
            String name = (String) map.get("name");

            if ("1".equals(name)) {
                map.put("name", "男");
                sexList.add("男");
            } else if ("2".equals(name)) {
                map.put("name", "女");
                sexList.add("女");
            } else {
                map.put("name", "未设置性别");
                sexList.add("未设置性别");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sexList", sexList);
        map.put("list", list);
        System.out.println(sexList);
        System.out.println(list);

        return map;
    }

    @Override
    public Map<String, Object> getMemberOfAge() {
        /**
         * 需要数据的格式,
         * {list=[{name=XX,value=xxx},{name=XX,value=xx},{name=XX,value=xx}],list1=[XX,XX,XX]}
         *
         * 现有数据格式
         * {XX:xxx,XX:xxx,XX:xxx}
         */
        //该map(resultMap)是返回前端的map,用来存放多个list
        Map<String, Object> resultMap = new HashMap<>();
        //该list用来存放返回饼状图name和value的Map(mapOfNameAndValue)
        List<Object> list = new ArrayList<>();
        //该list(listOfStage)用来存放返回饼状图的name
        List<String> listOfStageName = new ArrayList<>();

        Map<String, Integer> map = memberDao.findStageCount();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            //该map(mapOfNameAndValue)用来存放返回饼状图name和value
            Map<String, String> mapOfNameAndValue = new HashMap<>();

            //将name和value存入mapOfNameAndValue
            mapOfNameAndValue.put("name", entry.getKey());
            mapOfNameAndValue.put("value", String.valueOf(entry.getValue()));
            //将mapOfNameAndValue存入list
            list.add(mapOfNameAndValue);
            //将name存入listOfStageName
            listOfStageName.add(entry.getKey());
            //将list和listOfStageName存入resultMap中返回前端
            resultMap.put("list", list);
            resultMap.put("listOfStageName", listOfStageName);

        }
        System.out.println(map);
        System.out.println("年龄段" + listOfStageName);
        System.out.println("ListOfResultMap" + list);
        System.out.println("返回前端的数据" + resultMap);
        return resultMap;
    }

	    @Override
    public Map<String, List<Object>> getMouthMemberReport(Date fromDate,Date toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
//        Calendar  from  =  Calendar.getInstance();
//        from.setTime(fromDate);
//        Calendar  to  =  Calendar.getInstance();
//        to.setTime(toDate);
//        int fromYear = from.get(Calendar.YEAR);
//        int fromMonth = from.get(Calendar.MONTH);
//        int toYear = to.get(Calendar.YEAR);
//        int toMonth = to.get(Calendar.MONTH);
//        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth)+1;

        Calendar tempFrom = Calendar.getInstance();
        tempFrom.setTime(fromDate);
        tempFrom.set(tempFrom.get(Calendar.YEAR),tempFrom.get(Calendar.MONTH),1);

        Calendar tempTo = Calendar.getInstance();
        tempTo.setTime(toDate);
        tempTo.set(tempTo.get(Calendar.YEAR),tempTo.get(Calendar.MONTH),2);

        Calendar curr = tempFrom;
        List<Object> months =new ArrayList<>();
        List<Object> memberCount=new ArrayList<>();
        while (curr.before(tempTo)) {
            String monthStr = sdf.format(curr.getTime());
            months.add(monthStr);
            curr.add(Calendar.MONTH,1);
            Integer monthCount = memberDao.findMemberCountMonthDate(monthStr + "-01",sdf.format(curr.getTime())+"-01");
            memberCount.add(monthCount);
        }
        Map<String,List<Object>> map =new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return map;
    }

}
