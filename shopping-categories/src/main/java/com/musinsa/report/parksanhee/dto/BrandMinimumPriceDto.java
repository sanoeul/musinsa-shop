package com.musinsa.report.parksanhee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BrandMinimumPriceDto {
    private String brandName;
    private BigDecimal minimumPrice;
}
