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
import java.util.Properties;

/**
 * 缓存测试
 */
public class CacheTest {

    /**
     * 一级缓存
     */
    @Test
    public void firstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        //第一次查询
        User user1 = userDao.findById(1);

        //更新数据，刷新缓存
//        User upUser = new User();
//        upUser.setId(1);
//        upUser.setUsername("zs");
//        userDao.updateUser(upUser);
//        sqlSession.commit(true);
        //刷新缓存
        sqlSession.clearCache();

        //第二次查询
        User user2 = userDao.findById(1);

        System.out.println(user1 == user2);
    }

    @Test
    public void secondLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession3 = sqlSessionFactory.openSession(true);

        UserDao userDao1 = sqlSession1.getMapper(UserDao.class);
        UserDao userDao2 = sqlSession2.getMapper(UserDao.class);
        UserDao userDao3 = sqlSession3.getMapper(UserDao.class);

        //查询，走数据库，存储二级缓存
        User user1 = userDao1.findById(1);
        //必须提交事务，二级缓存才能生效
        sqlSession1.commit();

        //查询，这里会走二级缓存
        User user2 = userDao2.findById(1);

        //更新，清除二级缓存
        User user = new User();
        user.setId(1);
        user.setUsername("zs");
        userDao3.updateUser(user);
        sqlSession3.commit();

        //再次查询，走数据库,存储二级缓存
        User user4 = userDao2.findById(1);
        sqlSession2.commit();

        //缓存对象是否相同
        System.out.println(user1 == user2);

        sqlSession1.close();
        sqlSession2.close();
        sqlSession3.close();
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        Properties properties = new Properties();
        properties.put("username","root");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in,properties);
        return sqlSessionFactory;
    }
}
