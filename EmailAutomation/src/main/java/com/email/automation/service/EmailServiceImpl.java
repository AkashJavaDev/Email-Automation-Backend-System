package com.email.automation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.email.automation.model.EmailSchedule;
import com.email.automation.model.ToEmails;
import com.email.automation.repository.EmailRepositoryService;
import com.email.automation.repository.ToEmailsRespository;

import jakarta.mail.MessagingException;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EmailRepositoryService emailRepositoryService;
	
	@Autowired
	private ToEmailsRespository toEmailsRespository;

	@Override
	public String sendEmail(String recipient, String subject, String body) {

		try {
			SimpleMailMessage mailSend = new SimpleMailMessage();
			mailSend.setTo(recipient);
			mailSend.setSubject(subject);
			mailSend.setText(body);
			mailSend.setFrom("akashemailautomatio@gmail.com");
			javaMailSender.send(mailSend);

			return "Email Send Successfull!..";
		} catch (Exception ex) {
			return "Email is not sent.." + ex.getMessage();
		}

	}

	@Override
	public String scheduleEmail(String recipient, String subject, String body, LocalDateTime scheduledTime) {
		try {
			EmailSchedule emailSchedule = new EmailSchedule(recipient, subject, body, scheduledTime);
			emailRepositoryService.save(emailSchedule);
			ToEmails saveEmails = new ToEmails(recipient, subject, body, scheduledTime);
			toEmailsRespository.save(saveEmails);

			return "Email scheduled successfully!";
		} catch (Exception ex) {
			return "Email is not scheduled!.." + ex.getMessage();
		}
	}

	// Scheduled task to check for emails that need to be sent
	@Scheduled(fixedRate = 10000) // Runs every minute to check for emails to send
	public void sendScheduledEmails() throws MessagingException {
		List<EmailSchedule> emailToSend = emailRepositoryService
				.findByScheduledTimeBetween(LocalDateTime.now().minusSeconds(20), LocalDateTime.now().plusSeconds(20));
		for (EmailSchedule email : emailToSend) {
			sendEmail(email.getRecipient(), email.getSubject(), email.getBody());
			emailRepositoryService.deleteById(email.getId());
		}
	}

	
	//Set everything manually ( like inbuilt template )
	@Override
	@Scheduled(cron = "0 02 23 * 12 ?")
	public void sendScheduledEmail() {
		String to = "Type Your Email here";
		String subject = "Recharge Expired from jio..";
		String body = "Your Recharge plan has been expired,Please Recharge Immediately.";
		sendEmail(to, subject, body);
	}

}
