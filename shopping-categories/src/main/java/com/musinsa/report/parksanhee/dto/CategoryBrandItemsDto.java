package com.musinsa.report.parksanhee.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBrandItemsDto {
    private List<CategoryBrandItemDto> itemList;
    private BigDecimal totalPrice;
}
