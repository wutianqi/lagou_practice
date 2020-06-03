package com.lagou.edu;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wuqi
 * @date 2020-06-03 12:22
 */
public class MyTest {
	@Test
	public void testIoc(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Object myBean = applicationContext.getBean("myBean");
		System.out.println(myBean);
	}
}
