package com.email.automation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.automation.model.ToEmails;

public interface ToEmailsRespository extends JpaRepository<ToEmails, Long> {

}
