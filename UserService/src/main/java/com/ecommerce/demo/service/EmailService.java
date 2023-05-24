package com.ecommerce.demo.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {


    public String   sendMail(String toEmail , String from , String subject , String body){

        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","smtp.gmail.com");

        String username = "demodevnick5000@gmail.com";
        String password = "foucrmdfqfoqpqet";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try{
            Message message = new MimeMessage(session);

            message.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    return "Email Sent";
    }
}
