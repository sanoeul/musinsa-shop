package com.musinsa.report.parksanhee.dto;

import com.musinsa.report.parksanhee.domain.Money;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CategoryBrandItemDto {
    private String categoryName;
    private String brandName;
    private Money price;

    public CategoryBrandItemDto(String categoryName, String brandName, BigDecimal price) {
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.price = Money.wons(price);
    }
}
