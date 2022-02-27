package com.m2s.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.multipart.MultipartFile;

import com.m2s.config.ConfigProperties;
import com.m2s.entities.User;
import com.m2s.message.MessageStatus;
import com.m2s.service.MailService;



@SuppressWarnings("deprecation")
@Service("mailService")
public class MailServiceImpl implements MailService{

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	VelocityEngine velocityEngine;
	
	@Autowired
	ConfigProperties configProp;
	
	@Override
	public void sendEmail(String Template, Object object) {
			MimeMessage message = mailSender.createMimeMessage();
		 
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        User user=(User) object;
	 
	        Map<String, Object> model = new HashMap();
	        model.put("seat_company_name", "");
	        model.put("seat_console_URL", "");
	        model.put("url", "/#/auth/reset-password?Id="+"");
	        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, Template, model);
	 
	        try {
	        	 helper.setTo(user.getEmail());
				 helper.setFrom("");
				 helper.setBcc(configProp.getConfigValue("spring.mail.bcc"));
				 helper.setText(text, true); // set to html
			     helper.setSubject("Reset Password Request");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	       
	 
	        mailSender.send(message);

	}
	
	@Override
	public void sendEmail(String toEmail,String fromEmail,String subject, String body,MultipartFile file) {
		MimeMessage message = mailSender.createMimeMessage();
	 
        MimeMessageHelper helper = new MimeMessageHelper(message);
       
        try {
        	 helper.setTo(toEmail);
			 helper.setFrom(fromEmail);
			 helper.setBcc(configProp.getConfigValue("spring.mail.bcc"));
			 helper.setText(body, true); // set to html
		     helper.setSubject(subject);
		     helper.addAttachment(file.getOriginalFilename(),file);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        mailSender.send(message);
	}
	
	@Override
	public MessageStatus sendEmail(String[] toEmail,String fromEmail,String subject, String body) {
		MessageStatus msg=new MessageStatus();
		
		MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
       
        try {
        	 helper.setTo(toEmail);
			 helper.setFrom(fromEmail);
			 String [] bcclist=configProp.getConfigValue("spring.mail.bcc").split(",");
			 helper.setBcc(bcclist);
			 helper.setText(body, true); // set to html
		     helper.setSubject(subject);
		} catch (MessagingException e) {
			msg.setStatusCode(HttpServletResponse.SC_NOT_ACCEPTABLE);
			msg.setMessage("Email sending failed");
			e.printStackTrace();
			return msg;
		}
        mailSender.send(message);
        msg.setStatusCode(HttpServletResponse.SC_OK);
		msg.setMessage("Email send successfully");
        return msg;
	}

}
