package com.lagou;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring xml获取Bean测试
 * @author wuqi
 * @date 2020-06-02 17:35
 */
public class SpringXmlTest {

    @Test
    public void testXml(){
        //获取bean
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Object accountDao = applicationContext.getBean("accountDao");
        System.out.println(accountDao);

        Object user = applicationContext.getBean("user");
        System.out.println(user);

        Object user1 = applicationContext.getBean("user");
        System.out.println(user1);

        Object user2 = applicationContext.getBean("user2");
        System.out.println(user2);

        Object diObj = applicationContext.getBean("diObj");
        System.out.println(diObj);

        Object diObj2 = applicationContext.getBean("diObj2");
        System.out.println(diObj2);

        applicationContext.close();
    }
}
