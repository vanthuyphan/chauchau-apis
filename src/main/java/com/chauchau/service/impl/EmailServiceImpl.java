package com.chauchau.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.chauchau.model.Mail;
import com.chauchau.service.EmailService;

import freemarker.template.Template;

@Service
public class EmailServiceImpl implements EmailService {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired 
	private JavaMailSender javaMailSender;
	@Autowired 
	private FreeMarkerConfigurationFactoryBean freemarkerConfig;
	@Override
	public void sendEmail(Mail email) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Guest");
		model.put("company", "ChauChau-Insurance");
		model.put("signature", "dev@chauchau.com");
		model.put("content", email.getContent());
		model.put("location", "USA");
		email.setModel(model);
		MimeMessage message = javaMailSender.createMimeMessage();
		try{
		MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
		helper.addInline("logo.png", new ClassPathResource("classpath:/memorynotfound-logo.png"));
		
        Template t = freemarkerConfig.getObject().getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, email.getModel());
        helper.setTo(email.getTo().split(","));
        helper.setText(html, true);
        helper.setSubject(email.getSubject());
        helper.setFrom(email.getFrom());
		}
		catch (Exception e) {
			LOGGER.info("Cannot get read FTL"+ e);
		}
		javaMailSender.send(message);
	}

}
