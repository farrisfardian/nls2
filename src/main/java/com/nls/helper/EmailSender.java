/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.helper;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author farris
 */
public class EmailSender {
    
    private String email;
    private String password;
    private String smtpHost;
    private String smtpAuth;
    private String smtpPort;
    
    public EmailSender() {
    }
    
    public EmailSender(String email, String password, String smtpHost, String smtpAuth, String smtpPort) {
        this.email = email;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpAuth = smtpAuth;
        this.smtpPort = smtpPort;
    }
    
    public void kirimEmail(String to, String subjek, String konten, HashMap<String, DataSource> attachments) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", smtpHost);
//        properties.setProperty("mail.smtp.socketFactory.port", setting.getMailSmtpSocketFactoryPort());
//        properties.setProperty("mail.smtp.socketFactory.class",
//                setting.getMailSmtpSocketFactoryClass());
        properties.setProperty("mail.smtp.auth", smtpAuth);
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(lastByTgl.getEmailAplikasi()));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject(subjek);
//
//            // Send the actual HTML message, as big as you like
//            message.setContent(konten, "text/html");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(email));
            helper.setTo(to);
            helper.setSubject(subjek);
            for (String key : attachments.keySet()) {
                helper.addAttachment(key, attachments.get(key));
            }
            helper.setText(konten, true);

            // Send message
//            Transport.send(message);
            Transport.send(helper.getMimeMessage());
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the smtpHost
     */
    public String getSmtpHost() {
        return smtpHost;
    }

    /**
     * @param smtpHost the smtpHost to set
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     * @return the smtpAuth
     */
    public String getSmtpAuth() {
        return smtpAuth;
    }

    /**
     * @param smtpAuth the smtpAuth to set
     */
    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    /**
     * @return the smtpPort
     */
    public String getSmtpPort() {
        return smtpPort;
    }

    /**
     * @param smtpPort the smtpPort to set
     */
    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }
}
