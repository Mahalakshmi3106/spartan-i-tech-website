package com.spartanitech.website.repository;

import com.spartanitech.website.model.CareerApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerApplicationRepository extends JpaRepository<CareerApplication, Long> {
}