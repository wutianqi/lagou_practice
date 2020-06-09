package com.lagou.edu.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * spring中自带的拦截器
 * @author wuqi
 * @date 2020-06-07 12:31
 */
//@Component
public class MyInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("方法执行前...");
		Object proceed;
		try {
			proceed = invocation.proceed();
			System.out.println("方法执行后");
		}catch (Exception e){
			System.out.println("方法执行后");
			System.out.println("方法抛出异常了");
			throw e;
		}
		System.out.println("方法正常返回后");

		return proceed;
	}

}
