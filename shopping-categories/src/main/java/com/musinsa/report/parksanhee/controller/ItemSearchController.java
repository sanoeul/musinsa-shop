package com.musinsa.report.parksanhee.controller;

import com.musinsa.report.parksanhee.dto.BrandMinimumPriceDto;
import com.musinsa.report.parksanhee.dto.CategoryBrandDto;
import com.musinsa.report.parksanhee.dto.CategoryBrandItemsDto;
import com.musinsa.report.parksanhee.dto.MinAndMaxBrandItemDto;
import com.musinsa.report.parksanhee.service.ItemSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "items")
public class ItemSearchController {
    private final ItemSearchService itemSearchService;

    @GetMapping("/brandFilter")
    public BrandMinimumPriceDto getTotalMinimumPricesOfBrandItems(@RequestParam("name") String brandName) {
        return itemSearchService.getTotalMinimumPricesOfBrandItems(brandName);
    }

    @GetMapping("/categoryFilter")
    public MinAndMaxBrandItemDto getMinimumAndMaximumPriceByCategory(@RequestParam("name") String categoryName) {
        return itemSearchService.getMinimumAndMaximumPriceByCategory(categoryName);
    }

    @GetMapping("/categoryBrandFilters")
    public CategoryBrandItemsDto getAllMinimumPricesByCategoryAndBrand(@RequestParam("categories") String categories, @RequestParam("brands") String brands) {
        List<CategoryBrandDto> categoryBrandsDto = convertStrToCategoryBrandDtos(categories, brands);
        return itemSearchService.getAllMinimumPricesByCategoryAndBrand(categoryBrandsDto);
    }

    private List<CategoryBrandDto> convertStrToCategoryBrandDtos(String categories, String brands) {
        final String SEPARATOR = ",";
        final List<CategoryBrandDto> categoryBrandsDto = new ArrayList<>();
        final String[] splitCategories = categories.split(SEPARATOR);
        final String[] splitBrands = brands.split(SEPARATOR);

        if (splitCategories.length != splitBrands.length) {
            throw new RuntimeException("선택한 카테고리 개수(" + splitCategories.length + ")와 브랜드 개수(" + splitBrands.length + ")가 일치하지 않습니다.");
        }
        for (int i = 0; i < splitCategories.length; i++) {

            CategoryBrandDto categoryBrandDto = CategoryBrandDto.builder()
                    .categoryName(splitCategories[i])
                    .brandName(splitBrands[i])
                    .build();
            categoryBrandsDto.add(categoryBrandDto);
        }
        return categoryBrandsDto;
    }
}
