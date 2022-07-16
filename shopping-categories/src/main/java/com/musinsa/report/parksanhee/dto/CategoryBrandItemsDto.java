package com.musinsa.report.parksanhee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CategoryBrandItemsDto {
    private List<CategoryBrandItemDto> itemList;
    private BigDecimal totalPrice;
}
