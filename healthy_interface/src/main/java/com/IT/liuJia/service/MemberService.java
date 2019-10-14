package com.IT.liuJia.service;

import com.IT.liuJia.pojo.Member;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberService {
    /*
    * 手机号查询会员
    * */
    Member findByTelephone(String telephone);
    /*
    * 新增会员
    * */
    void add(Member member);

    Map<String,List<Object>> getMemberReport();

	Map<String,List<Object>> getMouthMemberReport(Date one , Date two);

    /**
     * 男女性别比例饼状图
     * @return
     */
    Map<String,Object> findMemberOfGender();

    /**
     * 年龄段比例饼状图
     * @return
     */
    Map<String,Object> getMemberOfAge();
}
