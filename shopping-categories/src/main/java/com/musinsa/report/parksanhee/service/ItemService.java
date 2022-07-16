package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.Money;
import com.musinsa.report.parksanhee.dto.BrandMinimumPriceDto;
import com.musinsa.report.parksanhee.dto.MinAndMaxBrandItemDto;
import com.musinsa.report.parksanhee.exception.NotFoundCategoryException;
import com.musinsa.report.parksanhee.exception.NotFoundItemException;
import com.musinsa.report.parksanhee.repository.ItemSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemSearchRepositoryImpl itemSearchRepository;

    public Money getTotalMinimumPricesOfBrandItems(String brandName) {
        List<BigDecimal> prices = itemSearchRepository.getMinimumPricesOfBrandItems(brandName);

        if (prices.size() == 0) {
            throw new NotFoundItemException("해당 브랜드에 아이템이 존재하지 않습니다.");
        }

        BigDecimal totalPrice = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return Money.wons(totalPrice);
    }

    public MinAndMaxBrandItemDto getMinimumAndMaximumPriceByCategory(String categoryName) {
        List<BrandMinimumPriceDto> prices = itemSearchRepository.getMinimumPricesOfCategoryItems(categoryName);

        if (prices.size() == 0) {
            throw new NotFoundCategoryException("해당 카테고리에 존재하는 아이템을 찾을 수 없습니다.");
        }

        return MinAndMaxBrandItemDto.builder()
                .minimumBrand(prices.get(0))
                .maximumBrand(prices.size() > 1 ? prices.get(prices.size() - 1) : null)
                .build();
    }
}
