package com.lti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_admin")
public class Admin {

	@Id
	@SequenceGenerator(name = "adm_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "adm_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "admin_login_id")
	private int adminId;

	@Column(name = "admin_password")
	private String adminPassword;

	@Column(name = "admin_name")
	private String adminName;

	public int getAdminId() {
		return adminId;
	}

	public void setId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setName(String adminName) {
		this.adminName = adminName;
	}
}
