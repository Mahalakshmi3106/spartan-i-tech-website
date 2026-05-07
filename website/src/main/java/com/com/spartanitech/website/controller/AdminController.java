package com.spartanitech.website.controller;

import com.spartanitech.website.model.CareerApplication;
import com.spartanitech.website.model.Enquiry;
import com.spartanitech.website.repository.CareerApplicationRepository;
import com.spartanitech.website.repository.EnquiryRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final CareerApplicationRepository careerRepository;
    private final EnquiryRepository enquiryRepository;

    public AdminController(CareerApplicationRepository careerRepository,
                           EnquiryRepository enquiryRepository) {
        this.careerRepository = careerRepository;
        this.enquiryRepository = enquiryRepository;
    }

    @GetMapping("/applications")
    public List<CareerApplication> getAllApplications() {
        return careerRepository.findAll();
    }

    @GetMapping("/enquiries")
    public List<Enquiry> getAllEnquiries() {
        return enquiryRepository.findAll();
    }

    @PutMapping("/application/status/{id}")
    public String updateApplicationStatus(@PathVariable Long id, @RequestParam String status) {
        CareerApplication app = careerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);
        careerRepository.save(app);

        return "Application status updated";
    }

    @PutMapping("/enquiry/status/{id}")
    public String updateEnquiryStatus(@PathVariable Long id, @RequestParam String status) {
        Enquiry enquiry = enquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enquiry not found"));

        enquiry.setStatus(status);
        enquiryRepository.save(enquiry);

        return "Enquiry status updated";
    }

    @DeleteMapping("/application/{id}")
    public String deleteApplication(@PathVariable Long id) {
        careerRepository.deleteById(id);
        return "Application deleted";
    }

    @DeleteMapping("/enquiry/{id}")
    public String deleteEnquiry(@PathVariable Long id) {
        enquiryRepository.deleteById(id);
        return "Enquiry deleted";
    }

    @GetMapping("/resume/{fileName}")
    public Resource downloadResume(@PathVariable String fileName) throws Exception {
        Path path = Paths.get("uploads").resolve(fileName).normalize();
        return new UrlResource(path.toUri());
    }
}