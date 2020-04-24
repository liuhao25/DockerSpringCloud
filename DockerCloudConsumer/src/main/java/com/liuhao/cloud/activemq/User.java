package com.liuhao.cloud.activemq;

import java.io.Serializable;

/**
 * Title: account
 * Description: 
 * @Author: liuhao
 * @date: 2019年1月18日 下午3:46:05
 */
public class User implements Serializable {

	private static final long serialVersionUID = 355824490972357087L;
	
    private Long id;
    private String username;
    private String password;
    
    public User() {
    	
    }
   
    public User(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
    
    public User(Long id, String username, String password) {
    	this.id = id;
    	this.username = username;
    	this.password = password;
    }
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
    
}
