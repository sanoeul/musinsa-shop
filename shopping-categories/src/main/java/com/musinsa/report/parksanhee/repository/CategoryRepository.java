package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
