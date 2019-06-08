package com.chauchau.config;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties emailPro = new Properties();

		try {
			emailPro.load(MailConfig.class.getClassLoader().getResource("email.properties").openStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
		// get Session
       
		mailSender.setHost(emailPro.getProperty("spring.mail.host"));
		mailSender.setPort(Integer.parseInt(emailPro.getProperty("spring.mail.port")));
		mailSender.setUsername(emailPro.getProperty("spring.mail.username"));
		mailSender.setPassword(emailPro.getProperty("spring.mail.password"));
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
	}

}
