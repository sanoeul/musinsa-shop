package com.musinsa.report.parksanhee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private String brandName;
    private String categoryName;
    private BigDecimal price;
}
