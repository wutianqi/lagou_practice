package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

/**
 * Dao层接口
 */
public interface UserDao {
    /**
     * 根据条件查找
     * @param user 条件
     * @return
     */
    public User findByCondition(User user);

    /**
     * 查找所有
     * @return
     */
    public List<User> findAll();
}
