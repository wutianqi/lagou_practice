package com.lagou.edu.dao.impl;

import com.lagou.edu.anno.Autowired;
import com.lagou.edu.anno.Repository;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.utils.ConnectionUtil;
import com.lagou.edu.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 应癫
 */
@Repository
public class JdbcAccountDaoImpl implements AccountDao {

    //自动注入
    @Autowired
    private ConnectionUtil connectionUtil;

    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        Connection con = connectionUtil.getConnection();
        String sql = "select * from account where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        Account account = new Account();
        while(resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }
        resultSet.close();
        preparedStatement.close();

        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {
        Connection con = connectionUtil.getConnection();
        String sql = "update account set money=? where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        return i;
    }
}
