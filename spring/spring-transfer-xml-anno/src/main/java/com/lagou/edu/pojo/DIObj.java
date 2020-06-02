package com.lagou.edu.pojo;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 依赖注册的对象
 * @author wuqi
 * @date 2020-06-02 19:27
 */
public class DIObj {
    private Integer id;
    private String name;
    private List<String> list;
    private String[] array;
    private Map<String,String> map;

    public DIObj(){}

    public DIObj(Integer id, String name){
        this.id = id;
        this.name = name;
    }

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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "DIObj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list=" + list +
                ", array=" + Arrays.toString(array) +
                ", map=" + map +
                '}';
    }
}
