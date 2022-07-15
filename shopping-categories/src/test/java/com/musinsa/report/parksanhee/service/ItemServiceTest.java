package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.Brand;
import com.musinsa.report.parksanhee.domain.Category;
import com.musinsa.report.parksanhee.domain.Item;
import com.musinsa.report.parksanhee.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ItemServiceTest extends AbstractServiceTest {
    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("한 브랜드에서 모든 카테고리의 상품을 한꺼번에 구매할 경우 최저가 및 브랜드 조회")
    void getMinimumPricesOfBrandItems() {
        // given
        Brand 브랜드1 = 브랜드_생성("브랜드1");
        Item 아이템1 = 아이템_생성(브랜드1, Money.wons(1000L));
        Item 아이템2 = 아이템_생성(브랜드1, Money.wons(99999999L));
        Item 아이템3 = 아이템_생성(브랜드1, Money.wons(1000L));
        Category 상의_카테고리 = 카테고리_생성("상의");
        Category 하의_카테고리 = 카테고리_생성("하의");

        아이템_카테고리_생성(상의_카테고리, 아이템1);
        아이템_카테고리_생성(상의_카테고리, 아이템2);
        아이템_카테고리_생성(하의_카테고리, 아이템3);

        // when
        Money 브랜드_최저가 = itemService.getMinimumPricesOfBrandItems("브랜드1");

        // then
        assertThat(브랜드_최저가).isEqualTo(Money.wons(2000));
    }
}