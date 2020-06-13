package com.lagou.edu;

import com.alibaba.fastjson.JSON;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author wuqi
 * @date 2020-06-12 23:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:applicationContext.xml")
public class AccountTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void accountTest(){
        List<Account> allAccounts = accountService.getAllAccounts();
        System.out.println(JSON.toJSONString(allAccounts));
    }

}
