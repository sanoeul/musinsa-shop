package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.domain.Money;

public interface ItemSearchRepository {
    Money getLowestPricesOfBrandItems(String brandName);
}
