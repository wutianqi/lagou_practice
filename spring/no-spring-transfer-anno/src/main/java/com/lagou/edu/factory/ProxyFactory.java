package com.lagou.edu.factory;

import com.lagou.edu.anno.Autowired;
import com.lagou.edu.anno.Component;
import com.lagou.edu.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂
 */
public class ProxyFactory {
    private TransactionManager transactionManager;

    public ProxyFactory(TransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }

    /**
     * 利用JDK动态代理为目标类生成代理类
     * @param target 目标类
     * @return
     */
    public Object getJdkProxy(Object target){
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return  Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    //开启事务
                    transactionManager.begin();

                    //方法调用
                    Object result = method.invoke(target, args);

                    //提交事务
                    transactionManager.commit();

                    return result;
                } catch (Exception e){
                    //回滚事务
                    transactionManager.rollback();
                    throw e;
                }
            }
        });
    }

    /**
     * 生成cglib代理类
     * @param target 目标类
     * @return
     */
    public Object getCglibProxy(Object target){
        return Enhancer.create(target.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                try {
                    //开启事务
                    transactionManager.begin();

                    //方法调用
                    Object result = method.invoke(target, args);

                    //事务提交
                    transactionManager.commit();

                    return result;
                }catch (Exception e){
                    //回滚事务
                    transactionManager.rollback();
                    throw e;
                }
            }
        });
    }
}
