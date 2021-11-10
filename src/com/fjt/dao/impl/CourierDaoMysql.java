package com.fjt.dao.impl;

import com.fjt.bean.Courier;
import com.fjt.dao.BaseCourierDao;
import com.fjt.util.DruidUtil;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import static com.fjt.dao.impl.ExpressDaoMysql.SQL_DELETE;

public class CourierDaoMysql implements BaseCourierDao {
    //用于查询所有快递员信息
    private static final String SQL_FIND_ALL = "SELECT * FROM COURIER";
    //用于分页查询数据库中快递信息
    private static final String SQL_FIND_LIMIT = "SELECT * FROM COURIER LIMIT ?,?";
    //查询总数
    private static final String SQL_TOTAL = "SELECT COUNT(courierid) c_total FROM COURIER";
    //录入快递员信息,指定创建时间等于上次登录时间，等到需要登录的时候再去修改上次登录时间
    private static final String SQL_INSERT = "INSERT INTO COURIER (couriername,courierphone,courieridCard,courierpassword,sendqty,registrationDate,LastLoginTime) VALUES(?,?,?,?,0,now(),now())";
    //根据快递员手机号来获取快递员信息
    private static final String SQL_FIND_COURIERPHONE = "SELECT * FROM COURIER WHERE courierPhone=?";
    //根据id修改快递员信息
    private static final String SQL_UPDATE = "UPDATE COURIER SET couriername=?,courierphone=?,courieridCard=?,courierpassword=? WHERE courierid=?";

    private static final String SQL_DELETE = "DELETE FROM COURIER WHERE courierid=?";

    /**
     * 查询快递员的总条数
     * @return 总数
     */
    @Override
    public int total() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int c_total = 0;
        try {
            //1.获取连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            preparedStatement = connection.prepareStatement(SQL_TOTAL);
            //3. 填充参数(无)
            //4.执行sql
            resultSet = preparedStatement.executeQuery();
            //5.获取执行结果
            if (resultSet.next()) {
                c_total= resultSet.getInt("c_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return c_total;
    }

    /**
     * 用于查询所有快递员
     * @param limit      是否分页标记,true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 查询的数量
     * @return 快递员的集合
     */
    @Override
    public List<Courier> findCourierAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data = new ArrayList<>();
        PreparedStatement preparedStatement = null;
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
                int courierId = resultSet.getInt("courierid");//编号
                String courierName = resultSet.getString("couriername");//姓名
                String courierPhone = resultSet.getString("courierphone");//手机号码
                String courierIdCard = resultSet.getString("courieridCard");//身份证
                String courierPassword = resultSet.getString("courierpassword");//密码
                String sendQty = resultSet.getString("sendqty");//派件数
                Timestamp registrationDate = resultSet.getTimestamp("registrationdate");//注册日期
                Timestamp lastLoginTime = resultSet.getTimestamp("lastlogintime");//上次登录时间
                Courier courier = new Courier(courierId,courierName,courierPhone,courierIdCard,courierPassword,sendQty,registrationDate,lastLoginTime);
                data.add(courier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }

    /**
     * 快递员的录入，
     *INSERT INTO COURIER (couriername,courierphone,courieridCard,courierpassword,sendqty,registrationDate,LastLoginTime) VALUES(?,?,?,?,0,now(),now())
     * @param courier 录入的快递员对象
     * @return ture 为成功，  false为失败
     */
    @Override
    public boolean courierInsert(Courier courier) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.编译sql语句
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            //3.填充参数
            preparedStatement.setString(1,courier.getCourierName());
            preparedStatement.setString(2,courier.getCourierPhone());
            preparedStatement.setString(3,courier.getCourierIdCard());
            preparedStatement.setString(4,courier.getCourierPassword());
            //4.执行sql语句，并获取执行结果
        return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5.释放资源
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    /**
     * 根据手机号来查询快递员信息
     *
     * @param phone 手机号码
     * @return 返回快递员信息的对象
     */
    @Override
    public Courier findByPhone(String phone) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Courier courier = null;
        try {
            //1.获取连接
            connection = DruidUtil.getConnection();
            //2.编译sql语句
            preparedStatement = connection.prepareStatement(SQL_FIND_COURIERPHONE);
            //3.填充参数
            preparedStatement.setString(1,phone);

            //4.执行sql语句
            resultSet = preparedStatement.executeQuery();

            //5.获取执行结果
            courier = null;
            if (resultSet.next()){
                int courierId = resultSet.getInt("courierid");//编号
                String courierName = resultSet.getString("couriername");//姓名
                String courierPhone = resultSet.getString("courierphone");//手机号码
                String courierIdCard = resultSet.getString("courieridCard");//身份证
                String courierPassword = resultSet.getString("courierpassword");//密码
                String sendQty = resultSet.getString("sendqty");//派件数
                Timestamp registrationDate = resultSet.getTimestamp("registrationdate");//注册日期
                Timestamp lastLoginTime = resultSet.getTimestamp("lastlogintime");//上次登录时间
                courier = new Courier(courierId,courierName,courierPhone,courierIdCard,courierPassword,sendQty,registrationDate,lastLoginTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return courier;
    }

    /**
     * 根据id修改快递员信息
     *UPDATE COURIER SET couriername=?,courierphone=?,courieridCard=?,courierpassword=? where courierid=?
     * @param id id
     * @param newCourier  用户新输入的
     * @return  t成功，f失败
     */
    @Override
    public boolean update(int id, Courier newCourier) {
        //System.out.println("输入的id："+id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译sql语句
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            //3.填充参数
            System.out.println(newCourier.getCourierName());
            System.out.println(newCourier.getCourierPhone());
            System.out.println(newCourier.getCourierIdCard());
            System.out.println(newCourier.getCourierPassword());
            preparedStatement.setString(1,newCourier.getCourierName());
            preparedStatement.setString(2,newCourier.getCourierPhone());
            preparedStatement.setString(3,newCourier.getCourierIdCard());
            preparedStatement.setString(4,newCourier.getCourierPassword());
            preparedStatement.setInt(5,id);
            //4.执行sql语句
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    /**
     * 根据id,删除快递信息
     *
     * @param courierId 要删除的快递员的id
     * @return
     */
    @Override
    public boolean delete(int courierId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            //3.填充数据
            preparedStatement.setInt(1,courierId);
            //4.运行sql语句
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }


}
