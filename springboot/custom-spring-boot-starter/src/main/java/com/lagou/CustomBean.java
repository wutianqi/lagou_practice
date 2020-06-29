package com.lagou;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author wuqi
 * @date 2020-06-18 8:22
 */
@ConfigurationProperties(prefix = "custom")
public class CustomBean {
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
        return "CustomBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
