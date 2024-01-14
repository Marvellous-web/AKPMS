package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.dto.UserResponse;
import com.idsargus.akpmsarservice.dto.request.PasswordChangeRequest;
import com.idsargus.akpmsarservice.model.ProcessManualEmailSubscription;
import com.idsargus.akpmsarservice.model.domain.EmailTemplate;
import com.idsargus.akpmsarservice.model.domain.UserEmailTemplateEntity;
import com.idsargus.akpmsarservice.repository.EmailTemplateRestRepository;
import com.idsargus.akpmsarservice.repository.UserDataRestRepository;
import com.idsargus.akpmsarservice.repository.UserEmailTemplateRepository;
import com.idsargus.akpmsarservice.service.EmailServiceImpl;
import com.idsargus.akpmsarservice.util.Constants;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
@RequestMapping("v1/arapi/password")
public class SendEmailController {
    @Autowired
    private UserDataRestRepository userDataRestRepository;

    @Autowired
    private UserEmailTemplateRepository userEmailTemplateRepository;

    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    private EmailTemplateRestRepository emailTemplateRestRepository;
//    private static String USER_NAME = "nayakarindam2023@gmail.com";  // GMail user name (just the part before "@gmail.com")
//    private static String PASSWORD = "dtlxsrximwvscnvu"; // GMail password

    @Value("${spring.mail.username}")
    private String USER_NAME;
    @Value("${spring.mail.password}")
    private String PASSWORD;
    @PatchMapping(path = "/change")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws MessagingException {
        UserResponse userResponse = new UserResponse();
        if (passwordChangeRequest.getCurrentPassword() != null
                && passwordChangeRequest.getNewPassword() != null
                && passwordChangeRequest.getRetypePassword() != null) {
            if (passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getRetypePassword())) {
                UserEntity record = getUserByEmail(passwordChangeRequest.getEmail());

                if (record != null) {
                    String dbPassword = record.getPassword();
                    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                    boolean isPasswordMatches = encoder.matches(passwordChangeRequest.getCurrentPassword(), dbPassword);
                    if (isPasswordMatches) {
                        record.setPassword(encoder.encode(passwordChangeRequest.getNewPassword()));
                        Integer userEntity = userDataRestRepository.updatePassword(record.getPassword(), record.getId());
                        boolean isSent = false;
                        try {
                            isSent = sendEmailToUser(passwordChangeRequest, record);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (KeyManagementException e) {
                            throw new RuntimeException(e);
                        }
                        userResponse.setMsg("Password has been changed.");
                        if (userEntity > 0 && isSent) {
                            userResponse.setStatus(true);
                        } else {
                            userResponse.setStatus(false);
                        }
                        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
                    }
                } else {

                    userResponse.setMsg("No record found with this user.");
                    userResponse.setStatus(false);
                    return ResponseEntity.status(HttpStatus.OK).body(userResponse);


                }
            } else {
                userResponse.setMsg("New password and retypePassword are not matching.");
                userResponse.setStatus(false);
                return ResponseEntity.status(HttpStatus.OK).body(userResponse);
            }

        }
        userResponse.setMsg("Something went wrong");
        userResponse.setStatus(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);


    }

    @GetMapping("/newUser/sendEmail/{email}")
    public ResponseEntity<UserResponse> sendEmailToNewUser(@PathVariable String email) throws MessagingException, NoSuchAlgorithmException, IOException, KeyManagementException {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {email}; // list of recipient email addresses
        String subject = "ArgusCore Registration Email";

        UserEntity record = userDataRestRepository.findByNameForAdmin(email);
        char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%")).toCharArray();
        String pwd = RandomStringUtils.random(15, possibleCharacters);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        record.setPassword(encoder.encode(pwd));

        userDataRestRepository.updatePassword(record.getPassword(), record.getId());
        EmailTemplate emailTemplate = emailTemplateRestRepository
                .findById(Constants.ARGUS_REGISTRATION_MAIL_TEMPLATE).get();
        String body = emailTemplate.getContent().replace("USER_FIRST_NAME", record.getFirstName())
                .replace("USER_PASSWORD", pwd).replace("USER_EMAIL", email)
                .replace("SITE_URL", Constants.SITE_URL);

        boolean isSent = emailService.sendFromServer(email, subject, body);
        UserResponse userResponse = new UserResponse();
        if (isSent) {
            userResponse.setMsg("EMail has been sent to your account!! ");
            userResponse.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } else {
            userResponse.setMsg("EMail not found in our database ");
            userResponse.setStatus(false);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }


    }


