package com.lti.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_error_login")
public class ErrorLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "err_seq")
	@SequenceGenerator(name = "err_seq", initialValue = 1001, allocationSize = 1)
	@Column(name = "err_login_id")
	private int errLoginId;

//	@ManyToOne(cascade = CascadeType.ALL)
	@Column(name = "user_id")
	private String userId;

	@Column(name = "date_and_time")
	private LocalDateTime dateAndTime;

	public int getErrLoginId() {
		return errLoginId;
	}

	public void setErrLoginId(int errLoginId) {
		this.errLoginId = errLoginId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

}
