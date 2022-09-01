package com.lti.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lti.dao.CustomerDao;
import com.lti.dto.AccountSummaryDto;
import com.lti.dto.AdminLoginStatus;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.CustomerDto;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.RegisterUserDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.entity.Account;
import com.lti.entity.AccountStatus;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.ErrorLogin;
import com.lti.entity.Transaction;
import com.lti.entity.TransactionType;
import com.lti.entity.User;
import com.lti.exception.InsufficientFundsException;
import com.lti.exception.MinimumAmountException;
import com.lti.exception.ServiceException;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDao dao;

	public String addBeneficiary(BeneficiaryAccountDto beneficiary) {
		Beneficiary ben = new Beneficiary();
		ben.setBeneficiaryId(beneficiary.getBeneficiaryId());
		ben.setCustomerAccountNumber(beneficiary.getCustomerAccountNumber());
		ben.setBeneficiaryName(beneficiary.getBeneficiaryName());
		Account acc = new Account();
		acc.setAccountNumber(beneficiary.getBeneficiaryAccount());
		ben.setBeneficiaryAccount(acc);
		try {
			if (dao.isCustomerExists(beneficiary.getBeneficiaryAccount())) {
				Beneficiary beneficiary2 = dao.addBeneficiary(ben);
				return "Beneficiary added successfully. Beneficiary ID : " + beneficiary2.getBeneficiaryId();
			} else {
				throw new ServiceException("Beneficiary doesn't exists. Please add correct account number.");
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<ViewAllBeneficiariesDto> viewAllBeneficiaries(int accNo) {
		List<ViewAllBeneficiariesDto> viewBen = new ArrayList<>();
		List<Beneficiary> ben = new ArrayList<>();
		ben = dao.viewAllBeneficiaries(accNo);
		for (Beneficiary beneficiary : ben) {
			ViewAllBeneficiariesDto viewAll = new ViewAllBeneficiariesDto();
			viewAll.setAccountNumber(beneficiary.getBeneficiaryAccount().getAccountNumber());
			viewAll.setBeneficiaryId(beneficiary.getBeneficiaryId());
			viewAll.setBeneficiaryName(beneficiary.getBeneficiaryName());
			viewBen.add(viewAll);
		}
		return viewBen;
	}

	public ForgotPasswordDto forgotPassword(int userId) {
		try {
			return dao.forgotPassword(userId);
		} catch (Exception e) {
			throw new ServiceException("Password updation failed.");
		}
	}

	public String changePassword(int userId, String loginPassword, String transactionPassword) {
		try {
			return dao.changePassword(userId, loginPassword, transactionPassword);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public String deleteBeneficiary(int beneficiaryId) {
		try {
			dao.deleteBeneficiary(beneficiaryId);
			return "Beneficiary deleted successfully.";
		} catch (Exception e) {
			return "Unexpected Error occured. Beneficiary was not deleted.";
		}
	}

	public BeneficiaryAccountDto findBeneficiary(int beneficiaryId) {
		Beneficiary ben = new Beneficiary();
		ben = dao.findBeneficiaryById(beneficiaryId);
		BeneficiaryAccountDto dto = new BeneficiaryAccountDto();
		dto.setBeneficiaryAccount(ben.getBeneficiaryAccount().getAccountNumber());
		dto.setBeneficiaryId(ben.getBeneficiaryId());
		dto.setBeneficiaryName(ben.getBeneficiaryName());
		dto.setCustomerAccountNumber(ben.getCustomerAccountNumber());
		return dto;
	}
//	Account service implementations

	public int openAccount(CustomerDto customerDto) {
		try {
			Customer cust = dao.openAccount(customerDto);
			return cust.getCustId();
		} catch (Exception e) {
			throw new ServiceException("Something went wrong.");
		}
	}

	public String documentUpload(MultipartFile file) {
		String imageUploadLocation = "E:\\Banking (2)\\Banking\\src\\assets\\uploads\\";
		String fileName = file.getOriginalFilename();
		String AlphaNumericString = "0123456789";
		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}

		String extension = fileName.split("\\.")[1];
		String targetFile = imageUploadLocation + fileName.split("\\.")[0] + sb.toString() + "." + extension;
		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(targetFile));
			return fileName.split("\\.")[0] + sb.toString() + "." + extension;
		} catch (IOException e) {
			throw new ServiceException("Document upload failed!");
		}
	}

	public Customer searchCustomerById(int custId) {
		try {
			Customer cust = new Customer();
			cust = dao.searchCustomerById(custId);
			return cust;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public String updateProfile(CustomerDto customerDto) {
		try {
			Customer customer = dao.updateProfile(customerDto);
			return "User Profile updated successfully!! ";
		} catch (Exception e) {
			throw new ServiceException("Unexpected Error occured!!!User Profile Updation failed!!");
		}
	}

	public List<TopFiveTransactionDto> findTopFiveTransactions(int accountNumber) {
		List<TopFiveTransactionDto> topTrans = new ArrayList<>();
		List<Transaction> transactions = new ArrayList<>();
		transactions = dao.findtoprec(accountNumber);

		for (Transaction t : transactions) {
			TopFiveTransactionDto top = new TopFiveTransactionDto();
			top.setTransactionId(t.getTransactionId());
			top.setAccountNumber(t.getAccount().getAccountNumber());
			top.setAmount(t.getAmount());
			top.setTransactionDate(t.getTransactionDate());
			top.setTransactionMode(t.getMode());
			top.setTransactionType(t.getTransactionType());

			topTrans.add(top);
		}
		return topTrans;
	}

	public boolean isCustomerExists(int accountNumber) {
		if (dao.isCustomerExists(accountNumber)) {
			return true;
		}
		return false;
	}

	public AccountSummaryDto accountSummary(int accountNumber) {
		AccountSummaryDto accSummDto = new AccountSummaryDto();
		Account acc = new Account();
		acc = dao.accountSummary(accountNumber);

		accSummDto.setAccountNumber(acc.getAccountNumber());
		accSummDto.setBalance(acc.getBalance());
		accSummDto.setAccountType(acc.getAccountType());

		return accSummDto;
	}

	public List<TopFiveTransactionDto> accountStatement(int accountNumber, LocalDate fromDate, LocalDate toDate) {
		try {
			List<TopFiveTransactionDto> topTrans = new ArrayList<>();
			List<Transaction> transactions = new ArrayList<>();
			transactions = dao.accountStatement(accountNumber, fromDate, toDate);

			for (Transaction t : transactions) {
				TopFiveTransactionDto top = new TopFiveTransactionDto();
				top.setTransactionId(t.getTransactionId());
				top.setAccountNumber(t.getAccount().getAccountNumber());
				top.setAmount(t.getAmount());
				top.setTransactionDate(t.getTransactionDate());
				top.setTransactionMode(t.getMode());
				top.setTransactionType(t.getTransactionType());

				topTrans.add(top);
			}
			return topTrans;
		} catch (Exception e) {
			throw new ServiceException("Something went wrong");
		}
	}

	public String fundTransfer(String fromAccount, String toAccount, double amount, TransactionType type,
			String password) {
		try {
			Account fromAccount1 = dao.accountSummary(Integer.parseInt(fromAccount));
			Account toAccount1 = dao.accountSummary(Integer.parseInt(toAccount));
			if (amount < 100) {
				throw new MinimumAmountException("Amount must be greater than 100");
			} else if (amount > fromAccount1.getBalance()) {
				throw new InsufficientFundsException("Insufficent balance");
			} else {
				Transaction transaction = dao.fundTransfer(fromAccount1, toAccount1, amount, type, password);
				return "Fund transfer successful. Transaction ID: " + transaction.getTransactionId();
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public RegisterUserDto signup(User user) {
		try {
			return dao.signup(user);
		} catch (Exception e) {
			throw new ServiceException("Registration Failed.");
		}
	}

	public int getCustomerId(int accountNumber) {
		return dao.getCustomerId(accountNumber);
	}

	public User login(int userId, String password) {
		try {
			if (dao.checkErrorLoginCount(userId))
				return dao.login(userId, password);
			else
				throw new ServiceException("User login attempts limit exceeded, Retry login after 24 hours");
		} catch (ServiceException serviceException) {
			throw new ServiceException(serviceException.getMessage());
		} catch (Exception e) {
			ErrorLogin errorLogin = new ErrorLogin();
			errorLogin.setDateAndTime(LocalDateTime.now());
			errorLogin.setUserId(String.valueOf(userId));
			dao.saveErrorData(errorLogin);
			throw new ServiceException(e.getMessage());
		}
	}

	public AdminLoginStatus adminLogin(int adminId, String adminPassword) {
		AdminLoginStatus status;
		status=dao.adminLogin(adminId, adminPassword);
		return status;
	}

	public Admin addAdmin() {
		return dao.addAdmin();
	}

	public List<String> getErrorLogins() {
		try {
			return dao.getErrorLogins();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public String unblockAccount(String userId) {
		try {
			dao.unblockAccount(userId);
			return "Unblocked SuccessFully";
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<CustomerDto> pendingRequest() {
		if (dao.pendingRequest().size() >= 1) {
			List<CustomerDto> customerDtos = new ArrayList<>();
			List<Customer> customers = new ArrayList<>();
			customers = dao.pendingRequest();

			for (Customer cust : customers) {
				CustomerDto dto = new CustomerDto();

				dto.setCustId(cust.getCustId());
				dto.setName(cust.getName());
				dto.setGender(cust.getGender());
				dto.setPanCardNo(cust.getPanCardNo());
				dto.setPanFileName(cust.getPan());
				dto.setAadhaarNo(cust.getAadhaarNo());
				dto.setAadharFileName(cust.getAadhar());
				dto.setMobileNo(cust.getMobileNo());
				dto.setEmailId(cust.getEmailId());
				dto.setDateOfBirth(cust.getDateOfBirth());

				dto.setIncomeId(cust.getIncome().getIncomeId());
				dto.setIncomeSource(cust.getIncome().getIncomeSource());
				dto.setGrossIncome(cust.getIncome().getGrossIncome());
				dto.setIncomeSource(cust.getIncome().getIncomeSource());
				dto.setOccupationType(cust.getIncome().getOccupationType());

				dto.setAddressId(cust.getAddress().getAddressId());
				dto.setAddressLine1(cust.getAddress().getAddressLine1());
				dto.setAddressLine2(cust.getAddress().getAddressLine2());
				dto.setLandmark(cust.getAddress().getLandmark());
				dto.setCity(cust.getAddress().getCity());
				dto.setPincode(cust.getAddress().getPincode());
				dto.setState(cust.getAddress().getState());

				dto.setAccountStatus(cust.getAccount().get(0).getAccountStatus());
				dto.setAccountType(cust.getAccount().get(0).getAccountType());

				customerDtos.add(dto);
			}
			return customerDtos;

		} else {
			throw new ServiceException("No pending requests.");
		}
	}

	public String updatePendingRequests(int custId, AccountStatus response) {
		try {
			return dao.updatePendingRequest(custId, response);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public AccountStatus trackApplication(int custId) {
		try {
			return dao.trackApplication(custId);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
