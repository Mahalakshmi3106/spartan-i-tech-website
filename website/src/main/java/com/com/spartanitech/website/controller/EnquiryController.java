package com.spartanitech.website.controller;

import com.spartanitech.website.model.Enquiry;
import com.spartanitech.website.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @PostMapping("/enquiry")
    public String saveEnquiry(@RequestBody Enquiry enquiry) {
        enquiryService.save(enquiry);
        return "Enquiry submitted successfully";
    }
}