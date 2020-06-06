package com.lagou.edu.pojo;

/**
 * 循环依赖中的A
 * @author wuqi
 * @date 2020-06-05 7:48
 */
public class MyBeanA {
	private MyBeanB myBeanB;


	public MyBeanB getMyBeanB() {
		return myBeanB;
	}

	public void setMyBeanB(MyBeanB myBeanB) {
		this.myBeanB = myBeanB;
	}
}
