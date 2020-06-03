package com.lagou.test;

import com.lagou.dao.UserDao;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 */
public class MybatisTest {

    //mybatis基本环境搭建
    @Test
    public void  test() throws IOException {
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //开启一个事务，默认事务不会自动提交，需要手动commit.
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.clearCache();
        User user1 = new User();
//        user1.setId(1);
//        user1.setUsername("aaa");
//        List<User> users = sqlSession.selectList("com.lagou.dao.UserDao.findByCondition",user1);
        List<User> users = sqlSession.selectList("com.lagou.dao.UserDao.findAll");
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     * 简单的增删改查因为太熟悉忽略，不在练习
     */
}
