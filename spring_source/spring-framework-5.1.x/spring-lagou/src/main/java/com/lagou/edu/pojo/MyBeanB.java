package com.lagou.edu.pojo;

/**
 * 循环依赖中的B
 * @author wuqi
 * @date 2020-06-05 7:48
 */
public class MyBeanB {
	private MyBeanA myBeanA;

	public MyBeanA getMyBeanA() {
		return myBeanA;
	}

	public void setMyBeanA(MyBeanA myBeanA) {
		this.myBeanA = myBeanA;
	}
}
