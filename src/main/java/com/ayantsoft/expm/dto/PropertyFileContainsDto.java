package com.ayantsoft.expm.dto;

import java.io.Serializable;

public class PropertyFileContainsDto implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 4811365552498118108L;
	
	private String notificationEmailId;
	private String notificationEmailPassword;
	private String adminEmail;
	private String accountEmail;
	private String subAdminEmail;
	
	
	public String getNotificationEmailId() {
		return notificationEmailId;
	}
	public void setNotificationEmailId(String notificationEmailId) {
		this.notificationEmailId = notificationEmailId;
	}
	public String getNotificationEmailPassword() {
		return notificationEmailPassword;
	}
	public void setNotificationEmailPassword(String notificationEmailPassword) {
		this.notificationEmailPassword = notificationEmailPassword;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAccountEmail() {
		return accountEmail;
	}
	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}
	public String getSubAdminEmail() {
		return subAdminEmail;
	}
	public void setSubAdminEmail(String subAdminEmail) {
		this.subAdminEmail = subAdminEmail;
	}
	
	

}
