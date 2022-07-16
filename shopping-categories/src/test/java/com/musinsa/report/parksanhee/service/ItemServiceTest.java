package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.Brand;
import com.musinsa.report.parksanhee.domain.Category;
import com.musinsa.report.parksanhee.domain.Item;
import com.musinsa.report.parksanhee.domain.Money;
import com.musinsa.report.parksanhee.dto.MinAndMaxBrandItemDto;
import com.musinsa.report.parksanhee.exception.NotFoundCategoryException;
import com.musinsa.report.parksanhee.exception.NotFoundItemException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

class ItemServiceTest extends AbstractServiceTest {
    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("한 브랜드에서 모든 카테고리의 상품 최저가 및 브랜드 조회")
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
        Money 브랜드_최저가 = itemService.getTotalMinimumPricesOfBrandItems("브랜드1");

        // then
        assertThat(브랜드_최저가).isEqualTo(Money.wons(2000));
    }

    @Test
    @DisplayName("상품이 없는 브랜드의 모든 카테고리의 상품 최저가 및 브랜드 조회시 예외 발생")
    void getMinimumPricesOfBrandItems2() {
        // given
        Brand 브랜드1 = 브랜드_생성("브랜드1");
        // when
        Throwable thrown = catchThrowable(() -> itemService.getTotalMinimumPricesOfBrandItems("브랜드1"));
        // then
        assertThat(thrown).isInstanceOf(NotFoundItemException.class);
    }

    @Test
    @DisplayName("각 카테고리 이름으로 최소, 최대 가격 조회")
    void getMinimumAndMaximumPriceByCategory() {
        // given
        Brand 브랜드1 = 브랜드_생성("브랜드1");
        Brand 브랜드2 = 브랜드_생성("브랜드2");
        Brand 브랜드3 = 브랜드_생성("브랜드3");
        Item 브랜드1_상의_아이템1 = 아이템_생성(브랜드1, Money.wons(9000L));
        Item 브랜드1_상의_아이템2 = 아이템_생성(브랜드1, Money.wons(5000L));
        Item 브랜드2_상의_아이템1 = 아이템_생성(브랜드2, Money.wons(2000L));
        Item 브랜드3_상의_아이템1 = 아이템_생성(브랜드3, Money.wons(3000L));

        Category 상의_카테고리 = 카테고리_생성("상의");

        아이템_카테고리_생성(상의_카테고리, 브랜드1_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, 브랜드1_상의_아이템2);
        아이템_카테고리_생성(상의_카테고리, 브랜드2_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, 브랜드3_상의_아이템1);

        // when
        MinAndMaxBrandItemDto 카테고리_브랜드_아이템_최소_최대_가격 = itemService.getMinimumAndMaximumPriceByCategory("상의");

        // then
        assertThat(카테고리_브랜드_아이템_최소_최대_가격.getMinimumBrand().getPrice()).isEqualTo(Money.wons(2000));
        assertThat(카테고리_브랜드_아이템_최소_최대_가격.getMaximumBrand().getPrice()).isEqualTo(Money.wons(5000));
    }

    @Test
    @DisplayName("존재하지 않은 카테고리 이름으로 최소, 최대 가격 조회시 예외 발생")
    void getMinimumAndMaximumPriceByNullCategory() {
        // when
        Throwable thrown = catchThrowable(() -> itemService.getMinimumAndMaximumPriceByCategory("카테고리"));
        // then
        assertThat(thrown).isInstanceOf(NotFoundCategoryException.class);
    }

    @Test
    @DisplayName("하나의 아이템을 가지고 있는 카테고리의 이름으로 최소, 최대 가격 조회")
    void getMinimumAndMaximumPriceByCategory2() {
        // given
        Brand 브랜드1 = 브랜드_생성("브랜드1");
        Item 브랜드1_상의_아이템1 = 아이템_생성(브랜드1, Money.wons(9000L));
        Category 상의_카테고리 = 카테고리_생성("상의");
        아이템_카테고리_생성(상의_카테고리, 브랜드1_상의_아이템1);

        // when
        MinAndMaxBrandItemDto 카테고리_브랜드_아이템_최소_최대_가격 = itemService.getMinimumAndMaximumPriceByCategory("상의");

        // then
        assertAll(
                () -> assertThat(카테고리_브랜드_아이템_최소_최대_가격.getMinimumBrand().getPrice()).isEqualTo(Money.wons(9000)),
                () -> assertThat(카테고리_브랜드_아이템_최소_최대_가격.getMaximumBrand()).isNull()
        );
    }

}