package com.fjt.dao;

import com.fjt.bean.Courier;

import java.util.List;

public interface BaseCourierDao {

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
    List<Courier> findCourierAll(boolean limit, int offset, int pageNumber);

    /**
     * 快递员的录入，
     * @param courier 录入的快递员对象
     * @return ture 为成功，  false为失败
     */
    public boolean courierInsert(Courier courier);

    /**
     * 根据手机号来查询快递员信息
     * @param phone 手机号码
     * @return 返回快递员信息的对象
     */
    public Courier findByPhone(String phone);

    /**
     * 根据id修改快递员信息
     * @param id
     * @param newCourier
     * @return
     */
    public boolean update(int id, Courier newCourier);



    /**
     * 根据id,删除快递信息
     * @param courierId 要删除的快递员的id
     * @return
     */
    boolean delete(int courierId);
}
