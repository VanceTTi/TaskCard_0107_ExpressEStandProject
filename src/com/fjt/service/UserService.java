package com.fjt.service;

import com.fjt.bean.Courier;
import com.fjt.bean.User;
import com.fjt.dao.BaseUserDao;
import com.fjt.dao.impl.UserDaoMysql;

import java.util.List;

public class UserService {
    private static BaseUserDao dao = new UserDaoMysql();


    /**
     * 查询用户的总条数
     * @return 总数
     */
    public static int total(){
        return dao.total();
    }


    /**
     * 用于查询所有用户
     * @param limit 是否分页标记,true表示分页，false表示查询所有用户信息
     * @param offset SQL语句的起始索引
     * @param pageNumber 查询的数量
     * @return 用户的集合
     */
    public static List<User> findUserAll(boolean limit, int offset, int pageNumber) {
        return dao.findUserAll(limit,offset,pageNumber);
    }


    /**
     * 用户的录入，
     * @param user 录入的用户对象
     * @return ture 为成功，  false为失败
     */
    public static boolean userInsert(User user){
        return dao.userInsert(user);
    }

    /**
     * 根据手机号码查询用户
     * @param uPhone
     * @return
     */
    public static User findByPhone(String uPhone){
        return dao.findByPhone(uPhone);
    }

    /**
     *根据id修改用户信息
     * @param id uid
     * @param newUser
     * @return
     */
    public static boolean update(int id ,User newUser){
        return dao.update(id,newUser);
    }

    /**
     * 根据id删除用户信息
     * @param uid
     * @return
     */
    public static boolean delete(int uid){
        return dao.delete(uid);
    }

}


