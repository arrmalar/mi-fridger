package com.meefri.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class EmailService {

    private String verificationCode = "";

    public void sendMail(String reciper, String msg) throws IOException, MessagingException {

        final String username = "fridgemanagerinspector@gmail.com";
        final String password = "eouqdcmzmnyluone";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("fridgemanagerinspector@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(reciper)
            );

            message.setSubject("Verification Code");
            message.setText(msg);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String getVerificationCode(){
            return verificationCode;
    }

    public void createVerificationCode(){
        StringBuilder code = new StringBuilder();

        for(int x = 0 ; x < 8 ; x++){
            int r =  (int)(Math.random()*10000)%3;
            switch(r){

                case 0:
                    code.append((char) (int) (48 + Math.random() * 10));
                    break;
                case 1:
                    code.append((char) (int) (65 + Math.random() * 26));
                    break;
                case 2:
                    code.append((char) (int) (97 + Math.random() * 26));
                    break;
            }
        }

        this.verificationCode = code.toString();
    }
}
