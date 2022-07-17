package com.musinsa.report.parksanhee.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {
    private String categoryName;
    private String brandName;
    private Long itemId;
    private BigDecimal price;
}
