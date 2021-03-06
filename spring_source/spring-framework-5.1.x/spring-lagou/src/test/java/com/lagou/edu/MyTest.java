package com.lagou.edu;

import com.lagou.edu.pojo.AopBean;
import com.lagou.edu.pojo.MyBean;
import com.lagou.edu.tx.TransactionServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 * 	Bean的生命周期（包括了BeanFactoryPostProcessor）
 * 		//BeanFactoryPostProcessor被调用了...
 * 		//MyBean被创建了...
 * 		//MyBean username被设置了...
 * 		//BeanName Aware调用了...
 * 		//BeanFactory Aware调用了...
 * 		//ApplicationContext Aware调用了...
 * 		//BeanPostProcessor 前方法执行了...
 * 		//postConstruct方法执行...
 * 		//afterPropertiesSet方法执行...
 * 		//init method方法执行..
 * 		//BeanPostProcessor 后方法执行了...
 * 		//MyBean{username='zs', password='123'}
 * 		//preDestroy方法执行...
 * 		//disposable destroy方法执行
 * 		//destroy method方法执行...
 *
 *
 * @author wuqi
 * @date 2020-06-03 12:22
 */
public class MyTest {
	@Test
	public void testIoc() throws IOException {

		/*
			BeanFactoryPostProcessor初始化方法，BeanFactoryPostProcessor后处理方法：AbstractApplicationContext#refresh#invokeBeanFactoryPostProcessors
			BeanPostProcessor初始化方法：AbstractApplicationContext#refresh#registerBeanPostProcessors
			构造方法，BeanPostProcessor前初始化方法，InitializingBean的afterPropertiesSet，BeanPostProcessor后初始化方法：AbstractApplicationContext#refresh#finishBeanFactoryInitialization

		 */
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		MyBean myBean = (MyBean)applicationContext.getBean("myBeanB");
		System.out.println(myBean);

		Object myBeanA = applicationContext.getBean("myBeanA");
		System.out.println(myBeanA);

		//aop
//		AopBean bean = applicationContext.getBean(AopBean.class);
//		bean.sayHello();

		//tx
//		TransactionServiceImpl bean = applicationContext.getBean(TransactionServiceImpl.class);
//		bean.transfer();
		applicationContext.close();

	}
}