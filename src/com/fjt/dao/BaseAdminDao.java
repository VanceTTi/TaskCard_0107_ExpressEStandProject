package com.fjt.dao;


import java.util.Date;

/**
 * 用于定义数据库中admin表格的操作规范
 */
public interface BaseAdminDao {
    //在用户登录的时候


    /**
     * 更新登录的时间
     * 根据管理员的用户名来更新登录时间和登录ip
     * 时间能够再代码中直接获取
     * @param username
     */
   public void updateLoginTime(String username, Date date , String ip);

    /**
     * 管理员根据账号密码登录
     * @param username 账号
     * @param password 密码
     * @return 登录结果，true表示登录成功
     */
   public boolean login(String username,String password);

}
