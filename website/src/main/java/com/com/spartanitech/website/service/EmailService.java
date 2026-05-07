package com.spartanitech.website.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);

            System.out.println("User mail sent to: " + to);

        } catch (Exception e) {
            System.out.println("User mail failed");
            e.printStackTrace();
        }
    }

    public void sendMailWithAttachment(String to, String subject, String text, String filePath) {
        try {
            File resumeFile = new File(filePath);

            System.out.println("Resume path: " + resumeFile.getAbsolutePath());
            System.out.println("Resume exists: " + resumeFile.exists());

            if (!resumeFile.exists()) {
                System.out.println("Resume file not found. HR mail not sent.");
                return;
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(resumeFile);
            helper.addAttachment(file.getFilename(), file);

            mailSender.send(message);

            System.out.println("HR mail sent to: " + to);

        } catch (Exception e) {
            System.out.println("HR mail failed");
            e.printStackTrace();
        }
    }
}