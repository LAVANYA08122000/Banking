package com.lti.service;

import java.time.LocalDate;
import java.util.List;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

import com.lti.dto.AccountSummaryDto;
import com.lti.dto.AdminLoginStatus;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.CustomerDetails;
import com.lti.dto.CustomerDto;
import com.lti.dto.DocumentUploadDto;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.RegisterUserDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.ViewAllBeneficiariesDto;
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

public interface CustomerService {

	String addBeneficiary(BeneficiaryAccountDto beneficiary);

	List<ViewAllBeneficiariesDto> viewAllBeneficiaries(int accNo);

	String deleteBeneficiary(int beneficiaryId);

	BeneficiaryAccountDto findBeneficiary(int beneficiaryId);

	List<TopFiveTransactionDto> findTopFiveTransactions(int accNo);

	boolean isCustomerExists(int accountNumber);

	RegisterUserDto signup(User user);

	User login(int userId, String password);

	ForgotPasswordDto forgotPassword(int userId);

	String changePassword(int userId, String loginPassword, String transactionPassword);

//	Account services 

	int openAccount(CustomerDto customerDto);

	String documentUpload(MultipartFile file);

	Customer searchCustomerById(int custId);

	String updateProfile(CustomerDto customerDto);

	AccountSummaryDto accountSummary(int accountNumber);

	List<TopFiveTransactionDto> accountStatement(int accountNumber, LocalDate fromDate, LocalDate toDate);

	String fundTransfer(String fromAccount, String toAccount, double amount, TransactionType type, String password);

	AdminLoginStatus adminLogin(int adminId, String adminPassword);

	int getCustomerId(int accountNumber);

	List<CustomerDto> pendingRequest();

	public Admin addAdmin();
	
	List<String> getErrorLogins();
	
	String unblockAccount(String userId);

	String updatePendingRequests(int custId, AccountStatus response);

	AccountStatus trackApplication(int custId);

}
