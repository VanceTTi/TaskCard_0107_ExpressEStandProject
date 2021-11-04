package test.com.fjt.util;

import com.fjt.util.SMSUtil;
import org.junit.Test;

public class SMSUtilTest {

    @Test
    public void sendSms(){
        boolean flag = SMSUtil.send("18516955565", "123456");
        System.out.println(flag);
    }

}