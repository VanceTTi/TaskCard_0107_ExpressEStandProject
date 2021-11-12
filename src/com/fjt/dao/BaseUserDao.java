package com.fjt.dao;


import com.fjt.bean.Courier;
import com.fjt.bean.User;

import java.util.List;

public interface BaseUserDao {

    /**
     * 查询快递员的总条数
     * @return 总数
     */
    int total();

    /**
     * 用于查询所有快递员
     * @param limit 是否分页标记,true表示分页，false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 查询的数量
     * @return 快递员的集合
     */
    List<User> findUserAll(boolean limit, int offset, int pageNumber);


    /**
     * 用户的录入，
     * @param user 录入的用户对象
     * @return ture 为成功，  false为失败
     */
    public boolean userInsert(User user);

    /**
     * 根据手机号码查询用户
     * @param uPhone
     * @return
     */
    User findByPhone(String uPhone);

    /**
     * 根据id修改快递员信息
     * @param id uid用户的id
     * @param newUser 新的用户信息
     * @return
     */
    boolean update(int id ,User newUser);

    /**
     * 根据id来删除用户信息
     * @param uid
     * @return
     */
    boolean delete(int uid);

}
