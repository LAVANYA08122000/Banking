package com.lti;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lti.dao.CustomerDao;
import com.lti.dao.CustomerDaoImpl;
import com.lti.dto.AccountSummaryDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.Status.StatusCode;
import com.lti.entity.Account;
import com.lti.entity.AccountType;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Transaction;
import com.lti.entity.TransactionType;
import com.lti.entity.User;
import com.lti.service.CustomerServiceImpl;
import com.lti.dto.ViewAllBeneficiariesDto;

@SpringBootTest
class BankingSpringBootApplicationTests {


	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Autowired 
	CustomerDao dao;
	
	/*@Test
	void contextLoads() {
	}*/
	/*@Test
	void adminLogin() {
		Admin a=new Admin();
		a.setId(12345);
		a.setName("Andy");
		a.setAdminPassword("admin");
		Assertions.assertThat(customerServiceImpl.adminLogin(a.getAdminId(),a.getAdminPassword()).getStatusMessage()).isEqualTo("Login Successful");		
	}/*
	/*@Test
	void addAdmin()
	{
		Admin a=new Admin();
		a.setId(36996);
		a.setName("John");
		a.setAdminPassword("admin");
		Admin ad=customerServiceImpl.addAdmin();
		Assertions.assertThat(ad.getAdminName().equals(a.getAdminName()) && ad.getAdminPassword().equals(a.getAdminPassword()));		
	}*/
	
	//@Test
/*void Summary() {
		
		AccountSummaryDto a = new AccountSummaryDto();
		a.setAccountNumber(120);
		a.setBalance(30000);
//	AccountType p = new AccountType();
//		a.setAccountType("Savings");
		AccountSummaryDto b = new AccountSummaryDto();
		b.setAccountNumber(130);
		b.setBalance(40000);
		
		Assertions.assertThat( b.getAccountNumber()==a.getAccountNumber());
	}*/
	
	/*@Test 
	void changePassword()
	{
		User u=new User();
		u.setUserId(702);
		u.setLoginPassword("pass");
		u.setTransactionPassword("password");
		Assertions.assertThat(customerServiceImpl.changePassword(u.getUserId(), u.getLoginPassword(),u.getTransactionPassword())).isEqualTo("Passwords Changed Successfully.");
	}*/
	@Test
	void testfindBeneficiary() {
		Beneficiary ben = new Beneficiary();
		Beneficiary ben1 = new Beneficiary();
		ben1 = dao.findBeneficiaryById(301);
		ben.setBeneficiaryId(301);
		ben.setBeneficiaryName("fdfdf");
		ben.setCustomerAccountNumber(100004);
		ben.setBeneficiaryAccount(ben1.getBeneficiaryAccount());
		BeneficiaryAccountDto beneficiary=customerServiceImpl.findBeneficiary(301);
		System.out.println(beneficiary.getBeneficiaryId());
		Assertions.assertThat(beneficiary.getBeneficiaryId()==ben.getBeneficiaryId() && beneficiary.getBeneficiaryName().equals(ben.getBeneficiaryName()) && beneficiary.getBeneficiaryAccount()==ben.getBeneficiaryAccount().getAccountNumber());
	}
	@Test
	void testviewAllBeneficiaries() {
		
		List<ViewAllBeneficiariesDto> view = new ArrayList<>();
		view = customerServiceImpl.viewAllBeneficiaries(100001);
		
		Assertions.assertThat(customerServiceImpl.viewAllBeneficiaries(100001)).isEqualTo(view);

}
	/*@Test
	void testfundTransfer() {
		String fromAccount="100003";
		String toAccount="100004";
		double amount=100;
		TransactionType type=TransactionType.IMPS;
		String password="Prasad@123";
		
		Account fromAccount1 = dao.accountSummary(Integer.parseInt(fromAccount));
		Account toAccount1 = dao.accountSummary(Integer.parseInt(toAccount));
		Transaction transaction = dao.fundTransfer(fromAccount1, toAccount1, amount, type, password);
		
		Assertions.assertThat(customerServiceImpl.fundTransfer(fromAccount, toAccount, amount, type, password).substring(0,25)).isEqualTo("Fund transfer successful.");
	}*/
	
	/*@Test
	void testaddBeneficiary() {
		/*BeneficiaryAccountDto beneficiary;
		Beneficiary ben = new Beneficiary();
		ben.setBeneficiaryId(beneficiary.getBeneficiaryId());
		ben.setCustomerAccountNumber(beneficiary.getCustomerAccountNumber());
		ben.setBeneficiaryName(beneficiary.getBeneficiaryName());
		Account acc = new Account();
		acc.setAccountNumber(beneficiary.getBeneficiaryAccount());
		ben.setBeneficiaryAccount(acc);*/
		
		
		/*BeneficiaryAccountDto b1d =new BeneficiaryAccountDto();
		b1d.setBeneficiaryAccount(100004);
		b1d.setBeneficiaryName("fdfdf");
		b1d.setCustomerAccountNumber(100003);
		b1d.setBeneficiaryId(30);
		
		Assertions.assertThat(customerServiceImpl.addBeneficiary(b1d).substring(0, 31)).isEqualTo("Beneficiary added successfully.");
		
		
		
	}*/
	@Test
	void accountSumaary()
	{
		AccountSummaryDto accDto=new AccountSummaryDto();
		accDto.setAccountNumber(100001);
		accDto.setAccountType(AccountType.Savings);
		accDto.setBalance(20000);
		AccountSummaryDto accResult=customerServiceImpl.accountSummary(100001);
		Assertions.assertThat(accResult.getAccountNumber()==accDto.getAccountNumber() && accResult.getAccountType()==accDto.getAccountType() && accResult.getBalance()==accDto.getBalance());
		
	}
	@Test
	public void loginTest() {
		User user = new User();
		user.setUserId(701);
		user.setLoginPassword("Prasad@123");
		
		CustomerDaoImpl custdao = new CustomerDaoImpl();
		Assertions.assertThat(custdao.login(701,"Prasad@123")).isEqualTo(user);
		
	}
	@Test 
	void accountStatement()
	{
		LocalDate to= LocalDate.of(2022, 8, 29);
		List<TopFiveTransactionDto> tp5=customerServiceImpl.accountStatement(100004,to, to);
		
		Assertions.assertThat(customerServiceImpl.accountStatement(100004,to, to)!=null);
		
	}
}
