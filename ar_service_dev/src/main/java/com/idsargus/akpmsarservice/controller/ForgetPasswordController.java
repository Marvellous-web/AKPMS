package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.dto.UserResponse;
import com.idsargus.akpmsarservice.model.domain.EmailTemplate;
import com.idsargus.akpmsarservice.repository.EmailTemplateRestRepository;
import com.idsargus.akpmsarservice.repository.UserDataRestRepository;
import com.idsargus.akpmsarservice.service.EmailServiceImpl;
import com.idsargus.akpmsarservice.util.Constants;
import com.idsargus.akpmsarservice.util.EmailUtil;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forget/password")
public class ForgetPasswordController {
    @Autowired
    private UserDataRestRepository userDataRestRepository;

    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    private EmailTemplateRestRepository emailTemplateRestRepository;

//    private static String USER_NAME = "nayakarindam2023@gmail.com";  // GMail user name (just the part before "@gmail.com")
//    private static String PASSWORD = "dtlxsrximwvscnvu"; // GMail password

    @Value("${spring.mail.username}")
    private String USER_NAME;
    @Value("${spring.mail.password}")
    private String PASSWORD;
    @GetMapping("/sendEmail/{email}")
    public ResponseEntity<?> sendEmail(@PathVariable String email) throws Exception {

        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {email}; // list of recipient email addresses
        String subject = "Your Password has been changed.";

        UserEntity record = userDataRestRepository.findByNameForAdmin(email);
        char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%")).toCharArray();
        String pwd = RandomStringUtils.random(15, possibleCharacters);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        record.setPassword(encoder.encode(pwd));
       // userDataRestRepository.save(record);
        userDataRestRepository.updatePassword(record.getPassword(), record.getId());
        EmailTemplate emailTemplate = emailTemplateRestRepository
                .findById(Constants.FORGET_PASSWOR_TEMPLATE).get();
          String body = emailTemplate.getContent().replace("USER_FIRST_NAME",record.getFirstName())
                .replace("USER_PASSWORD", pwd );

      //  boolean isSent = emailService.sendFromGMail(from, pass, to, subject,body);
        boolean isSent = emailService.sendFromServer(email,subject,body);
        UserResponse userResponse = new UserResponse();
        if(isSent) {
            userResponse.setMsg("EMail has been sent to your account!! ");
            userResponse.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }else{
            userResponse.setMsg("EMail not found in our database ");
            userResponse.setStatus(false);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }
    }


@GetMapping("/UserCreatedSendEmail/{email}")
    public ResponseEntity<?> UserCreatedSendEmail(@PathVariable String email) throws Exception {

//        String from = USER_NAME;
//        String pass = PASSWORD;
        String[] to = {email}; // list of recipient email addresses
        String subject = "Welcome aboard.";

        UserEntity record = userDataRestRepository.findByNameForAdmin(email);
        char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%")).toCharArray();
        String pwd = RandomStringUtils.random(15, possibleCharacters);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        record.setPassword(encoder.encode(pwd));
       // userDataRestRepository.save(record);
        userDataRestRepository.updatePassword(record.getPassword(), record.getId());
        EmailTemplate emailTemplate = emailTemplateRestRepository
                .findById(Constants.ARGUS_REGISTRATION_MAIL_TEMPLATE).get();
          String body = emailTemplate.getContent().replace("USER_FIRST_NAME",record.getFirstName())
                .replace("USER_EMAIL", record.getEmail() )
                  .replace("USER_PASSWORD", pwd )
                  .replace("SITE_URL", Constants.SITE_URL);

        boolean isSent = emailService.sendFromServer(email, subject,body);
        UserResponse userResponse = new UserResponse();
        if(isSent) {
            userResponse.setMsg("EMail has been sent to your account!! ");
            userResponse.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }else{
            userResponse.setMsg("EMail not found in our database ");
            userResponse.setStatus(false);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }
    }

}
