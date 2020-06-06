package com.lagou.edu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 日志切面类
 * @author wuqi
 * @date 2020-06-06 9:09
 */
//注解：声明切面类
@Aspect
//全注解实现Aop的方式
@EnableAspectJAutoProxy
public class LogAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.lagou.edu.service.TransferService.*(..))")
    public void pointcut(){}

    /**
     * 前置通知
     * @param joinPoint 连接点
     */
//    @Before("pointcut()")
    public void beforeMethod(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("前置通知方法执行,method=" + name + "...");
    }

    /**
     * 后置通知
     */
//    @After("pointcut()")
    public void afterMethod(){
        System.out.println("后置通知方法执行...");
    }

    /**
     * 正常返回
     * @param retVal 返回值
     */
//    @AfterReturning(value = "pointcut()",returning = "retVal")
    public void afterReturningMethod(Object retVal){
        System.out.println("正常返回通知方法执行,retVal=" + retVal + "...");
    }

    /**
     * 异常通知
     */
//    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowingMethod(Exception e){
        System.out.println("异常通知方法执行,errorMsg=" + e.getMessage() + "...");
    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint 正在执行的点
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object proceed;
        try {
            //方法执行前
            System.out.println("环绕通知，方法执行前");
            proceed = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            System.out.println("环绕通知，方法执行后");
        } catch (Throwable throwable) {
            System.out.println("环绕通知，方法执行后");
            System.out.println("环绕通知，方法抛出异常");
            throw throwable;
        }

        System.out.println("环绕通知，方法正常返回");
        return proceed;
    }
}
