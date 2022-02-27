package com.m2s.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactory;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.m2s")
public class MailConfig {
	
	@Autowired
	ConfigProperties configProp;
	
	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		// Using gmail.
		mailSender.setHost(configProp.getConfigValue("spring.mail.host"));
		mailSender.setPort(587);
		mailSender.setUsername(configProp.getConfigValue("spring.mail.username"));
		mailSender.setPassword(configProp.getConfigValue("spring.mail.password"));

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "false");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

	/*
	 * Velocity configuration.
	 */
	@Bean
	public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
		VelocityEngineFactory factory = new VelocityEngineFactory();
		factory.setResourceLoaderPath("/templates/");
		/*Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		factory.setVelocityProperties(props);*/
		return factory.createVelocityEngine();
	}
	
}
