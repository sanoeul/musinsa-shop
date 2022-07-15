package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.Money;
import com.musinsa.report.parksanhee.repository.ItemSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemSearchRepositoryImpl itemSearchRepository;

    public Money getMinimumPricesOfBrandItems(String brandName) {
        return itemSearchRepository.getLowestPricesOfBrandItems(brandName);
    }

}
