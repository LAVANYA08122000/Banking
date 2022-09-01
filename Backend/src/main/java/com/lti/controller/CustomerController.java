package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AccountStatementDto;
import com.lti.dto.AccountStatementStatus;
import com.lti.dto.AccountSummaryDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.ChangePasswordDto;
import com.lti.dto.CustomerDetails;
import com.lti.dto.CustomerDto;
import com.lti.dto.DocumentUploadDto;
import com.lti.dto.ErrorLoginStatus;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.FundTransferDto;
import com.lti.dto.LoginDto;
import com.lti.dto.OpenAccountDto;
import com.lti.dto.RegisterUserDto;
import com.lti.dto.SendAllBeneficiariesDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusCode;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.TopFiveTransactionsStatus;
import com.lti.dto.TrackApplicationDto;
import com.lti.dto.UserLoginStatus;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.entity.Customer;
import com.lti.entity.User;
import com.lti.exception.ServiceException;
import com.lti.service.CustomerService;
import com.lti.service.EmailService;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	EmailService emailService;

//	@RequestMapping(value = "/addBeneficiary", method = RequestMethod.POST)
	@PostMapping(value = "/addBeneficiary")
	public Status addBeneficiary(@RequestBody BeneficiaryAccountDto beneficiary) {
		try {
			customerService.addBeneficiary(beneficiary);
			Status status = new Status();
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Beneficiary Added Successfully");
			return status;

		} catch (ServiceException e) {
			Status status = new Status();
			status.setStatusMessage(e.getMessage());
			status.setStatusCode(StatusCode.FAILURE);
			return status;
		}
	}

	@GetMapping(value = "/findBeneficiary/{beneficiaryId}")
	public BeneficiaryAccountDto findBeneficiaryById(@PathVariable int beneficiaryId) {
		return customerService.findBeneficiary(beneficiaryId);
	}

	@GetMapping(value = "/viewAllBeneficiaries/{accNo}")
	public SendAllBeneficiariesDto viewAllBeneficiaries(@PathVariable int accNo) {
		try {
			List<ViewAllBeneficiariesDto> list = customerService.viewAllBeneficiaries(accNo);
			SendAllBeneficiariesDto status = new SendAllBeneficiariesDto();
			status.setBeneficiaryDto(list);
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Beneficiary Fetched Successfully");
			return status;
		} catch (ServiceException e) {
			SendAllBeneficiariesDto status = new SendAllBeneficiariesDto();
			status.setBeneficiaryDto(null);
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@DeleteMapping(value = "/deleteBeneficiary/{beneficiaryId}")
	public String deleteBeneficiary(@PathVariable int beneficiaryId) {
		String message = customerService.deleteBeneficiary(beneficiaryId);
		return message;
	}

	@PostMapping(value = "/documentUpload")
	public Status documentUpload(DocumentUploadDto dto) {
		Status status = new Status();
		try {
			status.setStatusMessage(customerService.documentUpload(dto.getFile()));
			status.setStatusCode(StatusCode.SUCCESS);
		} catch (Exception e) {
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
		}
		return status;
	}

	@PostMapping(value = "/openAccount")
	public OpenAccountDto openAccount(@RequestBody CustomerDto customerDto) {
		try {
//			DocumentUploadDto docDto = customerService.documentUpload(customerDto.getAadharCardFile(), customerDto.getPanCardFile());
//			
//			customerDto.setAadharFileName(docDto.getAadharCardFileName());
//			customerDto.setPanFileName(docDto.getPanCardFileName());

			int custId = customerService.openAccount(customerDto);
			OpenAccountDto dto = new OpenAccountDto();
			dto.setStatusCode(StatusCode.SUCCESS);
			dto.setStatusMessage("Registration Successful. Your customer ID is " + custId);
			dto.setCustId(custId);
			return dto;

		} catch (Exception e) {
			e.printStackTrace();
			OpenAccountDto dto = new OpenAccountDto();
			dto.setStatusCode(StatusCode.FAILURE);
			dto.setStatusMessage(e.getMessage());
			return dto;
		}
	}

	@GetMapping("/searchCustomer/{custId}")
	public CustomerDetails searchCustomerById(@PathVariable int custId) {

		try {
			Customer cust = customerService.searchCustomerById(custId);
			CustomerDto dto = new CustomerDto();

			dto.setCustId(cust.getCustId());
			dto.setName(cust.getName());
			dto.setGender(cust.getGender());
			dto.setPanCardNo(cust.getPanCardNo());
			dto.setAadhaarNo(cust.getAadhaarNo());
			dto.setMobileNo(cust.getMobileNo());
			dto.setEmailId(cust.getEmailId());
			dto.setDateOfBirth(cust.getDateOfBirth());
			dto.setAadharFileName(cust.getAadhar());
			dto.setPanFileName(cust.getPan());

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

			CustomerDetails details = new CustomerDetails();
			details.setCustomer(dto);
			details.setStatusCode(StatusCode.SUCCESS);
			details.setStatusMessage("Fetch succesfull");
			return details;
		} catch (Exception e) {
			CustomerDetails details = new CustomerDetails();
			details.setStatusCode(StatusCode.FAILURE);
			details.setStatusMessage(e.getMessage());
			return details;
		}
	}

	@PutMapping("/updateProfile")
	public Status updateUserProfile(@RequestBody CustomerDto customerDto) {
		try {
			String message = customerService.updateProfile(customerDto);
			Status status = new Status();
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage(message);
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			Status status = new Status();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
//		return message;
	}

	@GetMapping(value = "/accountSummary/{accountNumber}")
	public AccountSummaryDto accountSummary(@PathVariable int accountNumber) {
		return customerService.accountSummary(accountNumber);
	}

	@GetMapping(value = "/findTopFiveTransactions/{accountNumber}")
	public TopFiveTransactionsStatus findTopFiveTransactions(@PathVariable int accountNumber) {
		try {
			TopFiveTransactionsStatus status = new TopFiveTransactionsStatus();
			List<TopFiveTransactionDto> transactions = customerService.findTopFiveTransactions(accountNumber);
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Data fetched successfully");
			status.setTopTransactions(transactions);
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TopFiveTransactionsStatus status = new TopFiveTransactionsStatus();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@GetMapping(value = "/isCustomerExists/{accountNumber}")
	public boolean isCustomerExists(@PathVariable int accountNumber) {
		return customerService.isCustomerExists(accountNumber);
	}

	@PostMapping(value = "/accountStatement")
	public AccountStatementStatus accountStatement(@RequestBody AccountStatementDto dto) {
		try {
			AccountStatementStatus status = new AccountStatementStatus();
			status.setTransactions(
					customerService.accountStatement(dto.getAccountNumber(), dto.getFromDate(), dto.getToDate()));
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Data fetch successful");
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			AccountStatementStatus status = new AccountStatementStatus();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@PostMapping(value = "/fundTransfer")
	public Status fundTransfer(@RequestBody FundTransferDto ftDto) {
		try {
			Status status = new Status();
			System.out.println(ftDto.getTransactionType());
			status.setStatusMessage(customerService.fundTransfer(ftDto.getFromAccount(), ftDto.getToAccount(),
					ftDto.getAmount(), ftDto.getTransactionType(), ftDto.getPassword()));
			status.setStatusCode(StatusCode.SUCCESS);
			return status;
		} catch (Exception e) {
			Status status = new Status();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			System.err.println(status.getStatusCode());
			return status;
		}

	}

	@GetMapping(value = "/forgotPassword/{userId}")
	public Status forgotPassword(@PathVariable int userId) {
		Status status = new Status();
		try {
			ForgotPasswordDto dto = customerService.forgotPassword(userId);
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("New generated password has been mailed to you");

			String email = dto.getEmail();
			String text = "Password has been changed.\nYour new generated password is: " + dto.getPassword()
					+ "\nNote: Kindly login and change the password to your own one.";
			String subject = "Forgot Password";

			emailService.sendEmailForSignup(email, text, subject);
		} catch (Exception e) {
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
		}
		return status;
	}

	@PutMapping(value = "/changePassword")
	public Status changePassword(@RequestBody ChangePasswordDto dto) {
		Status status = new Status();
		try {
			status.setStatusMessage(customerService.changePassword(dto.getUserId(), dto.getLoginPassword(),
					dto.getTransactionPassword()));
			status.setStatusCode(StatusCode.SUCCESS);
			return status;
		} catch (Exception e) {

			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@GetMapping(value = "/getOTP/{custId}")
	public Status getOTP(@PathVariable int custId) {
		System.err.println(custId);
		try {
			String AlphaNumericString = "0123456789";
			StringBuilder sb = new StringBuilder(6);

			for (int i = 0; i < 6; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());

				sb.append(AlphaNumericString.charAt(index));
			}
			Status status = new Status();
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage(sb.toString());

			String email = customerService.searchCustomerById(custId).getEmailId();
			String text = "Your OTP for the fund transfer is: " + sb.toString();
			String subject = "OTP for Fund Transfer";

			emailService.sendEmailForSignup(email, text, subject);

			return status;
		} catch (Exception e) {
			Status status = new Status();
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public Status signup(@RequestBody User user) {
		try {
			Status status = new Status();
			RegisterUserDto dto = customerService.signup(user);

			status.setStatusMessage("Registration successful");
			status.setStatusCode(StatusCode.SUCCESS);

			String email = dto.getEmail();
			String text = "Registration Successful. Your generated user ID: " + dto.getUserId();
			String subject = "Registration Confirmation";

			emailService.sendEmailForSignup(email, text, subject);
			return status;
		} catch (Exception e) {
			Status status = new Status();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@PostMapping(value = "/login")
	public UserLoginStatus login(@RequestBody LoginDto loginDto) {
//		return customerService.login(loginDto.getUserId(), loginDto.getLoginPassword());
		try {
			User user = customerService.login(loginDto.getUserId(), loginDto.getLoginPassword());
			UserLoginStatus userLoginStatus = new UserLoginStatus();
			userLoginStatus.setStatusCode(StatusCode.SUCCESS);
			userLoginStatus.setStatusMessage("Login Successful");
			userLoginStatus.setUserId(user.getUserId());
			userLoginStatus.setAccountNumber(String.valueOf(user.getAccountNumber()));
			userLoginStatus.setCustId(customerService.getCustomerId(user.getAccountNumber()));
			return userLoginStatus;
		} catch (ServiceException e) {
			UserLoginStatus userLoginStatus = new UserLoginStatus();
			userLoginStatus.setStatusCode(StatusCode.FAILURE);
			userLoginStatus.setStatusMessage(e.getMessage());
			return userLoginStatus;
		}
	}

	@GetMapping(value = "/trackApplication/{custId}")
	public TrackApplicationDto trackApplication(@PathVariable int custId) {
		TrackApplicationDto dto = new TrackApplicationDto();

		try {
			dto.setStatusCode(StatusCode.SUCCESS);
			dto.setStatusMessage("Application fetch Successful.");
			dto.setAccountStatus(customerService.trackApplication(custId));
		} catch (Exception e) {
			dto.setStatusCode(StatusCode.FAILURE);
			dto.setStatusMessage(e.getMessage());
		}

		return dto;
	}

	@GetMapping(value = "/getErrorLogins")
	public ErrorLoginStatus getErrorLogin() {
		try {
//			return customerService.getErrorLogins();
			ErrorLoginStatus status = new ErrorLoginStatus();
			status.setErrorLogins(customerService.getErrorLogins());
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Fetch successful");
			return status;
		} catch (Exception e) {
			ErrorLoginStatus status = new ErrorLoginStatus();
			status.setStatusMessage(e.getMessage());
			status.setStatusCode(StatusCode.FAILURE);
			return status;
		}
	}

	@DeleteMapping(value = "/unblockAccount/{userId}")
	public Status unblockAccount(@PathVariable String userId) {
		Status status = new Status();

		try {
			status.setStatusMessage(customerService.unblockAccount(userId));
			status.setStatusCode(StatusCode.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			status.setStatusMessage(e.getMessage());
			status.setStatusCode(StatusCode.FAILURE);
		}

		return status;
	}
}
