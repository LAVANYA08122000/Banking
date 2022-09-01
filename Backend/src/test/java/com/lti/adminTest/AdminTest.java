package com.lti.adminTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lti.dto.AccountSummaryDto;
import com.lti.dto.AdminLoginStatus;
import com.lti.dto.Status.StatusCode;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.entity.AccountType;
import com.lti.entity.Admin;
import com.lti.entity.User;
import com.lti.service.CustomerServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AdminTest {

	@Autowired
	CustomerServiceImpl customerServiceImpl;
	@Test
	void adminLogin() {
		Admin a=new Admin();
		a.setId(12345);
		a.setName("Andy");
		a.setAdminPassword("admin");
		Assertions.assertThat(customerServiceImpl.adminLogin(a.getAdminId(),a.getAdminPassword()).getStatusMessage()).isEqualTo("Login Successful");		
	}
	@Test
	void addAdmin()
	{
		Admin a=new Admin();
		a.setId(36996);
		a.setName("John");
		a.setAdminPassword("admin");
		Admin ad=customerServiceImpl.addAdmin();
		Assertions.assertThat(ad.getAdminName().equals(a.getAdminName()) && ad.getAdminPassword().equals(a.getAdminPassword()));		
	}
	@Test 
	void changePassword()
	{
		User u=new User();
		u.setUserId(702);
		u.setLoginPassword("pass");
		u.setTransactionPassword("password");
		Assertions.assertThat(customerServiceImpl.changePassword(u.getUserId(), u.getLoginPassword(),u.getTransactionPassword())).isEqualTo("Passwords Changed Successfully.");
	}
	@Test
	void accountSummary()
	{
		AccountSummaryDto accDto=new AccountSummaryDto();
		accDto.setAccountNumber(100001);
		accDto.setAccountType(AccountType.Savings);
		accDto.setBalance(20000);
		AccountSummaryDto accResult=customerServiceImpl.accountSummary(100001);
		Assertions.assertThat(accResult.getAccountNumber()==accDto.getAccountNumber() && accResult.getAccountType()==accDto.getAccountType() && accResult.getBalance()==accDto.getBalance());
		
	}
	@Test 
	void accountStatement()
	{
		LocalDate to= LocalDate.of(2022, 8, 29);
		List<TopFiveTransactionDto> tp5=customerServiceImpl.accountStatement(100004,to, to);
		Assertions.assertThat(customerServiceImpl.accountStatement(100004,to, to)!=null);
		
	}

}
