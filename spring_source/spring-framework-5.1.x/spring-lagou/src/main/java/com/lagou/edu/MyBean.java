package com.lagou.edu;

/**
 * @author wuqi
 * @date 2020-06-03 12:28
 */
public class MyBean {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "MyBean{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
