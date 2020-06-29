package com.custom.impot;

/**
 * @author wuqi
 * @date 2020-06-19 8:18
 */
public class MyBean {
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
        return "Bean1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
