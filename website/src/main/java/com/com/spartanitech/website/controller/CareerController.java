package com.spartanitech.website.controller;

import com.spartanitech.website.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @PostMapping("/career")
    public String saveCareer(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String role,
            @RequestParam MultipartFile resume
    ) {
        try {
            careerService.save(fullName, email, phone, role, resume);
            return "Application submitted successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}