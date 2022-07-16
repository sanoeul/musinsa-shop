package com.musinsa.report.parksanhee.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MinAndMaxBrandItemDto {
    private BrandMinimumPriceDto minimumBrand;
    private BrandMinimumPriceDto maximumBrand;
}
