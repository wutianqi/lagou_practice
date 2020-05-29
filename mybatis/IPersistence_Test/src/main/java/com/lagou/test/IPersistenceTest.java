package com.lagou.test;

import com.lagou.dao.UserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlsession.SqlSession;
import com.lagou.sqlsession.SqlSessionFactory;
import com.lagou.sqlsession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 持久层框架测试类
 */
public class IPersistenceTest {

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        SqlSession sqlSession = getSqlSession();
        UserDao userDao = sqlSession.getDao(UserDao.class);
        User user = new User();
        user.setId(1);
        user.setUsername("lisisi");
        int affectRows = userDao.update(user);
        System.out.println(affectRows);
    }

    /**
     * 测试增加
     */
    @Test
    public void testAdd() throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        SqlSession sqlSession = getSqlSession();
        UserDao userDao = sqlSession.getDao(UserDao.class);
        User user = new User();
        user.setUsername("zsss");
        int affectRows = userDao.insert(user);
        System.out.println(affectRows);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        SqlSession sqlSession = getSqlSession();
        UserDao userDao = sqlSession.getDao(UserDao.class);
        User user = new User();
        user.setId(6);
        int affectRows = userDao.delete(user);
        System.out.println(affectRows);
    }

    /**
     * 测试查询
     */
    @Test
    public void testQuery() throws PropertyVetoException, DocumentException, SQLException, IllegalAccessException, InvocationTargetException, IntrospectionException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        SqlSession sqlSession = getSqlSession();

        User user = new User();
        user.setId(1);
        user.setUsername("zs");

        //查询单个
//        User user2 = (User)sqlSession.selectOne("user.selectOne", user);
//        System.out.println(user2);

        //查询所有
//        List<Object> objects = sqlSession.selectList("user.selectList");
//        for(Object object : objects){
//            User user3 = (User)object;
//            System.out.println(user3);
//        }

        //Dao代理类查询单个
        UserDao dao = sqlSession.getDao(UserDao.class);
//        User user4 = dao.findByCondition(user);
//        System.out.println(user4);

        //Dao代理类查询所有
        List<User> users = dao.findAll();
        System.out.println(users);
    }

    private SqlSession getSqlSession() throws PropertyVetoException, DocumentException {
        InputStream in = Resources.getResourceAsStream("/SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

        return sqlSessionFactory.openSession();
    }

}
