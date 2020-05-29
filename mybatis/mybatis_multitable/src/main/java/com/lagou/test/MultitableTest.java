package com.lagou.test;

import com.lagou.dao.OrderDao;
import com.lagou.dao.SysRoleDao;
import com.lagou.dao.UserDao;
import com.lagou.pojo.Order;
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
 * 测试类
 */
public class MultitableTest {

    /**
     * 一对一
     */
    @Test
    public void test1() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrderDao mapper = sqlSession.getMapper(OrderDao.class);
        User user = new User();
        user.setId(1);
        List<Order> orders = mapper.findByCondition(user);
        for(Order order : orders){
            System.out.println(order);
        }
    }

    /**
     * 一对多
     */
    @Test
    public void test2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> users = mapper.findAll();
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     * 多对多
     */
    @Test
    public void test3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysRoleDao mapper = sqlSession.getMapper(SysRoleDao.class);
        List<User> users = mapper.findAll();
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     * 注解基本的CRUD
     */
    @Test
    public void  test4() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setId(3);
        user.setUsername("haha");
//        userDao.addUser(user);

        user.setUsername("修改后的名字");
//        userDao.updateUser(user);

        User queryUser = userDao.findById(1);
//        System.out.println(queryUser);

        userDao.deleteUser(3);
    }

    /**
     * 注解一对一
     */
    @Test
    public void test5() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        List<Order> orders = orderDao.findByAll();
        for (Order order : orders){
            System.out.println(order);
        }
    }

    /**
     * 注解一对多
     */
    @Test
    public void test6() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.findAllByAnnotation();
        for (User user : users){
            System.out.println(user);
        }
    }

    /**
     * 注解多对多
     */
    @Test
    public void test7() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.findUserRoleByAnnotation();
        for (User user : users){
            System.out.println(user);
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        return sqlSessionFactory;
    }
}
