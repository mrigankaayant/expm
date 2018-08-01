package com.ayantsoft.expm.jsf.view;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.dto.PropertyFileContainsDto;
import com.ayantsoft.expm.hibernate.dao.AccountDao;
import com.ayantsoft.expm.hibernate.dao.BankDao;
import com.ayantsoft.expm.hibernate.dao.BankInternalDao;
import com.ayantsoft.expm.hibernate.dao.UserDao;
import com.ayantsoft.expm.hibernate.dao.VendorDao;
import com.ayantsoft.expm.hibernate.pojo.Account;
import com.ayantsoft.expm.hibernate.pojo.Bank;
import com.ayantsoft.expm.hibernate.pojo.BankInternal;
import com.ayantsoft.expm.hibernate.pojo.User;
import com.ayantsoft.expm.hibernate.pojo.Vendor;


@ManagedBean(eager=true)
@ApplicationScoped
public class AppDataBean implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -7794432783634471605L;


	@ManagedProperty(value="#{bankDao}")
	private BankDao bankDao;

	@ManagedProperty(value="#{accountDao}")
	private AccountDao accountDao;

	@ManagedProperty(value="#{vendorDao}")
	private VendorDao vendorDao;

	@ManagedProperty(value="#{bankInternalDao}")
	private BankInternalDao bankInternalDao;
	
	@ManagedProperty(value="#{userDao}")
	private UserDao userDao;
	
	


	private List<Bank> bankList = new ArrayList<Bank>();
	private List<Account> accountList = new ArrayList<Account>();
	private List<Vendor> vendorList = new ArrayList<Vendor>();
	private List<BankInternal> bankInternalList = new ArrayList<BankInternal>();
	private List<User> userList = new ArrayList<User>();
	private PropertyFileContainsDto propertyFileContainsDto = new PropertyFileContainsDto();

	@PostConstruct
	public void init(){
		bankList = bankDao.findBankList();
		accountList = accountDao.findAccountList();
		vendorList = vendorDao.findVendorList();
		bankInternalList = bankInternalDao.findBankList();
		userList = userDao.findAllUser();

		try{
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expm.properties");
			prop.load(inputStream);

			propertyFileContainsDto.setNotificationEmailId(prop.getProperty("notificationEmailId"));
			propertyFileContainsDto.setNotificationEmailPassword(prop.getProperty("notificationEmailPassword"));
			propertyFileContainsDto.setAdminEmail(prop.getProperty("adminEmail"));
			propertyFileContainsDto.setAccountEmail(prop.getProperty("accountEmail"));
			propertyFileContainsDto.setSubAdminEmail("subAdminEmail");
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void accountListInitialize(){
		accountList = accountDao.findAccountList();
	}

	public void vendorListInitaalize(){
		vendorList = vendorDao.findVendorList();
	}

	public void bankInternalListInitialize(){
		bankInternalList = bankInternalDao.findBankList();
	}



	// setter and getter


	public BankDao getBankDao() {
		return bankDao;
	}


	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}


	public List<Bank> getBankList() {
		return bankList;
	}


	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}



	public AccountDao getAccountDao() {
		return accountDao;
	}



	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}



	public VendorDao getVendorDao() {
		return vendorDao;
	}



	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}



	public List<Account> getAccountList() {
		return accountList;
	}



	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}



	public List<Vendor> getVendorList() {
		return vendorList;
	}



	public void setVendorList(List<Vendor> vendorList) {
		this.vendorList = vendorList;
	}


	public BankInternalDao getBankInternalDao() {
		return bankInternalDao;
	}


	public void setBankInternalDao(BankInternalDao bankInternalDao) {
		this.bankInternalDao = bankInternalDao;
	}


	public List<BankInternal> getBankInternalList() {
		return bankInternalList;
	}


	public void setBankInternalList(List<BankInternal> bankInternalList) {
		this.bankInternalList = bankInternalList;
	}


	public PropertyFileContainsDto getPropertyFileContainsDto() {
		return propertyFileContainsDto;
	}


	public void setPropertyFileContainsDto(PropertyFileContainsDto propertyFileContainsDto) {
		this.propertyFileContainsDto = propertyFileContainsDto;
	}


	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public List<User> getUserList() {
		return userList;
	}


	public void setUserList(List<User> userList) {
		this.userList = userList;
	}





}
