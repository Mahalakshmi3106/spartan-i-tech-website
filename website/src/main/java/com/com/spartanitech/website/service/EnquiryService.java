package com.spartanitech.website.service;

import com.spartanitech.website.model.Enquiry;
import com.spartanitech.website.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private EmailService emailService;

    public void save(Enquiry enquiry) {

        if (!enquiry.getPhone().matches("[6-9][0-9]{9}")) {
            throw new RuntimeException("Invalid phone number");
        }

        if (!enquiry.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Invalid email address");
        }

        enquiryRepository.save(enquiry);

        // USER CONFIRMATION MAIL
        emailService.sendMail(
                enquiry.getEmail(),
                "Spartan I-Tech - Enquiry Received",
                "Hi " + enquiry.getName() + ",\n\n" +
                        "Thanks for contacting Spartan I-Tech.\n" +
                        "We will contact you soon.\n\n" +
                        "Regards,\nSpartan I-Tech"
        );

        // HR MAIL
        emailService.sendMail(
                "spartanitech.hrd@gmail.com",
                "New Enquiry - Spartan I-Tech",
                "New enquiry received:\n\n" +
                        "Name: " + enquiry.getName() + "\n" +
                        "Email: " + enquiry.getEmail() + "\n" +
                        "Phone: " + enquiry.getPhone() + "\n" +
                        "Message: " + enquiry.getMessage()
        );
    }
}