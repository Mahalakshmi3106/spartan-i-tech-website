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

        String userMessage =
                "Dear " + enquiry.getName() + ",\n\n" +

                        "Greetings from Spartan I-Tech.\n\n" +

                        "Thank you for reaching out to Spartan I-Tech. " +
                        "We have successfully received your enquiry.\n\n" +

                        "Our team will review your requirements and contact you shortly. " +
                        "We appreciate your interest in our services.\n\n" +

                        "Regards,\n" +
                        "Spartan I-Tech Team\n\n" +

                        "This is an auto-generated email. Please do not reply to this message.";

        emailService.sendMail(
                enquiry.getEmail(),
                "Enquiry Received - Spartan I-Tech",
                userMessage
        );

        String hrMessage =
                "New Enquiry Received\n\n" +

                        "Name    : " + enquiry.getName() + "\n" +
                        "Email   : " + enquiry.getEmail() + "\n" +
                        "Phone   : " + enquiry.getPhone() + "\n" +
                        "Message : " + enquiry.getMessage() + "\n\n" +

                        "Please follow up with the customer.";

        emailService.sendMail(
                "spartanitech.hrd@gmail.com",
                "New Enquiry - Spartan I-Tech",
                hrMessage
        );
    }
}