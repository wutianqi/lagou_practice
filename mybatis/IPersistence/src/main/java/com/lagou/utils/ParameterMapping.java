package com.lagou.utils;

/**
 * 参数映射 即#{name}中的name
 */
public class ParameterMapping {
    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
