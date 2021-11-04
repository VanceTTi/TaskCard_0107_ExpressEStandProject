package com.fjt.service;

import com.fjt.bean.Express;
import com.fjt.dao.BaseExpressDao;
import com.fjt.dao.impl.ExpressDaoMysql;
import com.fjt.exception.DuplicateCodeException;
import com.fjt.util.RandomUtil;
import com.fjt.util.SMSUtil;

import java.util.List;
import java.util.Map;

public class ExpressService {
    private static BaseExpressDao dao = new ExpressDaoMysql();
    /**
     * 用于查询数据库中的全部快递(总数+新增)，待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页标记,true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 查询的数量
     * @return 快递的集合
     */
    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息
     */
    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据录入人手机号码，查询他所有的快递信息
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息
     */
    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }


    /**
     * 快递的录入
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     * @throws DuplicateCodeException
     * 如果出现异常就递归再走一遍这个方法，
     *
     * 短信发送(后台输出)：在快递录入时就开始发送短信通知用户。
     */
    public static boolean insert(Express e) {
        //1. 生成取件码
        e.setCode(RandomUtil.getCode()+"");
        try {
            boolean flag = dao.insert(e);
            if (flag) {//如果没有出现异常，录入成功了就调用发送短信的方法，发送短信
                SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException ex) {
            return insert(e);
        }

    }

    /**
     * 快递的修改
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number，company，username，userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     * 如果用户修改了手机号，则该快递会先删除，再重新插入。
     * 如果没有修改手机号就正常修改，
     * 如果没有修改手机号且修改了取件状态，且取件状态=1，也就是已经取件则完成取件操作
     */
    public static boolean update(int id, Express newExpress) {
        if (newExpress.getUserPhone() != null) {
            dao.delete(id);
            insert(newExpress);
            return insert(newExpress);
        }else {
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if (newExpress.getStatus() == 1) {
                updateStatus(e.getCode());
            }
            return update;
        }
    }

    /**
     * 更改快递的状态，为1表示取件完成
     *
     * @param code 要修改的取件码
     * @return 修改的结果，true表示成功，false表示失败
     */
    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 根据id,删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
