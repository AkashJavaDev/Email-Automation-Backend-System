package com.email.automation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.email.automation.model.EmailScheduleWithAttachment;
import com.email.automation.repository.EmailWithAttachmentRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class EmailWithAttachmentServiceImpl implements EmailWithAttachmentService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EmailWithAttachmentRepository emailWithAttachmentRepository;

	@Override
	public String sendEmailwithAttachment(String recipient, String subject, String body, MultipartFile attachment)
			throws MessagingException {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setTo(recipient);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);
			mimeMessageHelper.addAttachment(attachment.getOriginalFilename(),
					new ByteArrayResource(attachment.getBytes()));
			javaMailSender.send(message);

			return "Email With Attachment Sent Successfully";
		} catch (Exception ex) {
			return "Failed to sent Email with Attachment!.." + ex.getMessage();
		}

	}

	@Override
	public String scheduleEmailwithAttachment(String recipient, String subject, String body, MultipartFile attachment,
			LocalDateTime scheduleTime) {
		try {
			if (recipient == null || recipient.isBlank()) {
				throw new IllegalArgumentException("Recipient cannot be null or empty");
			}
			if (attachment == null || attachment.isEmpty()) {
				throw new IllegalArgumentException("Attachment cannot be null or empty");
			}
			String fileName = attachment.getOriginalFilename();
			byte[] file = attachment.getBytes();
			EmailScheduleWithAttachment withAttachment = new EmailScheduleWithAttachment(recipient, subject, body,
					fileName, file, scheduleTime);
			emailWithAttachmentRepository.save(withAttachment);
			return "Email with Attachment is Scheduled Successfully";
		} catch (Exception ex) {
			return "Failed to schedule Email with Attachment!..";
		}
	}

	@Scheduled(fixedRate = 60000) // Runs every minute to check for emails to send
	@Transactional
	public void sendScheduledEmailswithAttachment() throws MessagingException {
		List<EmailScheduleWithAttachment> emailWithAttachment = emailWithAttachmentRepository
				.findByScheduleTimeBetween(LocalDateTime.now().minusSeconds(30), LocalDateTime.now().plusSeconds(30));
		for (EmailScheduleWithAttachment email : emailWithAttachment) {
			try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(email.getRecipient());
			helper.setSubject(email.getSubject());
			helper.setText(email.getBody());
			
			// Attach the file
			if (email.getAttachment() != null && email.getAttachment().length > 0) {
				String fileName = email.getFileName() != null ? email.getFileName() : "attachment";
				ByteArrayResource attachment = new ByteArrayResource(email.getAttachment());
				helper.addAttachment(fileName, attachment);
			} else {
				System.out.println("No attachment for email is fetched");
			}

			// Send the email
			javaMailSender.send(message);
			
			//Delete after the mail is send
			emailWithAttachmentRepository.deleteById(email.getId());
			}
			catch(Exception ex){
				System.err.print("The Email with attachment is not sent"+ex.getMessage());
			}
		}
	}

	@Override
	public void deleteMails() {
		emailWithAttachmentRepository.deleteAll();

	}

}
