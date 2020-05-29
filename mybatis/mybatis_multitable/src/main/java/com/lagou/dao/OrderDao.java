package com.lagou.dao;

import com.lagou.pojo.Order;
import com.lagou.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单
 */
public interface OrderDao {
    List<Order> findByCondition(User user);

    /*
        使用注解进行关联查询需要用到 @Results->resultMap的替换 @Result->property的替换
        @One->一对以一时，对另一个statement的指向。
     */
    @Results(
            {
                    @Result(property = "id", column = "id"),
                    @Result(property = "ordertime", column = "ordertime"),
                    @Result(property = "total", column = "total"),
                    @Result(property = "user", column = "uid", javaType = User.class,one = @One(
                            select = "com.lagou.dao.UserDao.findById"
                    ))
            }
    )
    @Select("select * from orders")
    List<Order> findByAll();

    @Select("select * from orders where uid=#{userId}")
    List<Order> selectByUserId(int userId);
}
