package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

/**
 * Dao层接口
 */
public interface UserDao {

    /**
     * 更新
     * @param user 参数对象
     * @return
     */
    int update(User user);

    /**
     * 插入
     * @param user 参数对象
     * @return
     */
    int insert(User user);

    /**
     * 删除
     * @param user 参数对象
     * @return
     */
    int delete(User user);

    /**
     * 根据条件查找
     * @param user 条件
     * @return
     */
    User findByCondition(User user);

    /**
     * 查找所有
     * @return
     */
    List<User> findAll();
}
