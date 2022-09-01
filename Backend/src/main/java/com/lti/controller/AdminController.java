package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AdminLoginDto;
import com.lti.dto.AdminLoginStatus;
import com.lti.dto.PendingRequestDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusCode;
import com.lti.dto.UpdatePendingRequestDto;
import com.lti.entity.AccountStatus;
import com.lti.entity.Admin;
import com.lti.entity.Customer;
import com.lti.service.CustomerService;
import com.lti.service.EmailService;

@RestController
@RequestMapping("/admin")
@CrossOrigin()
public class AdminController {
	@Autowired
	CustomerService customerService;

	@Autowired
	EmailService emailService;

	@PostMapping(value = "/adminLogin")
	public AdminLoginStatus adminLogin(@RequestBody AdminLoginDto adminDto) {
		return customerService.adminLogin(adminDto.getAdminId(), adminDto.getAdminPassword());
	}

	@GetMapping(value = "/pendingRequests")
	public PendingRequestDto getPendingRequests() {
		PendingRequestDto dto = new PendingRequestDto();
		try {
			dto.setCustomers(customerService.pendingRequest());
			dto.setStatusCode(StatusCode.SUCCESS);
			dto.setStatusMessage("Fetch Sucessful.");
		} catch (Exception e) {
			dto.setStatusCode(StatusCode.FAILURE);
			dto.setStatusMessage(e.getMessage());
		}
		return dto;
	}

	@PutMapping(value = "/updateRequest")
	public Status updateRequest(@RequestBody UpdatePendingRequestDto dto) {
		try {
			Status status = new Status();
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage(customerService.updatePendingRequests(dto.getCustId(), dto.getAccountStatus()));

			Customer cust = customerService.searchCustomerById(dto.getCustId());

			if (dto.getAccountStatus() == AccountStatus.Approved) {
				String email = cust.getEmailId();
				String text = "Your account has been successfully verified and is now approved. Your account number is: "
						+ cust.getAccount().get(0).getAccountNumber();
				String subject = "Account Application Status";

				emailService.sendEmailForSignup(email, text, subject);
			} else {
				String email = cust.getEmailId();
				String text = "We are sorry to inform you that your application for account opening is rejected.\nKindly re-apply with correct details and KYC.";
				String subject = "Account Application Status";

				emailService.sendEmailForSignup(email, text, subject);
			}
			return status;
		} catch (Exception e) {
			Status status = new Status();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@PostMapping(value = "/createAdmin")
	public Admin addAdmin() {
		return customerService.addAdmin();
	}
}
