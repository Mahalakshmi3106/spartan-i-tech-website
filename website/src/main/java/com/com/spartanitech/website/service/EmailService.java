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

    // =========================
    // NORMAL MAIL
    // =========================
    public void sendMail(String to, String subject, String text) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("spartanitech.hrd@gmail.com", "Spartan I-Tech Team");

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(text);

            mailSender.send(message);

            System.out.println("Mail sent successfully to: " + to);

        } catch (Exception e) {

            System.out.println("Mail sending failed");

            e.printStackTrace();
        }
    }

    // =========================
    // MAIL WITH ATTACHMENT
    // =========================
    public void sendMailWithAttachment(String to,
                                       String subject,
                                       String text,
                                       String filePath) {

        try {

            File resumeFile = new File(filePath);

            System.out.println("Resume Path: " +
                    resumeFile.getAbsolutePath());

            if (!resumeFile.exists()) {

                System.out.println("Resume file not found");

                return;
            }

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("spartanitech.hrd@gmail.com", "Spartan I-Tech HR");

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(text);

            FileSystemResource file =
                    new FileSystemResource(resumeFile);

            helper.addAttachment(
                    file.getFilename(),
                    file
            );

            mailSender.send(message);

            System.out.println("HR mail with attachment sent");

        } catch (Exception e) {

            System.out.println("HR mail failed");

            e.printStackTrace();
        }
    }
}