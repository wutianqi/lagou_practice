package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountMapper;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wuqi
 * @date 2020-06-12 23:07
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> getAllAccounts(){
        return accountMapper.getAllAccounts();
    }

}
