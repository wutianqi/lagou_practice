package com.lagou.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 存在redis中的类
 *
 * @author wuqi
 * @date 2020-06-20 15:14
 */
@RedisHash("person") //指定操作实体类对象再redis数据库中的存储空间
public class Person {
    @Id
    private Integer id; //标识实体类主键
    @Indexed //标识对应属性在redis数据库中生成二级索引
    private String firstname;
    @Indexed
    private String lastname;
    private Address address;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address=" + address +
                '}';
    }
}
