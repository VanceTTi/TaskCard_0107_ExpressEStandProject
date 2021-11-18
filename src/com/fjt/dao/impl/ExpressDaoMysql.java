package com.fjt.dao.impl;

import com.fjt.bean.Express;
import com.fjt.dao.BaseExpressDao;
import com.fjt.exception.DuplicateCodeException;
import com.fjt.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoMysql implements BaseExpressDao {
    /*
    用于查询数据库中的全部快递(总数+新增)，待取件快递（总数+新增）
    count(id) date1_size  //总数，有多少id就有多少快递，
    count(to_days(intime)=to_days(now()) or null) date1_day //今天新增多少，使用to_days传入数据库中intime与现在时间相对比，有多少条，需要null，因为有空的情况
    count(status=0 or null) date2_size //status状态是0的有多少条，待取件有多少
    count(to_days(intime)=to_days(nuw()) and status=0 or null) date2_day  //今天新增了多少待取件的快递
     */
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(ID) data1_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) OR NULL) data1_day," +
            "COUNT(STATUS=0 OR NULL) data2_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) data2_day" +
            " FROM EXPRESS";
    //快递员总数
    public static final String SQL_CONSOLE_COURIER = "SELECT COUNT(courierid) courier_size , COUNT(TO_DAYS(registrationdate)=TO_DAYS(NOW()) OR NULL) courier_day FROM COURIER";
    //用户总数
    public static final String SQL_CONSOLE_USER = "SELECT COUNT(UID) user_size,COUNT(TO_DAYS(registrationdate)=TO_DAYS(NOW()) OR NULL) user_day FROM user";

    //用于查询数据库中的 所有 快递信息
    //public static final String SQL_FIND_ALL = "select * from express";
    public static final String SQL_FIND_ALL = "SELECT * FROM EXPRESS";
    //用于 分页 查询数据库中的快递信息
    //public static final String SQL_FIND_LIMIT = "select * from express limit ?,?";
    public static final String SQL_FIND_LIMIT = "SELECT * FROM EXPRESS LIMIT ?,?";
    //通过取件码查询快递信息
    public static final String SQL_FIND_BY_CODE = "select * from express where code=?";
    //通过快递单号查询快递信息
    public static final String SQL_FIND_BY_NUMBER = "select * from express where number=?";
    //通过录入人手机号查询查询快递信息
    public static final String SQL_FIND_BY_SYSPHONE = "select * from express where sysphone=?";
    //通过用户手机号查询快递信息
    public static final String SQL_FIND_BY_USERPHONE = "select * from express where userphone=?";

    //通过用户手机号查询快递信息
    public static final String SQL_FIND_BY_USERPHONE_AND_STATUS = "select * from express where userphone=? and status=?";

    //录入快递
    public static final String SQL_INSERT = "insert into express (number,username,userphone,company,code,intime,status,sysphone) values(?,?,?,?,?,now(),0,?)";
    //快递修改，只修改单号、姓名、公司，手机号的修改选择删除后再添加的方式
    public static final String SQL_UPDATE = "update express set number=?,username=?,company=?,status=? where id=?";
    //快递的状态码改变（取件）
    public static final String SQL_UPDATE_STATUS = "update express set status=1,outtime=now(),code=null where code=?";
    //快递删除
    public static final String SQL_DELETE = "delete from express where id=?";


    /**
     * 用于查询数据库中的全部快递(总数+新增)，待取件快递（总数+新增）
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     * 返回值中数组中有数组，且都是key：value的形式
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String,Integer>> data = new ArrayList<>();
        Connection conn = null;//通过德鲁伊来获取连接
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            //1.  获取数据库连接
            conn = DruidUtil.getConnection();
            //2.  预编译SQL语句
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.  填充参数
            //无参数填充
            //4.  执行SQL语句
            resultSet = state.executeQuery();
            //5.  获取执行结果
            if (resultSet.next()) {
                int data1_size = resultSet.getInt("data1_size");
                int data1_day = resultSet.getInt("data1_day");
                int data2_size = resultSet.getInt("data2_size");
                int data2_day = resultSet.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);
                data.add(data1);
                data.add(data2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.  资源的释放
            DruidUtil.close(conn,state,resultSet);
        }
        return data;
    }

    /**
     * 查询快递员总数和快递员当天注册数
     * @return
     */
    public List<Map<String, Integer>> consoleCourier() {
        ArrayList<Map<String,Integer>> data = new ArrayList<>();
        Connection conn = null;//通过德鲁伊来获取连接
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            //1.  获取数据库连接
            conn = DruidUtil.getConnection();
            //2.  预编译SQL语句
            state = conn.prepareStatement(SQL_CONSOLE_COURIER);
            //3.  填充参数
            //无参数填充
            //4.  执行SQL语句
            resultSet = state.executeQuery();
            //5.  获取执行结果
            if (resultSet.next()) {
                int courier_size = resultSet.getInt("courier_size");
                int courier_day = resultSet.getInt("courier_day");
                Map data1 = new HashMap();
                data1.put("courier_size",courier_size);
                data1.put("courier_day",courier_day);
                data.add(data1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.  资源的释放
            DruidUtil.close(conn,state,resultSet);
        }
        return data;
    }

    /**
     * 查询用户总数和用户当天注册数
     *
     * @return
     */
    @Override
    public List<Map<String, Integer>> consoleUser() {
        ArrayList<Map<String,Integer>> data = new ArrayList<>();
        Connection conn = null;//通过德鲁伊来获取连接
        PreparedStatement state = null;
        ResultSet resultSet = null;
        try {
            //1.  获取数据库连接
            conn = DruidUtil.getConnection();
            //2.  预编译SQL语句
            state = conn.prepareStatement(SQL_CONSOLE_USER);
            //3.  填充参数
            //无参数填充
            //4.  执行SQL语句
            resultSet = state.executeQuery();
            //5.  获取执行结果
            if (resultSet.next()) {
                int user_size = resultSet.getInt("user_size");
                int user_day = resultSet.getInt("user_day");
                Map data1 = new HashMap();
                data1.put("user_size",user_size);
                data1.put("user_day",user_day);
                data.add(data1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.  资源的释放
            DruidUtil.close(conn,state,resultSet);
        }
        return data;
    }


    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页标记,true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            //1.获取数据库连接
            conn = DruidUtil.getConnection();
            //2.预编译SQL语句
            //2.1这里的查询所有需要判定是否分页，
            if (limit) {
                preparedStatement = conn.prepareStatement(SQL_FIND_LIMIT);
            //3.参数填充
                preparedStatement.setInt(1,offset);
                preparedStatement.setInt(2,pageNumber);
            } else {
                preparedStatement = conn.prepareStatement(SQL_FIND_ALL);
            }
            //4.执行SQL语句
            resultSet = preparedStatement.executeQuery();
            //5. 获取执行的结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String userName = resultSet.getString("username");
                String userPhone = resultSet.getString("userphone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("intime");
                Timestamp outTime = resultSet.getTimestamp("outtime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysphone");
                Express express = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6. 释放资源
            DruidUtil.close(conn,preparedStatement,resultSet);
        }
        return data;
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    @Override
    public Express findByNumber(String number) {
        //1.获取数据库连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //2.预编译SQL语句
            preparedStatement = conn.prepareStatement(SQL_FIND_BY_NUMBER);
            //3.填充参数
            preparedStatement.setString(1,number);
            //4.执行SQL语句
            resultSet = preparedStatement.executeQuery();
            //5. 获得执行的结果
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String userPhone = resultSet.getString("userphone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("intime");
                Timestamp outTime = resultSet.getTimestamp("outtime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysphone");
                Express express = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone);
                return express;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(conn,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    @Override
    public Express findByCode(String code) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();

            //2.预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_CODE);
            //3.填充参数
            preparedStatement.setString(1,code);

            //4.执行SQL语句
            resultSet = preparedStatement.executeQuery();
            //5.获取参数
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String userName = resultSet.getString("username");
                String userPhone = resultSet.getString("userphone");
                String company = resultSet.getString("company");
                Timestamp inTime = resultSet.getTimestamp("intime");
                Timestamp outTime = resultSet.getTimestamp("outtime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysphone");
                Express express = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone);
                return express;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        ArrayList<Express> data = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.获取连接
            connection = DruidUtil.getConnection();
            //2.预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_USERPHONE);
            //3.填充数据
            preparedStatement.setString(1,userPhone);
            //4.执行sql语句
            resultSet = preparedStatement.executeQuery();
            //5.获取数据
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String userName = resultSet.getString("username");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("intime");
                Timestamp outTime = resultSet.getTimestamp("outtime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysphone");
                Express express = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @param status    状态码
     * @return 查询的是没有收货的快递信息
     */
    @Override
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        ArrayList<Express> data = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.获取连接
            connection = DruidUtil.getConnection();
            //2.预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_USERPHONE_AND_STATUS);
            //3.填充数据
            preparedStatement.setString(1,userPhone);
            preparedStatement.setInt(2,status);
            //4.执行sql语句
            resultSet = preparedStatement.executeQuery();
            //5.获取数据
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String userName = resultSet.getString("username");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("intime");
                Timestamp outTime = resultSet.getTimestamp("outtime");
                String sysPhone = resultSet.getString("sysphone");
                Express express = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;

    }

    /**
     * 根据录入人手机号码，查询他所有的快递信息
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> data = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_SYSPHONE);
            //3.填充参数
            preparedStatement.setString(1,sysPhone);
            //4.执行sql语句
            resultSet = preparedStatement.executeQuery();
            //5.获取执行结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String userName = resultSet.getString("username");
                String userPhone = resultSet.getString("userphone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("intime");
                Timestamp outTime = resultSet.getTimestamp("outtime");
                int status = resultSet.getInt("status");
                Express express = new Express(id,number,userName,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }


    /**
     * 快递的录入
     * sql语句：insert into express (number,username,userphone,company,code,intime,status,sysphone) values(?,?,?,?,?,now(),0,?)
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     * @throws DuplicateCodeException 自定义异常，主要针对code重复而自定义抛出的异常。
     * 异常抛出后，程序员，根据异常处理重复问题：重新生成code
     */
    @Override
    public boolean insert(Express e) throws DuplicateCodeException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取连接数据库
            connection = DruidUtil.getConnection();
            //2.预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            //3.填充参数
            preparedStatement.setString(1,e.getNumber());
            preparedStatement.setString(2,e.getUserName());
            preparedStatement.setString(3,e.getUserPhone());
            preparedStatement.setString(4,e.getCompany());
            preparedStatement.setString(5,e.getCode());
            preparedStatement.setString(6,e.getSysPhone());
            //4.执行sql语句，并获取执行结果
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException ex) {
            //ex.printStackTrace();
            System.out.println(ex.getMessage());
            if (ex.getMessage().endsWith("for key 'code'")){
                //因为取件码重复而出现的异常
                DuplicateCodeException e2 = new DuplicateCodeException(ex.getMessage());
                throw e2;
            } else {
                ex.printStackTrace();
            }
        } finally {
            //5.释放资源
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    /**
     * 快递的修改
     * update express set number=?,username=?,company=?,status=? where id=?
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number，company，username，userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean update(int id, Express newExpress) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1. 获取数据库连接
            connection = DruidUtil.getConnection();
            //2.  预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            //3. 填充参数
            preparedStatement.setString(1, newExpress.getNumber());
            preparedStatement.setString(2, newExpress.getUserName());
            preparedStatement.setString(3, newExpress.getCompany());
            preparedStatement.setInt(4, newExpress.getStatus());
            preparedStatement.setInt(5, id);
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

    /**
     * 更改快递的状态，为1表示取件完成
     *
     * @param code 要修改的取件码，根据取件码来判定快递的状态
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean updateStatus(String code) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 1. 获取连接
            connection = DruidUtil.getConnection();
            //2. 预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS);
            //3.填充数据
            preparedStatement.setString(1,code);
            //4.运行SQL语句
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
     * 根据id,删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return true：删除成功，false：删除失败
     */
    @Override
    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = DruidUtil.getConnection();
            //2.预编译SQL语句
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            //3.填充数据
            preparedStatement.setInt(1,id);
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
