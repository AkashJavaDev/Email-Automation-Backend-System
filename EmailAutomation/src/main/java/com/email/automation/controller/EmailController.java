package com.email.automation.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.automation.service.EmailService;
import com.email.automation.service.EmailWithAttachmentService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailWithAttachmentService emailWithAttachmentService;

	@DeleteMapping("/delete")
	public void deleteMails() {
		emailWithAttachmentService.deleteMails();
	}

	// send email immediately
	@PostMapping("/send")
	public String sendEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String body) {
		return emailService.sendEmail(recipient, subject, body);
	}

	// schedule email
	@PostMapping("/schedule")
	public String scheduleEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String body,
			@RequestParam String scheduleTime) {

		try {
			LocalDateTime scheduledTime = LocalDateTime.parse(scheduleTime);
			if (scheduledTime.isBefore(LocalDateTime.now())) {
				return "Error: The scheduled time is can not be in the past...!";
			}
			return emailService.scheduleEmail(recipient, subject, body, scheduledTime);
		} catch (Exception ex) {
			return "Invalid date.." + ex.getMessage();
		}
	}

	// Send email With an Attachment immediately
	@PostMapping("/send-with-attachment")
	public String sendEmailWithAttachment(@RequestParam String recipient, @RequestParam String subject,
			@RequestParam String body, @RequestParam MultipartFile attachment) throws MessagingException {

		if (attachment == null || attachment.isEmpty()) {
			return "Error: No attachment provided!";
		}

		return emailWithAttachmentService.sendEmailwithAttachment(recipient, subject, body, attachment);

	}

	// Schedule email With an Attachment immediately
	@PostMapping("/schedule-with-attachment")
	public String scheduleEmailWithAttachment(@RequestParam String recipient, @RequestParam String subject,
			@RequestParam String body, @RequestParam MultipartFile attachment, @RequestParam String scheduleTime)
			throws MessagingException {
		try {

			if (attachment == null || attachment.isEmpty()) {
				return "Error: No attachment provided!";
			}

			LocalDateTime scheduledTime = LocalDateTime.parse(scheduleTime);

			if (scheduledTime.isBefore(LocalDateTime.now())) {
				return "Error: The scheduled time is can not be in the past...!";
			}

			return emailWithAttachmentService.scheduleEmailwithAttachment(recipient, subject, body, attachment,
					scheduledTime);

		} catch (Exception ex) {
			return "Invalid date.." + ex.getMessage();
		}
	}
}
