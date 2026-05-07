package com.spartanitech.website.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMail(String to, String subject, String text) {

        try {
            System.out.println("MAIL METHOD CALLED");
            System.out.println("Trying to send mail to: " + to);
            System.out.println("From mail: " + fromMail);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(fromMail, "Spartan I-Tech Team");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);

            mailSender.send(message);

            System.out.println("Mail sent successfully to: " + to);

        } catch (Exception e) {
            System.out.println("Mail sending failed to: " + to);
            e.printStackTrace();
        }
    }

    public void sendMailWithAttachment(String to,
                                       String subject,
                                       String text,
                                       String filePath) {

        try {
            System.out.println("HR MAIL METHOD CALLED");
            System.out.println("Trying to send HR mail to: " + to);
            System.out.println("From mail: " + fromMail);

            File resumeFile = new File(filePath);

            System.out.println("Resume Path: " + resumeFile.getAbsolutePath());
            System.out.println("Resume Exists: " + resumeFile.exists());

            if (!resumeFile.exists()) {
                System.out.println("Resume file not found");
                return;
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromMail, "Spartan I-Tech HR");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);

            FileSystemResource file = new FileSystemResource(resumeFile);
            helper.addAttachment(file.getFilename(), file);

            mailSender.send(message);

            System.out.println("HR mail with attachment sent to: " + to);

        } catch (Exception e) {
            System.out.println("HR mail failed to: " + to);
            e.printStackTrace();
        }
    }
}