    @GetMapping("/processManual/subscription/{email}")
    public ResponseEntity<UserResponse> processManualSubscription(@PathVariable String email) throws MessagingException, NoSuchAlgorithmException, IOException, KeyManagementException {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {email}; // list of recipient email addresses

        UserEntity record = userDataRestRepository.findByNameForAdmin(email);
//        char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%")).toCharArray();
//        String pwd = RandomStringUtils.random(15, possibleCharacters);
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        record.setPassword(encoder.encode(pwd));

//        userDataRestRepository.updatePassword(record.getPassword(), record.getId());
//        EmailTemplate emailTemplate = emailTemplateRestRepository
//                .findById(Constants.PROCESS_MANUAL_MODIFICATION_MAIL_TEMPLATE).get();
        userDataRestRepository.updatePassword(record.getPassword(), record.getId());
        EmailTemplate emailTemplate = emailTemplateRestRepository
                .findById(Constants.PROCESS_MANUAL_EMAIL_SUBSCRIPTION_MAIL_TEMPLATE).get();


        String body = emailTemplate.getContent();


        boolean isSent = emailService.sendFromServer(email, emailTemplate.getName(), body);
        UserResponse userResponse = new UserResponse();
        if (isSent) {
            userResponse.setMsg("EMail has been sent to your account!! ");
            userResponse.setStatus(true);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } else {
            userResponse.setMsg("EMail not found in our database ");
            userResponse.setStatus(false);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }


    }

    @PostMapping("/processmanual/subscription/sendemail")
    public ResponseEntity<?> sendEmailOnProcessManual(@RequestBody ProcessManualEmailSubscription
                                                 processManualEmailSubscription) throws MessagingException, NoSuchAlgorithmException, IOException, KeyManagementException {
        boolean isSent = false;
        if(processManualEmailSubscription != null) {
            String email = processManualEmailSubscription.getEmail();
            String modiSummary = processManualEmailSubscription.getModificationSummary();
            String title = processManualEmailSubscription.getTitle();
            String date  = processManualEmailSubscription.getModifiedOn();
            String modifiedBy = processManualEmailSubscription.getModifiedBy();
            String action = processManualEmailSubscription.getAction();
            if(email != null && modiSummary != null && action !=null
                    && title != null && date != null && modifiedBy != null) {
                String from = USER_NAME;
                String pass = PASSWORD;
                String[] to = {email}; // list of recipient email addresses

                UserEntity record = userDataRestRepository.findByNameForAdmin(email);

                if (record != null) {
                    UserEmailTemplateEntity userEmailTemplateInfo =
                            userEmailTemplateRepository.findSubscriptionByUserId(record.getId());

                        if(userEmailTemplateInfo != null) {
                            EmailTemplate emailTemplate = emailTemplateRestRepository
                                    .findById(Constants.PROCESS_MANUAL_MODIFICATION_SUBSCRIPTION_MAIL_TEMPLATE).get();
                            String body = emailTemplate.getContent().replace("TITLE", processManualEmailSubscription.getTitle())
                                    .replace("MODIFICATION_SUMMARY", modiSummary)
                                    .replace("TITLE", title)
                                    .replace("MODIFIEDBY", modifiedBy)
                                    .replace("ADDED_MODIFIED", action)
                                    .replace("MODIFIEDON", date);

                            isSent = emailService.sendFromServer(email, emailTemplate.getName(), body);
                            UserResponse userResponse = new UserResponse();
                            if (isSent) {
                                userResponse.setMsg("EMail has been sent to your account!! ");
                                userResponse.setStatus(true);
                                return ResponseEntity.status(HttpStatus.OK).body(userResponse);
                            } else {
                                userResponse.setMsg("EMail not found in our database ");
                                userResponse.setStatus(false);
                                return ResponseEntity.status(HttpStatus.OK).body(userResponse);

                            }
                        }else{
                            UserResponse userResponse = new UserResponse();
                            userResponse.setMsg("User has not subscribed to ProcessManual Notification ");
                            userResponse.setStatus(true);
                            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
                        }

                }
            } else{
                    UserResponse userResponse = new UserResponse();
                    userResponse.setMsg("Please add all fields");
                    userResponse.setStatus(true);
                    return ResponseEntity.status(HttpStatus.OK).body(userResponse);

            }
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setMsg("Something went wrong!!");
        userResponse.setStatus(true);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);


    }

    private UserEntity getUserByEmail(String email) {
        return userDataRestRepository.findByNameForAdmin(email);
    }


    public boolean sendEmailToUser(PasswordChangeRequest passwordChangeRequest, UserEntity userEntity) throws MessagingException, NoSuchAlgorithmException, IOException, KeyManagementException {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {passwordChangeRequest.getEmail()}; // list of recipient email addresses
        String subject = "Your Password has been changed.";

        EmailTemplate emailTemplateChangePassword = emailTemplateRestRepository
                .findById(Constants.CHANGE_PASSWORD_TEMPLATE).get();
        String body = emailTemplateChangePassword.getContent().replace("USER_FIRST_NAME", userEntity.getFirstName())
                .replace("NEW_PASSWORD", passwordChangeRequest.getNewPassword());
        return emailService.sendFromServer(passwordChangeRequest.getEmail(), subject, body);

    }
}
