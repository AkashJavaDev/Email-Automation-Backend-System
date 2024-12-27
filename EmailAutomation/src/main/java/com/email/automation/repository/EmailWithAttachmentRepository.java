package com.email.automation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.email.automation.model.EmailScheduleWithAttachment;

@Repository
public interface EmailWithAttachmentRepository extends JpaRepository<EmailScheduleWithAttachment, Long> {
	
	List<EmailScheduleWithAttachment> findByScheduleTimeBetween(LocalDateTime start, LocalDateTime end);
	
	void deleteById(long id);

}
