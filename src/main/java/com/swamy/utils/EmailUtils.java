package com.swamy.utils;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public String sendEmail(String subject, String receiver, String body) {

		String email_response = null;

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setSubject(subject);
			messageHelper.setTo(receiver);
			messageHelper.setText(body);
			// Adding the attachment
			FileSystemResource file = new FileSystemResource(
					new File("C:\\Users\\LENOVO PC\\OneDrive\\Pictures\\Images\\success.jpg"));

			messageHelper.addAttachment(file.getFilename(), file);
			javaMailSender.send(messageHelper.getMimeMessage());
			email_response = "Email Sent Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			email_response = "Error While Sending Email";
		}
		return email_response;
	}
}
