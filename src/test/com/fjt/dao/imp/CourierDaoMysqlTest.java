package test.com.fjt.dao.imp;

import com.fjt.bean.Courier;
import com.fjt.dao.BaseCourierDao;
import com.fjt.dao.impl.CourierDaoMysql;
import org.junit.Test;

import java.util.List;

public class CourierDaoMysqlTest {
    BaseCourierDao dao = new CourierDaoMysql();

    /**
     * 查询快递员的总条数
     * @return 总数
     */
    @Test
    public void total(){
        int total = dao.total();
        System.out.println(total);

    }

    /**
     * 用于查询所有快递员
     * 快递员的集合
     */
    @Test
    public void findCourierAll(){
        List<Courier> courierAll = dao.findCourierAll(false, 0, 0);
        System.out.println(courierAll);
    }

    /**
     * 根据手机号查询
     */
    @Test
    public void findByCourierPhone() {
        Courier byPhone = dao.findByPhone("138438888810");
        System.out.println(byPhone);

    }

    /**
     * 根据id修改
     */
    @Test
    public void update(){
        Courier newCourier = new Courier();
        newCourier.setCourierName("测试1号");
        newCourier.setCourierPhone("12345678910");
        newCourier.setCourierIdCard("123456789123456789");
        newCourier.setCourierPassword("1235");
        boolean update = dao.update(1003, newCourier);
        System.out.println(update);
    }


}
