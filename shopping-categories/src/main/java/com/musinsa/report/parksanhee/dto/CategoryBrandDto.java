package com.musinsa.report.parksanhee.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryBrandDto {
    private String categoryName;
    private String brandName;
}
