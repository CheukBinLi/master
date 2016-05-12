package project.master.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import project.master.fw.sh.common.AbstractEntity;

/**
 * *
 * 
 * Copyright 2016 CHEUK.BIN.LI Individual All
 * 
 * ALL RIGHT RESERVED
 * 
 * CREATE ON 2016年5月4日
 * 
 * EMAIL:20796698@QQ.COM
 * 
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 用户表
 *
 */
@Entity
public class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Column(unique = true)
	private String phone;// 用户名(手机号)
	private String password;
	private int status;// 帐号状态(锁定、停用、正常)
	private Date lastLogin;
	@Column(updatable = false, nullable = false)
	private Date createDate = new Date();

	public String getId() {
		return id;
	}

	public User setId(String id) {
		this.id = id;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public User setStatus(int status) {
		this.status = status;
		return this;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public User setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public User setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}

	public User(String id) {
		super();
		this.id = id;
	}

	public User() {
		super();
	}

}
