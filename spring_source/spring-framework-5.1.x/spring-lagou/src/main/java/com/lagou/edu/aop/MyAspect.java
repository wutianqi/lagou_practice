package com.lagou.edu.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 切面
 * @author wuqi
 * @date 2020-06-06 15:55
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class MyAspect {

	@Pointcut("execution(* com.lagou.edu.pojo.AopBean.*(..))")
	public void pointcut(){}

	@Before(value = "pointcut()")
	public void beforeMethod(){
		System.out.println("前置通知被调用...");
	}

	@After(value = "pointcut()")
	public void afterMethod(){
		System.out.println("后置通知被调用...");
	}

	@AfterReturning(value = "pointcut()")
	public void afterReturningMethod(){
		System.out.println("正常返回通知被调用...");
	}

}
