package com.lagou;

import com.lagou.edu.config.SpringConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring xml获取Bean测试
 * @author wuqi
 * @date 2020-06-02 17:35
 */
public class SpringAnnoTest {

    @Test
    public void testAnno(){
        //获取bean
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        //获取user对象
        Object user = applicationContext.getBean("user");
        //获取BeanFactory对象
        Object user2 = applicationContext.getBean("&user");
        System.out.println(user);
        System.out.println(user2);
        applicationContext.close();
    }
}
