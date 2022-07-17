package com.musinsa.report.parksanhee.controller;

import com.musinsa.report.parksanhee.dto.BrandMinimumPriceDto;
import com.musinsa.report.parksanhee.dto.CategoryBrandDto;
import com.musinsa.report.parksanhee.dto.CategoryBrandItemsDto;
import com.musinsa.report.parksanhee.dto.MinAndMaxBrandItemDto;
import com.musinsa.report.parksanhee.service.ItemSearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "items")
public class ItemSearchController {
    private final ItemSearchService itemSearchService;

    @ApiOperation(value = "한 브랜드에서 모든 상품을 구매할 경우 최저가 조회", notes = "ex) items/brandFilter?name=B")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "요청한 브랜드에 존재하는 상품을 찾을 수 없을 경우 실패")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/brandFilter")
    public BrandMinimumPriceDto getTotalMinimumPricesOfBrandItems(@RequestParam("name") String brandName) {
        return itemSearchService.getTotalMinimumPricesOfBrandItems(brandName);
    }

    @ApiOperation(value = "각 카테고리 이름으로 최소, 최대 가격 조회", notes = "ex) items/categoryFilter?name=상의")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "요청한 카테고리에 존재하는 상품을 찾을 수 없을 경우 실패")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categoryFilter")
    public MinAndMaxBrandItemDto getMinimumAndMaximumPriceByCategory(@RequestParam("name") String categoryName) {
        return itemSearchService.getMinimumAndMaximumPriceByCategory(categoryName);
    }

    @ApiOperation(value = "모든 카테고리의 상품을 브랜드 별로 선택 시 최저가 조회", notes = "ex) items/categoryBrandFilters?categories=상의,아우터&brands=A,B")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "요청한 조건에 존재하는 상품을 찾을 수 없을 경우 실패")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categoryBrandFilters")
    public CategoryBrandItemsDto getAllMinimumPricesByCategoryAndBrand(@RequestParam("categories") String categories, @RequestParam("brands") String brands) {
        List<CategoryBrandDto> categoryBrandsDto = convertStrToCategoryBrandsDto(categories, brands);
        return itemSearchService.getAllMinimumPricesByCategoryAndBrand(categoryBrandsDto);
    }

    private List<CategoryBrandDto> convertStrToCategoryBrandsDto(String categories, String brands) {
        final String SEPARATOR = ",";
        final List<CategoryBrandDto> categoryBrandsDto = new ArrayList<>();
        final String[] splitCategories = categories.split(SEPARATOR);
        final String[] splitBrands = brands.split(SEPARATOR);

        if (splitCategories.length != splitBrands.length) {
            throw new IllegalArgumentException("선택한 카테고리 개수(" + splitCategories.length + ")와 브랜드 개수(" + splitBrands.length + ")가 일치하지 않습니다.");
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
