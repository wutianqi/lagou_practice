package com.lagou.edu.service.impl;

import com.lagou.edu.anno.Autowired;
import com.lagou.edu.anno.Service;
import com.lagou.edu.anno.Transactional;
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
@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    //自动注入
    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
//            int i = 1/0;
            accountDao.updateAccountByCardNo(from);
    }
}
