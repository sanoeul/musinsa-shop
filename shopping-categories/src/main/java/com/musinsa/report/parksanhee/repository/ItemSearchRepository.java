package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.dto.BrandMinimumPriceDto;
import com.musinsa.report.parksanhee.dto.CategoryBrandItemDto;

import java.math.BigDecimal;
import java.util.List;

public interface ItemSearchRepository {
    List<BigDecimal> getMinimumPricesOfBrandItems(String brandName);

    List<BrandMinimumPriceDto> getMinimumPricesOfCategoryItems(String categoryName);

    CategoryBrandItemDto getAllMinimumPriceByCategoryAndBrand(String categoryName, String brandName);
}
