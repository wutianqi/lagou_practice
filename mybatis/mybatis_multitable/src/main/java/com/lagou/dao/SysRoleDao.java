package com.lagou.dao;

import com.lagou.pojo.SysRole;
import com.lagou.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色
 */
public interface SysRoleDao {
    public List<User> findAll();

    @Select("select * from sys_role r, sys_user_role ur where r.id=ur.roleid and ur.userid=#{userId}")
    public List<SysRole> findByUserId(int userId);
}
