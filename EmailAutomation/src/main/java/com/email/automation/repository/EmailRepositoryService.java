package com.email.automation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.email.automation.model.EmailSchedule;
import com.email.automation.model.ToEmails;

@Repository
public interface EmailRepositoryService extends JpaRepository<EmailSchedule, Long> {

	List<EmailSchedule> findByScheduledTimeBetween(LocalDateTime start, LocalDateTime end);

	void deleteById(Long id);

	void save(ToEmails saveEmails);

}
