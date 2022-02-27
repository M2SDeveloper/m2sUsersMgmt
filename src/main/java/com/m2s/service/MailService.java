package com.m2s.service;

import org.springframework.web.multipart.MultipartFile;

import com.m2s.message.MessageStatus;

public interface MailService {

	void sendEmail(String Template, Object object);
	
	void sendEmail(String toEmail,String fromEmail,String subject, String body,MultipartFile file);
	
	MessageStatus sendEmail(String toEmail[],String fromEmail,String subject, String body);
	
}