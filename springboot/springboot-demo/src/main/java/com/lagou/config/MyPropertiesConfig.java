package com.lagou.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuqi
 * @date 2020-06-16 8:22
 */
@ConfigurationProperties(prefix = "test")
public class MyPropertiesConfig {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyPropertiesConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
