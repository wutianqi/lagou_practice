package com.lagou.edu.service.impl;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 应癫
 */
//全注解配置事务管理
@EnableTransactionManagement
@Service("transferService")
public class TransferServiceImpl implements TransferService {

    //自动注入,自动根据类型查找
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        String sql = "select * from account where cardNo=?";
        Account from = jdbcTemplate.query(sql, new RowMapper<Account>() {

            @Override
            public Account mapRow(ResultSet resultSet, int i) throws SQLException {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                account.setMoney(resultSet.getInt("money"));
                account.setCardNo(resultSet.getString("cardNo"));
                return account;
            }
        }, fromCardNo).get(0);

        Account to = jdbcTemplate.query(sql, new RowMapper<Account>() {

            @Override
            public Account mapRow(ResultSet resultSet, int i) throws SQLException {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                account.setMoney(resultSet.getInt("money"));
                account.setCardNo(resultSet.getString("cardNo"));
                return account;
            }
        }, toCardNo).get(0);

        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);

        String updateSql = "update account set money = ? where cardNo = ?";
        jdbcTemplate.update(updateSql, to.getMoney(), to.getCardNo());
        int i = 1/0;
        jdbcTemplate.update(updateSql, from.getMoney(), from.getCardNo());
    }
}
