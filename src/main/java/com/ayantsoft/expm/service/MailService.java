package com.ayantsoft.expm.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailService implements Runnable {

	private String userName;
	private String password;
	private String subject;
	private String mailMessage;
	private String toEmail;
	private List<String> filePathList;

	@Override
	public void run() {
		this.sendMail();
	}


	public void setParameters(String userName,String password,String subject,String mailMessage,String toEmail,List<String> filePathList){
		this.userName = userName;
		this.password = password;
		this.subject = subject;
		this.mailMessage = mailMessage;
		this.toEmail = toEmail;
		this.filePathList = filePathList;
	}


	private Session getSession() {
		Session session = null;
		try{
			Properties properties = new Properties();
			
			properties.put("mail.smtp.host","mail.ayantsoft.com");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "true");
			properties.put("mail.smtp.port","465");
			properties.put("mail.smtp.socketFactory.port","465");
			properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			

			session = Session.getInstance(properties,new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName,password);
				}
			});


		}catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}



	private void sendMail(){
		try{
			Session session = getSession();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(mailMessage);

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mailMessage, "text/html");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (filePathList != null && filePathList.size() > 0) {
				for (String filePath : filePathList) {
					MimeBodyPart attachPart = new MimeBodyPart();
					try {
						attachPart.attachFile(filePath);
					}catch (IOException ex){
						ex.printStackTrace();
					}
					multipart.addBodyPart(attachPart);
				}
			}
			message.setContent(multipart);
			Transport.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
