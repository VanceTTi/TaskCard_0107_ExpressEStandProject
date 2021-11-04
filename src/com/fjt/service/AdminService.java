package com.fjt.service;

import com.fjt.dao.BaseAdminDao;
import com.fjt.dao.impl.AdminDaoMysql;

import java.util.Date;

/**
 * 这里的service也建议用接口的方式，
 * 但是这里为了方便就不用接口了
 */
public class AdminService {
    private static BaseAdminDao dao = new AdminDaoMysql();
    /**
     * 更新登录时间和ip
     * @param username
     * @param date
     * @param ip
     */
    public static void updateLoginTimeAndIP(String username, Date date, String ip){
        //调用dao
        dao.updateLoginTime(username,date,ip);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return true 登录成功，false登录失败
     */
    public static boolean login(String username,String password) {
        return  dao.login(username,password);
    }
}
