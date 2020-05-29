package com.lagou.dao;

import com.lagou.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户
 */
@CacheNamespace() //开启二级缓存
public interface UserDao {
    List<User> findAll();

    @Insert("insert into user (id,username) values (#{id},#{username})")
    void addUser(User user);

    @Update("update user set username=#{username} where id=#{id}")
    void updateUser(User user);

    @Select("select * from user where id=#{userId}")
    User findById(int userId);

    @Delete("delete from user where id=#{userId}")
    void deleteUser(int userId);

    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "orders", column = "id", javaType = List.class,many = @Many(
                    select = "com.lagou.dao.OrderDao.selectByUserId"
            )),

    })
    List<User> findAllByAnnotation();

    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "roles", column = "id", javaType = List.class, many = @Many(
                    select = "com.lagou.dao.SysRoleDao.findByUserId"
            )),

    })
    List<User> findUserRoleByAnnotation();
}
