package com.fjt.dao.impl;

import com.fjt.dao.BaseAdminDao;
import com.fjt.util.DruidUtil;
import java.util.Date;
import java.sql.*;

/**
 * 相当于BaseAdminDaoImpl
 */
public class AdminDaoMysql implements BaseAdminDao {
    //也可以传入当前时间Now()//用于更新的登录的时间
    private static final String SQL_UPDATE_LOGIN_TIME = "update eadmin set logintime=?,loginip=? where username=?";
    private static final String SQL_LOGIN = "select id from eadmin where username=? and password=?";
    /**
     * 更新登录的时间
     * 根据管理员的用户名来更新登录时间和登录ip
     * 时间能够再代码中直接获取
     *
     * @param username
     * @param date
     * @param ip
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //1. 获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2. 预编译sql语句
        //这里的sql写成常量
        try {
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            //3.填充参数
            //new java.sql.Date(date.getTime())由util的date转换成sql的date
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,ip);
            state.setString(3,username);
            //4.执行
            state.executeUpdate();//修改操作，执行语句


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //5.释放资源
            //德鲁伊连接池提供的关闭资源的方法。
            DruidUtil.close(conn,state,null);
        }
    }

    /**
     * 管理员根据账号密码登录
     *
     * @param username 账号
     * @param password 密码
     * @return 登录结果，true表示登录成功
     */
    @Override
    public boolean login(String username, String password) {
        //1.获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            //2.预编译sql语句
            state = conn.prepareStatement(SQL_LOGIN);
            //3.填充数据
            state.setString(1,username);
            state.setString(2,password);
            //4.执行并获取结果
            rs = state.executeQuery();

            //5.根据查询结果，返回true
            return rs.next();//如果查询有结果，那么就能走下一行就是true
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6. 释放资源
            DruidUtil.close(conn,state,rs);
        }
        return false;
    }
}
