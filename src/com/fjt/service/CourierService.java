package com.fjt.service;

import com.fjt.bean.Courier;
import com.fjt.dao.BaseCourierDao;
import com.fjt.dao.impl.CourierDaoMysql;

import java.util.List;

public class CourierService {
    private static BaseCourierDao dao = new CourierDaoMysql();

    /**
     * 查询快递员的总条数
     * @return 总数
     */
    public static int total(){
        return dao.total();
    }

    /**
     * 用于查询所有快递员
     * @param limit 是否分页标记,true表示分页，false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 查询的数量
     * @return 快递员的集合
     */
    public static List<Courier> findCourierAll(boolean limit, int offset, int pageNumber){
        return dao.findCourierAll(limit,offset,pageNumber);
    }

    /**
     * 快递员的录入，
     * @param courier 录入的快递员对象
     * @return ture 为成功，  false为失败
     */
    public static boolean courierInsert(Courier courier){
        return dao.courierInsert(courier);
    }

    /**
     * 根据手机号来查询快递员信息
     * @param phone 手机号码
     * @return 返回快递员信息的对象
     */
    public static Courier findByPhone(String phone){
        return  dao.findByPhone(phone);
    }

    /**
     *根据id修改快递员信息
     * @param id
     * @param newCourier
     * @return
     */
    public static boolean update(int id ,Courier newCourier){
        return dao.update(id,newCourier);
    }


    /**
     * 根据id,删除快递信息
     * @param courierId 要删除的快递员的id
     * @return
     */
    public static boolean  delete(int courierId){
        return dao.delete(courierId);
    }


}
