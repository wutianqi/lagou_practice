package com.lagou.edu.tx;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * 开启事务的类
 * @author wuqi
 * @date 2020-06-07 12:45
 */
//@Service
@EnableTransactionManagement
public class TransactionServiceImpl {
	@Transactional
	public void transfer(){
		System.out.println("此处进行转账逻辑");
	}
}
