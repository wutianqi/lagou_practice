package com.lagou.edu.dao;

import com.lagou.edu.pojo.Account;

import java.util.List;

/**
 * @author wuqi
 * @date 2020-06-12 22:53
 */
public interface AccountMapper {

    List<Account> getAllAccounts();
}
