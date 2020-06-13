package com.lagou.edu.controller;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wuqi
 * @date 2020-06-12 23:42
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/getAll")
    @ResponseBody
    public List<Account> getAll(){
        return accountService.getAllAccounts();
    }
}
