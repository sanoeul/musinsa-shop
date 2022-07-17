package com.musinsa.report.parksanhee.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinAndMaxBrandItemDto {
    private BrandMinimumPriceDto minimumBrand;
    private BrandMinimumPriceDto maximumBrand;
}
