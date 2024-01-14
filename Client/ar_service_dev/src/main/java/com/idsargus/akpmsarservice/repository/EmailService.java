package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.EmailTemplate;

public interface EmailService{
    String sendSimpleMail(EmailTemplate details);

    String sendMailWithAttachment(EmailTemplate details);
}

