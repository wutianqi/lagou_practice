package com.lagou.edu.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author wuqi
 * @date 2020-06-03 12:28
 */
public class MyBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {
	public MyBean(){
		System.out.println("MyBean被创建了...");
	}

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		System.out.println("MyBean username被设置了...");
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public void setBeanName(String name) {
		System.out.println("BeanName Aware调用了...");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactory Aware调用了...");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("ApplicationContext Aware调用了...");
	}

	@PostConstruct
	public void postConstruct(){
		System.out.println("postConstruct方法执行...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet方法执行...");
	}

	public void initMethod(){
		System.out.println("init method方法执行..");
	}

	@PreDestroy
	public void preDestroy(){
		System.out.println("preDestroy方法执行...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("disposable destroy方法执行");
	}

	public void destroyMethod(){
		System.out.println("destroy method方法执行...");
	}

	@Override
	public String toString() {
		return "MyBean{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}


}
