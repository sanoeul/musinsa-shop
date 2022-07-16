package com.musinsa.report.parksanhee.dto;

import com.musinsa.report.parksanhee.domain.Money;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BrandMinimumPriceDto {
    private String brandName;
    private Money price;

    public BrandMinimumPriceDto(String brandName, BigDecimal price) {
        this.brandName = brandName;
        this.price = Money.wons(price);
    }
}
