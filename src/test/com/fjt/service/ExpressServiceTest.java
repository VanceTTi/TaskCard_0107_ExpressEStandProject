package test.com.fjt.service;

import com.fjt.bean.Express;
import com.fjt.service.ExpressService;
import org.junit.Test;

public class ExpressServiceTest {

    @Test
    public void insert() {
        Express e = new Express("12311","笑哈哈","15555555555","顺丰快递","18888888888","666666");
        boolean flag = ExpressService.insert(e);
        System.out.println(flag);
    }
}