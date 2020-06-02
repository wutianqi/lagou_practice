package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.dao.impl.JdbcAccountDaoImpl;
import com.lagou.edu.factory.BeanFactory;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import com.lagou.edu.utils.ConnectionUtil;
import com.lagou.edu.utils.TransactionManager;

import java.sql.Connection;

/**
 * @author 应癫
 */
public class TransferServiceImpl implements TransferService {
    //原始方式获取bean
    //private AccountDao accountDao = new JdbcAccountDaoImpl();

    //工厂方式获取Bean
//    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    //自动注入
    private AccountDao accountDao;

    public TransferServiceImpl(){
        System.out.println("TransferServiceImpl被创建了...");
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
//        try {
//            //开启事务
//            TransactionManager.getInstance().begin();

            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
//            int a = 1/0;
            accountDao.updateAccountByCardNo(from);

//            //提交事务
//            TransactionManager.getInstance().commit();
//        }catch (Exception e){
//            //回滚事务
//            TransactionManager.getInstance().rollback();
//            throw e;
//        }
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
