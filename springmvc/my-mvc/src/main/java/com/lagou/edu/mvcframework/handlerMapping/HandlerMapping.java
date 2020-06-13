package com.lagou.edu.mvcframework.handlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 处理器映射器
 * @author wuqi
 * @date 2020-06-10 17:42
 */
public class HandlerMapping {
    /**
     * handler
     */
    private Method handler;

    /**
     * 调用方法的bean对象
     */
    private Object bean;

    /**
     * url模式
     */
    private Pattern urlPattern;

    /**
     * 参数映射  0,name
     */
    private Map<Integer, String> parameterMapping;

    /**
     * 允许访问该Handler的用户名列表
     */
    private List<String> allowUsers;


    public HandlerMapping(Method handler, Object bean, Pattern urlPattern, Map<Integer, String> parameterMapping, List<String> allowUsers) {
        this.handler = handler;
        this.bean = bean;
        this.urlPattern = urlPattern;
        this.parameterMapping = parameterMapping;
        this.allowUsers = allowUsers;
    }

    public Method getHandler() {
        return handler;
    }

    public void setHandler(Method handler) {
        this.handler = handler;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Pattern getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(Pattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Map<Integer, String> getParameterMapping() {
        return parameterMapping;
    }

    public void setParameterMapping(Map<Integer, String> parameterMapping) {
        this.parameterMapping = parameterMapping;
    }

    public List<String> getAllowUsers() {
        return allowUsers;
    }

    public void setAllowUsers(List<String> allowUsers) {
        this.allowUsers = allowUsers;
    }
}
