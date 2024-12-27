package com.email.automation.service;

import java.time.LocalDateTime;

public interface EmailService {

	// Send email immediately
	public String sendEmail(String recipient, String subject, String body);

	// Schedule an email
	public String scheduleEmail(String recipient, String subject, String body, LocalDateTime scheduledTime);

	// Send scheduled emails (cron-based or triggered)
	public void sendScheduledEmail();

}