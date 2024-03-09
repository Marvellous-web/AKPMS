package com.idsargus.akpmsarservice.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig
{
    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSender mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(25);
//
//        mailSender.setUsername("admin@gmail.com");
//        mailSender.setPassword("password");
//
//        Properties props = mailSender..getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage emailTemplate()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("nayakarindam2023@gmail.com");
        message.setFrom("nayakarindam2023@gmail.com");
        message.setText("Test email");
        return message;
    }
}