package com.email.automation.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "to_emails")
public class ToEmails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "recipient", nullable = false)
	private String recipient;

	@Column(name = "subject", nullable = false)
	private String subject;

	@Column(name = "body", nullable = false)
	private String body;

	@Column(name = "scheduledtime", nullable = false)
	private LocalDateTime scheduledTime;

	public ToEmails() {
		super();
	}

	public ToEmails(String recipient, String subject, String body, LocalDateTime scheduledTime) {
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
		this.scheduledTime = scheduledTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String to) {
		this.recipient = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

}
