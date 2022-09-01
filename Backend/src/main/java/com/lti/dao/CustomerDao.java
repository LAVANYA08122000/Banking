package com.lti.dao;

import java.time.LocalDate;
import java.util.List;

import com.lti.dto.AdminLoginStatus;
import com.lti.dto.CustomerDto;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.RegisterUserDto;
import com.lti.entity.Account;
import com.lti.entity.AccountStatus;
import com.lti.entity.Address;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.ErrorLogin;
import com.lti.entity.Income;
import com.lti.entity.Transaction;
import com.lti.entity.TransactionType;
import com.lti.entity.User;

public interface CustomerDao {

	Customer openAccount(CustomerDto customerDto);

	Customer updateProfile(CustomerDto customer);

	Customer searchCustomerById(int custId);

	Account accountSummary(int accountNumber);

	List<Transaction> accountStatement(int accountNumber, LocalDate fromDate, LocalDate toDate);

	Transaction fundTransfer(Account fromAccount, Account toAccount, double amount, TransactionType type,
			String password);

	boolean checkTransactionPassword(int accountNumber, String password);

	List<Transaction> findtoprec(int accNo);

	boolean isCustomerExists(int accountNumber);

	boolean isCustExists(int userId);

	boolean checkErrorLoginCount(int userId);

	void saveErrorData(ErrorLogin errLogin);

	User login(int userId, String password);

	ForgotPasswordDto forgotPassword(int userId);

	String changePassword(int userId, String loginPassword, String transactionPassword);

	RegisterUserDto signup(User user);

	int getCustomerId(int accountNumber);

	Beneficiary addBeneficiary(Beneficiary beneficiary);

	List<Beneficiary> viewAllBeneficiaries(int accNo);

	Beneficiary findBeneficiaryById(int beneficiaryId);

	void deleteBeneficiary(int beneficiaryId);

	public Admin addAdmin();

	AdminLoginStatus adminLogin(int adminId, String adminPassword);

	List<Customer> pendingRequest();

	String updatePendingRequest(int custId, AccountStatus response);

	AccountStatus trackApplication(int custId);

	List<String> getErrorLogins();

	void unblockAccount(String userId);

}
