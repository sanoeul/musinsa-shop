package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
