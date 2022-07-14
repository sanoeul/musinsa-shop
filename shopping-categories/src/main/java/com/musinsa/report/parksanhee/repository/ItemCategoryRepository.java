package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.domain.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
}
