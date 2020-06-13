package com.lagou.edu.pojo;

import javax.persistence.*;

/**
 * 简历实体类（在类中要使用注解建立实体类和数据表之间的映射关系以及属性和字段的映射关系）
 * 实体类与数据库表之间的对应
 * @Entity
 * @Table
 *
 * 实体类的属性和数据库表字段的对应
 * @Id 标识主键
 * @GeneratedValue 主键的生成策略
 * @Column 标识列
 *
 *
 *
 * @author wuqi
 * @date 2020-06-13 17:40
 */
@Entity
@Table(name = "tb_resume")
public class Resume {
    /**
     * GenerationType.IDENTITY mysql主键自增的策略
     * GenerationType.SEQUENCE 使用序列 oracle中的
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
