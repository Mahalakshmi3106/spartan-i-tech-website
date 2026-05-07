package com.spartanitech.website.service;

import com.spartanitech.website.model.CareerApplication;
import com.spartanitech.website.repository.CareerApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class CareerService {

    @Autowired
    private CareerApplicationRepository careerRepository;

    @Autowired
    private EmailService emailService;

    public void save(String fullName, String email, String phone,
                     String role, MultipartFile resume) throws Exception {

        // Validation
        if (!phone.matches("[6-9][0-9]{9}")) {
            throw new RuntimeException("Invalid phone number");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Invalid email");
        }

        if (resume == null || resume.isEmpty()) {
            throw new RuntimeException("Resume required");
        }

        if (!resume.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException("Only PDF allowed");
        }

        // Render Compatible Upload Folder
        String uploadDir = System.getProperty("java.io.tmpdir") + "/uploads/";

        File folder = new File(uploadDir);

        if (!folder.exists()) {
            folder.mkdirs();
        }


        String fileName = System.currentTimeMillis() + "_" +
                resume.getOriginalFilename();

        String fullPath = uploadDir + fileName;

        File file = new File(fullPath);

        resume.transferTo(file);

        // Save to DB
        CareerApplication c = new CareerApplication();

        c.setFullName(fullName);
        c.setEmail(email);
        c.setPhone(phone);
        c.setRole(role);
        c.setResumeFileName(fileName);

        careerRepository.save(c);

        // User Mail
        String userMessage =
                "Dear " + fullName + ",\n\n" +

                        "Greetings from Spartan I-Tech.\n\n" +

                        "Thank you for applying for the " + role + " position.\n" +
                        "We have successfully received your application and resume.\n\n" +

                        "Our recruitment team will review your profile carefully. " +
                        "If shortlisted, we will contact you regarding the next steps.\n\n" +

                        "Regards,\n" +
                        "Spartan I-Tech Team\n" +
                        "HR Department";

        emailService.sendMail(
                email,
                "Application Received - Spartan I-Tech",
                userMessage
        );

        // HR Mail
        String hrMessage =
                "New Career Application Received\n\n" +

                        "Name : " + fullName + "\n" +
                        "Email : " + email + "\n" +
                        "Phone : " + phone + "\n" +
                        "Role : " + role;

        emailService.sendMailWithAttachment(
                "spartanitech.hrd@gmail.com",
                "New Job Application - Spartan I-Tech",
                hrMessage,
                fullPath
        );
    }
}