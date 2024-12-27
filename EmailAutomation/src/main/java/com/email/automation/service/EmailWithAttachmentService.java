package com.email.automation.service;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;

public interface EmailWithAttachmentService {

	// Send email with an attachment immediately
		public String sendEmailwithAttachment(String recipient, String subject, String body, MultipartFile attachment)
				throws MessagingException;

		// Schedule email with attachment
		public String scheduleEmailwithAttachment(String recipient, String subject, String body, MultipartFile attachment,
				LocalDateTime scheduldTime);

		// Send scheduled emails with an attatchment(cron-based or triggered)
		public void sendScheduledEmailswithAttachment() throws MessagingException;
		
		void deleteMails();
		
		
}