package com.lti.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.lti.dto.AdminLoginStatus;
import com.lti.dto.CustomerDto;
import com.lti.dto.ForgotPasswordDto;
import com.lti.dto.RegisterUserDto;
import com.lti.dto.Status.StatusCode;
import com.lti.entity.Account;
import com.lti.entity.AccountStatus;
import com.lti.entity.AccountType;
import com.lti.entity.Address;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.ErrorLogin;
import com.lti.entity.Income;
import com.lti.entity.Transaction;
import com.lti.entity.TransactionType;
import com.lti.entity.User;
import com.lti.exception.InsufficientFundsException;
import com.lti.exception.LoginFailException;
import com.lti.exception.ServiceException;
import com.lti.entity.Gender;

@Component
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public Customer openAccount(CustomerDto customerDto) {

		try {
			Customer customer = new Customer();
			customer.setName(customerDto.getName());
			customer.setAadhaarNo(customerDto.getAadhaarNo());
			customer.setMobileNo(customerDto.getMobileNo());
			customer.setGender(customerDto.getGender());
			customer.setDateOfBirth(customerDto.getDateOfBirth());
			customer.setPanCardNo(customerDto.getPanCardNo());
			customer.setEmailId(customerDto.getEmailId());
			customer.setAadhar(customerDto.getAadharFileName());
			customer.setPan(customerDto.getPanFileName());
			Customer cust = em.merge(customer);
			Address address = new Address();
			address.setAddressLine1(customerDto.getAddressLine1());
			address.setAddressLine2(customerDto.getAddressLine2());
			address.setCity(customerDto.getCity());
			address.setLandmark(customerDto.getLandmark());
			address.setState(customerDto.getState());
			address.setPincode(customerDto.getPincode());
			address.setCustomer(cust);
			Account account = new Account();
			account.setAccountStatus(customerDto.getAccountStatus());
			account.setAccountType(customerDto.getAccountType());
			account.setBalance(customerDto.getBalance());
			account.setCustomer(cust);
			Income income = new Income();
			income.setOccupationType(customerDto.getOccupationType());
			income.setIncomeSource(customerDto.getIncomeSource());
			income.setGrossIncome(customerDto.getGrossIncome());
			income.setCustomer(cust);
			em.merge(account);
			em.merge(address);
			em.merge(income);
			return cust;
		} catch (Exception e) {
			throw new ServiceException("Unexpected error occured.");
		}
	}

	@Transactional
	public Customer searchCustomerById(int custId) {
		Customer cust = em.find(Customer.class, custId);
		return cust;
	}

	@Transactional
	public Customer updateProfile(CustomerDto customerDto) {

		Customer customer = new Customer();
		customer.setCustId(customerDto.getCustId());
		customer.setName(customerDto.getName());
		customer.setMobileNo(customerDto.getMobileNo());
		customer.setEmailId(customerDto.getEmailId());
		customer.setPanCardNo(customerDto.getPanCardNo());
		customer.setAadhaarNo(customerDto.getAadhaarNo());
		customer.setDateOfBirth(customerDto.getDateOfBirth());
		customer.setGender(customerDto.getGender());
		customer.setAadhar(customerDto.getAadharFileName());
		customer.setPan(customerDto.getPanFileName());

		Address address = new Address();
		address.setAddressId(customerDto.getAddressId());
		address.setAddressLine1(customerDto.getAddressLine1());
		address.setAddressLine2(customerDto.getAddressLine2());
		address.setCity(customerDto.getCity());
		address.setLandmark(customerDto.getLandmark());
		address.setState(customerDto.getState());
		address.setPincode(customerDto.getPincode());
		address.setCustomer(customer);

		Income income = new Income();
		income.setIncomeId(customerDto.getIncomeId());
		income.setOccupationType(customerDto.getOccupationType());
		income.setIncomeSource(customerDto.getIncomeSource());
		income.setGrossIncome(customerDto.getGrossIncome());
		income.setCustomer(customer);

		em.merge(income);
		em.merge(address);

		Customer updatedProfile = em.merge(customer);
		return updatedProfile;
	}

	@Transactional
	public Account accountSummary(int accountNumber) {
		return em.find(Account.class, accountNumber);
	}

	@Transactional
	public List<Transaction> accountStatement(int accountNumber, LocalDate fromDate, LocalDate toDate) {
		String jpql = "select t from Transaction t where t.account.accountNumber=:accno and t.transactionDate between to_date(:fromDate) and to_date(:toDate)";
		TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
		query.setParameter("accno", accountNumber);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		return query.getResultList();
	}

	@Transactional
	public boolean checkTransactionPassword(int accountNumber, String password) {
		String jpql = "select u from User u where u.accountNumber=:accNo and u.transactionPassword=:tpwd";
		TypedQuery<User> query = em.createQuery(jpql, User.class);

		query.setParameter("accNo", accountNumber);
		query.setParameter("tpwd", password);

		try {
			User user = query.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public Transaction fundTransfer(Account fromAccount, Account toAccount, double amount, TransactionType type,
			String password) {
		if (checkTransactionPassword(fromAccount.getAccountNumber(), password)) {
			Transaction transaction = new Transaction();
			transaction.setAccount(toAccount);
			transaction.setAmount(amount);
			transaction.setTransactionDate(LocalDate.now());
			transaction.setTransactionType(type);
			transaction.setMode("Credit");
			em.persist(transaction);
			Transaction transaction1 = new Transaction();
			transaction1.setAccount(fromAccount);
			transaction1.setAmount(amount);
			transaction1.setTransactionDate(LocalDate.now());
			transaction1.setTransactionType(type);
			transaction1.setMode("Debit");
			em.persist(transaction1);
			fromAccount.setBalance(fromAccount.getBalance() - amount);
			em.merge(fromAccount);
			toAccount.setBalance(toAccount.getBalance() + amount);
			em.merge(toAccount);
			return transaction;
		} else {
			throw new ServiceException("Authentication failed");
		}
	}

	@Transactional
	public Beneficiary addBeneficiary(Beneficiary beneficiary) {
		em.persist(beneficiary);
		return beneficiary;
	}

	@Transactional
	public List<Beneficiary> viewAllBeneficiaries(int accountNumber) {
		String jpql = "select b from Beneficiary b where b.customerAccountNumber=:accNo";
		TypedQuery<Beneficiary> query = em.createQuery(jpql, Beneficiary.class);
		query.setParameter("accNo", accountNumber);
		return query.getResultList();
	}

	@Transactional
	public void deleteBeneficiary(int beneficiaryId) {
		Beneficiary beneficiary = findBeneficiaryById(beneficiaryId);
		em.remove(beneficiary);
	}

	@Transactional
	public Beneficiary findBeneficiaryById(int beneficiaryId) {
		return em.find(Beneficiary.class, beneficiaryId);
	}

	@Transactional
	public List<Transaction> findtoprec(int accountNumber) {
		String jpql = "select t from Transaction t where t.account.accountNumber=:accNo and rownum<=5 ORDER BY transactionId DESC";
		TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
		query.setParameter("accNo", accountNumber);
		return query.getResultList();
	}

	@Transactional
	public boolean isCustomerExists(int accountNumber) {
		return em.find(Account.class, accountNumber) != null;
	}

	@Transactional
	public boolean isCustExists(int custId) {
		return em.find(Customer.class, custId) != null;
	}

	@Transactional
	public List<String> getErrorLogins() {
		String jpql = "SELECT el.userId from ErrorLogin el GROUP BY el.userId HAVING count(el.userId)>=3";
		return em.createQuery(jpql, String.class).getResultList();
	}

	@Transactional
	public void unblockAccount(String userId) {
		String jpql = "delete from ErrorLogin el where el.userId= :userId";
		em.createQuery(jpql).setParameter("userId", userId).executeUpdate();
	}

	@Transactional
	public boolean checkErrorLoginCount(int userId) {
		Long count = (Long) em
				.createQuery(
						"select count(e.userId) from ErrorLogin e where e.userId = :id and e.dateAndTime>= SYSDATE - 1")
				.setParameter("id", String.valueOf(userId)).getSingleResult();
		return count < 3 ? true : false;
	}

	@Transactional
	public void saveErrorData(ErrorLogin errLogin) {
		em.persist(errLogin);
	}

	@Transactional
	public User login(int userId, String password) {
		try {
			String jpql = "select u from User u where u.userId=:uid and u.loginPassword=:pwd";
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			query.setParameter("uid", userId);
			query.setParameter("pwd", password);

			User user = query.getSingleResult();
			return user;
		} catch (Exception e) {
			throw new LoginFailException("Invalid User ID/password");
		}
	}

	@Transactional
	public int getCustomerId(int accountNumber) {
		Account acc = em.find(Account.class, accountNumber);
		return acc.getCustomer().getCustId();
	}

	@Transactional
	public RegisterUserDto signup(User user) {
		em.persist(user);
		em.flush();

		int custId = getCustomerId(user.getAccountNumber());
		String email = searchCustomerById(custId).getEmailId();

		RegisterUserDto dto = new RegisterUserDto();
		dto.setUserId(String.valueOf(user.getUserId()));
		dto.setEmail(email);

		return dto;
	}

	@Transactional
	public AdminLoginStatus adminLogin(int adminId, String adminPassword) {
		String jpql = "select a from Admin a where a.adminId=:aid and a.adminPassword=:pwd";

		TypedQuery<Admin> query = em.createQuery(jpql, Admin.class);
		query.setParameter("aid", adminId);
		query.setParameter("pwd", adminPassword);

		Admin admin;

		try {
			admin = query.getSingleResult();
			AdminLoginStatus status = new AdminLoginStatus();
			status.setAdminId(admin.getAdminId());
			status.setName(admin.getAdminName());
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Login Successful");
			return status;
		} catch (Exception e) {
			AdminLoginStatus status = new AdminLoginStatus();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage("Login failed");
			return status;
		}
	}

	@Transactional
	public Admin addAdmin() {
		Admin admin = new Admin();
		admin.setAdminPassword("John");
		admin.setName("admin");

		em.persist(admin);
		return admin;
	}

	@Transactional
	public List<Customer> pendingRequest() {
		String jpql = "select c from Customer c join Account a on c.custId=a.customer.custId and a.accountStatus=:acSt";
		return em.createQuery(jpql, Customer.class).setParameter("acSt", AccountStatus.Pending).getResultList();
	}

	@Transactional
	public String updatePendingRequest(int custId, AccountStatus response) {
		String jpql = "update Account a set a.accountStatus=:accSt where a.customer.custId=:cId";
		try {
			em.createQuery(jpql).setParameter("accSt", response).setParameter("cId", custId).executeUpdate();
			return "Account status changed.";
		} catch (Exception e) {
			throw new ServiceException("Account status change failed");
		}
	}

	@Transactional
	public ForgotPasswordDto forgotPassword(int userId) {
		String jpql = "update User u set u.loginPassword=:pwd where u.userId=:uId";

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(8);

		for (int i = 0; i < 8; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}
		try {
			em.createQuery(jpql).setParameter("pwd", sb.toString()).setParameter("uId", userId).executeUpdate();
			User user = em.createQuery("select u from User u where u.userId=:uid", User.class)
					.setParameter("uid", userId).getSingleResult();
			int custId = getCustomerId(user.getAccountNumber());
			String email = searchCustomerById(custId).getEmailId();
			ForgotPasswordDto dto = new ForgotPasswordDto();
			dto.setEmail(email);
			dto.setPassword(sb.toString());
			return dto;
		} catch (Exception e) {
			throw new ServiceException("Password updation failed.");
		}
	}

	@Transactional
	public String changePassword(int userId, String loginPassword, String transactionPassword) {
		try {
			String jpql = "update User u set u.loginPassword=:lpwd, u.transactionPassword=:tpwd where u.userId=:uId";
			em.createQuery(jpql).setParameter("lpwd", loginPassword).setParameter("tpwd", transactionPassword)
					.setParameter("uId", userId).executeUpdate();
			return "Passwords Changed Successfully.";
		} catch (Exception e) {
			throw new ServiceException("Something went wrong");
		}
	}

	@Transactional
	public AccountStatus trackApplication(int custId) {
		String jpql = "select a from Account a join Customer c on c.custId=a.customer.custId where c.custId=:cid";
		TypedQuery<Account> query = em.createQuery(jpql, Account.class);
		query.setParameter("cid", custId);
		try {
			Account acc = query.getSingleResult();
			return acc.getAccountStatus();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
