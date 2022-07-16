package com.musinsa.report.parksanhee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CategoryBrandItemsDto {
    private List<CategoryBrandItemDto> itemList;
}
