package com.musinsa.report.parksanhee.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBrandItemDto {
    private String categoryName;
    private String brandName;
    private BigDecimal minimumPrice;
}
