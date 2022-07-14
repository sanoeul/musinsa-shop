package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
