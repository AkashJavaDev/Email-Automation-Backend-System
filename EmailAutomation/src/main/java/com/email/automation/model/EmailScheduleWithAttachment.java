package com.email.automation.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_schedule_with_attachment")
public class EmailScheduleWithAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "recipient", nullable = false)
	private String recipient;
	
	@Column(name = "subject", nullable = false)
	private String subject;
	
	@Column(name = "body", nullable = false)
	private String body;
	
	@Column(name = "filename", nullable = false)
	private String fileName;
	
	@Lob
	@Column(name = "attachment", nullable = false)
	private byte[] attachment;
	
	@Column(name = "scheduleTime", nullable = false)
	private LocalDateTime scheduleTime;

	public EmailScheduleWithAttachment() {
		super();
	}

	public EmailScheduleWithAttachment(String recipient, String subject, String body, String fileName,
			byte[] attachment, LocalDateTime scheduleTime) {
		super();
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
		this.attachment = attachment;
		this.fileName = fileName;
		this.scheduleTime = scheduleTime;
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

	public void setRecipient(String recipient) {
		this.recipient = recipient;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public LocalDateTime getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(LocalDateTime scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

}
