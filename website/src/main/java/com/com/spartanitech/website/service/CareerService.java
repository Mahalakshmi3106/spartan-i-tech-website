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

    public void save(String fullName, String email, String phone, String role, MultipartFile resume) throws Exception {

        // ✅ validation
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

        // 🔥 IMPORTANT FIX (ABSOLUTE PATH)
        String uploadDir = "C:/uploads/";   // 👈 change if needed

        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + resume.getOriginalFilename();
        String fullPath = uploadDir + fileName;

        File file = new File(fullPath);
        resume.transferTo(file);

        // DB save
        CareerApplication c = new CareerApplication();
        c.setFullName(fullName);
        c.setEmail(email);
        c.setPhone(phone);
        c.setRole(role);
        c.setResumeFileName(fileName);

        careerRepository.save(c);

        // User mail
        emailService.sendMail(
                email,
                "Spartan I-Tech - Application Received",
                "Hi " + fullName + ",\n\nYour resume received successfully.\n\nRegards,\nSpartan I-Tech"
        );

        // HR mail + attachment
        emailService.sendMailWithAttachment(
                "spartanitech@gmail.com",
                "New Job Application",
                "Name: " + fullName + "\nEmail: " + email + "\nPhone: " + phone + "\nRole: " + role,
                fullPath
        );
    }
}