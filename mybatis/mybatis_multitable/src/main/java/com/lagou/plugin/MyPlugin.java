package com.lagou.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * 自定义插件
 */
@Intercepts({
        @Signature(
                //拦截哪个类
                type = StatementHandler.class,
                //拦截哪个方法
                method = "prepare",
                //参数信息，用于定位到唯一的方法
                args = {Connection.class, Integer.class}
        )
})
public class MyPlugin implements Interceptor {
    //最终在执行目标方法之前会调用该方法
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("拦截到了目标方法");

        return invocation.proceed();
    }

    //该方法为目标类生成代理对象
    @Override
    public Object plugin(Object target) {
        if(target instanceof  StatementHandler){
            return Plugin.wrap(target, this);
        }

        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("参数列表=" + properties);
    }
}
