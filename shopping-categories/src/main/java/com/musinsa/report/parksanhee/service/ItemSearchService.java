package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.dto.*;
import com.musinsa.report.parksanhee.exception.NotFoundCategoryException;
import com.musinsa.report.parksanhee.exception.NotFoundItemException;
import com.musinsa.report.parksanhee.repository.ItemSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemSearchService {
    private final ItemSearchRepositoryImpl itemSearchRepository;

    public BrandMinimumPriceDto getTotalMinimumPricesOfBrandItems(String brandName) {
        List<BigDecimal> prices = itemSearchRepository.getMinimumPricesOfBrandItems(brandName);

        if (prices.size() == 0) {
            throw new NotFoundItemException("조회하신 " + brandName + " 브랜드에 존재하는 상품을 찾을 수 없습니다.");
        }

        BigDecimal totalPrice = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return new BrandMinimumPriceDto(brandName, totalPrice);
    }

    public MinAndMaxBrandItemDto getMinimumAndMaximumPriceByCategory(String categoryName) {
        List<BrandMinimumPriceDto> prices = itemSearchRepository.getMinimumPricesOfCategoryItems(categoryName);

        if (prices.size() == 0) {
            throw new NotFoundCategoryException("조회하신 " + categoryName + " 카테고리에 존재하는 상품을 찾을 수 없습니다.");
        }

        return MinAndMaxBrandItemDto.builder()
                .minimumBrand(prices.get(0))
                .maximumBrand(prices.size() > 1 ? prices.get(prices.size() - 1) : null)
                .build();
    }

    public CategoryBrandItemsDto getAllMinimumPricesByCategoryAndBrand(List<CategoryBrandDto> categoryBrandsDto) {
        List<CategoryBrandItemDto> result = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CategoryBrandDto categoryBrand : categoryBrandsDto) {
            CategoryBrandItemDto categoryBrandItem = itemSearchRepository.getAllMinimumPriceByCategoryAndBrand(categoryBrand.getCategoryName(), categoryBrand.getBrandName());
            if (categoryBrandItem != null) {
                result.add(categoryBrandItem);
                totalPrice = totalPrice.add(categoryBrandItem.getMinimumPrice());
            }
        }

        if (result.size() == 0) {
            throw new NotFoundItemException("요청한 조건의 모든 상품이 존재하지 않습니다.");
        }
        return new CategoryBrandItemsDto(result, totalPrice);
    }
}
