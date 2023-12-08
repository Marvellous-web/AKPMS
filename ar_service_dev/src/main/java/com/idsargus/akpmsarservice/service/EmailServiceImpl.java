package com.idsargus.akpmsarservice.service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.idsargus.akpmsarservice.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    public  boolean sendFromGMail(String from, String pass, String[] to, String subject, String body) throws MessagingException {
        Properties props = System.getProperties();
    //    String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);

        // added debug to check exact issue for TLS
        System.setProperty("javax.net.debug", "all");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
       // props.put("mail.smtp.port", "587");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);

       MimeMessage message =  javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);


        //MimeMessage message = new MimeMessage(session);
        helper.setSubject(subject);
        String content = body.replace("\n", "<br>");
        helper.setText(content, true);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }


            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());

            transport.close();
            return true;
        } catch (AddressException ae) {
            ae.printStackTrace();
            return false;
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }

    public  boolean sendFromServer(String to, String subject, String body) throws MessagingException, NoSuchAlgorithmException, KeyManagementException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        props.put("mail.smtp.auth", "false");
        // Create a Session with the configured properties
        Session session = Session.getInstance(props);

        // Create a MimeMessage
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(username));
        mimeMessage.setSubject(subject);
        InternetAddress[] recipients = InternetAddress.parse(to);
        mimeMessage.setRecipients(Message.RecipientType.TO, recipients);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body,"text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        mimeMessage.setContent(multipart);


       // mimeMessage.setText(body);

        // Get the Transport instance and send the message
        Transport transport = session.getTransport("smtp");
        transport.connect();
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        return true;
    }
}
