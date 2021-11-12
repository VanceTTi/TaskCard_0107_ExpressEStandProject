package test.com.fjt.dao.imp;

import com.fjt.bean.Courier;
import com.fjt.bean.User;
import com.fjt.dao.BaseUserDao;
import com.fjt.dao.impl.UserDaoMysql;
import org.junit.Test;

public class UserDaoMysqlTest {
    BaseUserDao dao = new UserDaoMysql();
    /**
     * 根据id修改
     */
    @Test
    public void update(){
        User newUser = new User();

        newUser.setNickname("user33");
        newUser.setuPhone("13459354333");
        newUser.setuPassword("1234");

        boolean update = dao.update(1003, newUser);
        System.out.println(update);
    }

    @Test
    public void test1(){
        User byPhone = dao.findByPhone("13459354333");
        System.out.println(byPhone.getUid());
    }


}
