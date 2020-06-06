package com.lagou;

import com.lagou.edu.service.TransferService;
import com.lagou.edu.service.impl.TransferServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring xml获取Bean测试
 * @author wuqi
 * @date 2020-06-02 17:35
 */
public class SpringAopTest {

    @Test
    public void testAop() throws Exception {
        //获取bean
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        TransferService transferService = applicationContext.getBean(TransferService.class);
        transferService.transfer("6029621011000", "6029621011001", 200);

        applicationContext.close();
    }
}
