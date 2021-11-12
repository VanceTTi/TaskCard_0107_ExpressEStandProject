package com.fjt.dao.impl;

import com.fjt.bean.User;
import com.fjt.dao.BaseUserDao;
import com.fjt.util.DruidUtil;

import java.rmi.server.UID;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMysql implements BaseUserDao {
    //1.获取数据库连接
    //2.预编译sql语句
    //3.参数填充
    //4.执行sql语句
    //5.获取执行结果
    //6.释放资源

    //查询总数
    private static final String SQL_TOTAL = "SELECT COUNT(uid) u_total FROM USER";
    //用于查询所有用户信息
    private static final String SQL_FIND_ALL = "SELECT * FROM USER";
    //用于分页查询数据库中用户信息
    private static final String SQL_FIND_LIMIT = "SELECT * FROM USER LIMIT ?,?";
    //录入用户信息
    private static final String SQL_INSERT = "INSERT INTO USER (nickname,uphone,upassword,registrationDate,LastLoginTime) VALUES(?,?,?,now(),now())";
    //根据电话号码查询用户
    private static final String SQL_FIND_UPHONE = "SELECT * FROM USER WHERE uphone=?";
    //根据id修改用户信息
    private static final String SQL_UPDATE = "UPDATE USER SET nickname=?,uphone=?,upassword=? where uid=?";
    //根据id删除用户信息
    private static final String SQL_DELETE = "DELETE FROM USER WHERE uid=?";

    /**
     * 查询用户的总条数
     *
     * @return 总数
     */
    @Override
    public int total() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int u_total = 0;
        try {
            //1.获取连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            preparedStatement = connection.prepareStatement(SQL_TOTAL);
            //3.填充参数（无）
            //4.编译sql语句
            resultSet = preparedStatement.executeQuery();
            //5.获取结果集
            if (resultSet.next()) {
                u_total = resultSet.getInt("u_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }

        return u_total;
    }

    /**
     * 用于查询所有用户
     *
     * @param limit      是否分页标记,true表示分页，false表示查询所有用户
     * @param offset     SQL语句的起始索引
     * @param pageNumber 查询的数量
     * @return 用户的集合
     */
    @Override
    public List<User> findUserAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> list = new ArrayList<>();
        PreparedStatement preparedStatement =null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            if (limit) {
                preparedStatement = connection.prepareStatement(SQL_FIND_LIMIT);
                //3.参数填充
                preparedStatement.setInt(1,offset);
                preparedStatement.setInt(2,pageNumber);
            } else {
                preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
            }
            //4.执行sql语句
            resultSet = preparedStatement.executeQuery();
            //5.获取执行结果
            while (resultSet.next()) {
                int uid = resultSet.getInt("uid");
                String nickname = resultSet.getString("nickname");
                String uphone = resultSet.getString("uphone");
                String upassword = resultSet.getString("upassword");
                Timestamp registrationdate = resultSet.getTimestamp("registrationdate");
                Timestamp lastlogintime = resultSet.getTimestamp("lastlogintime");
                User user = new User(uid,nickname,uphone,upassword,registrationdate,lastlogintime);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return list;
    }

    /**
     * 用户的录入，
     *
     * @param user 录入的用户对象
     * @return ture 为成功，  false为失败
     */
    @Override
    public boolean userInsert(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            //3.参数填充
            preparedStatement.setString(1,user.getNickname());
            preparedStatement.setString(2,user.getuPhone());
            preparedStatement.setString(3,user.getuPassword());
            //4.执行sql语句//5.获取执行结果
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    /**
     * 根据手机号码查询用户
     * @param uPhone
     * @return
     */
    @Override
    public User findByPhone(String uPhone) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            preparedStatement = connection.prepareStatement(SQL_FIND_UPHONE);

            //3.参数填充
            preparedStatement.setString(1,uPhone);
            //4.执行sql语句
            resultSet = preparedStatement.executeQuery();
            //5.获取执行结果
            User user = null;
            if (resultSet.next()){
                int uid = resultSet.getInt("uid");
                String nickname = resultSet.getString("nickname");
                String uphone = resultSet.getString("uphone");
                String upassword = resultSet.getString("upassword");
                Timestamp registrationdate = resultSet.getTimestamp("registrationdate");
                Timestamp lastlogintime = resultSet.getTimestamp("lastlogintime");
                user = new User(uid,nickname,uphone,upassword,registrationdate,lastlogintime);
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
       return null;
    }

    /**
     * 根据id修改快递员信息
     * UPDATE USER SET nickname=?,uphone=?,upassword=? where uid=?
     * @param id  uid用户的id
     * @param newUser 新的用户信息
     * @return t成功，f失败
     */
    @Override
    public boolean update(int id, User newUser) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            //3.参数填充
            preparedStatement.setString(1,newUser.getNickname());
            preparedStatement.setString(2,newUser.getuPhone());
            preparedStatement.setString(3,newUser.getuPassword());
            preparedStatement.setInt(4,id);
            //4.执行sql语句//5.获取执行结果
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    /**
     * 根据id来删除用户信息
     *
     * @param uid
     * @return
     */
    @Override
    public boolean delete(int uid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            //3.参数填充
            preparedStatement.setInt(1,uid);
            //4.执行sql语句//5.获取执行结果
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }


}
