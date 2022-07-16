package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.Brand;
import com.musinsa.report.parksanhee.domain.Category;
import com.musinsa.report.parksanhee.domain.Item;
import com.musinsa.report.parksanhee.domain.Money;
import com.musinsa.report.parksanhee.dto.CategoryBrandDto;
import com.musinsa.report.parksanhee.dto.CategoryBrandItemsDto;
import com.musinsa.report.parksanhee.dto.MinAndMaxBrandItemDto;
import com.musinsa.report.parksanhee.exception.NotFoundCategoryException;
import com.musinsa.report.parksanhee.exception.NotFoundItemException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("모든 카테고리의 상품을 브랜드 별로 선택할 때 최저가 조회")
    void getAllMinimumPricesByCategoryAndBrand() {
        // given
        Brand A브랜드 = 브랜드_생성("A");
        Item A브랜드_상의_아이템1 = 아이템_생성(A브랜드, Money.wons(1000L));
        Item A브랜드_상의_아이템2 = 아이템_생성(A브랜드, Money.wons(2000L));
        Item A브랜드_아우터_아이템1 = 아이템_생성(A브랜드, Money.wons(1800L));
        Item A브랜드_아우터_아이템2 = 아이템_생성(A브랜드, Money.wons(2100L));

        Brand B브랜드 = 브랜드_생성("B");
        Item B브랜드_상의_아이템1 = 아이템_생성(B브랜드, Money.wons(1500L));
        Item B브랜드_상의_아이템2 = 아이템_생성(B브랜드, Money.wons(2500L));
        Item B브랜드_아우터_아이템1 = 아이템_생성(B브랜드, Money.wons(1100L));
        Item B브랜드_아우터_아이템2 = 아이템_생성(B브랜드, Money.wons(3500L));

        Brand C브랜드 = 브랜드_생성("C");
        Item C브랜드_상의_아이템1 = 아이템_생성(C브랜드, Money.wons(1900L));
        Item C브랜드_상의_아이템2 = 아이템_생성(C브랜드, Money.wons(2900L));
        Item C브랜드_아우터_아이템1 = 아이템_생성(C브랜드, Money.wons(1950L));
        Item C브랜드_아우터_아이템2 = 아이템_생성(C브랜드, Money.wons(900L));

        Category 상의_카테고리 = 카테고리_생성("상의");
        Category 아우터_카테고리 = 카테고리_생성("아우터");

        아이템_카테고리_생성(상의_카테고리, A브랜드_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, A브랜드_상의_아이템2);
        아이템_카테고리_생성(상의_카테고리, B브랜드_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, B브랜드_상의_아이템2);
        아이템_카테고리_생성(상의_카테고리, C브랜드_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, C브랜드_상의_아이템2);

        아이템_카테고리_생성(아우터_카테고리, A브랜드_아우터_아이템1);
        아이템_카테고리_생성(아우터_카테고리, A브랜드_아우터_아이템2);
        아이템_카테고리_생성(아우터_카테고리, B브랜드_아우터_아이템1);
        아이템_카테고리_생성(아우터_카테고리, B브랜드_아우터_아이템2);
        아이템_카테고리_생성(아우터_카테고리, C브랜드_아우터_아이템1);
        아이템_카테고리_생성(아우터_카테고리, C브랜드_아우터_아이템2);

        List<CategoryBrandDto> 카테고리_브랜드별_검색_리스트 = new ArrayList<>();
        CategoryBrandDto A브랜드_상의 = CategoryBrandDto.builder().categoryName("상의").brandName("A").build();
        CategoryBrandDto B브랜드_아우터 = CategoryBrandDto.builder().categoryName("아우터").brandName("C").build();
        카테고리_브랜드별_검색_리스트.add(A브랜드_상의);
        카테고리_브랜드별_검색_리스트.add(B브랜드_아우터);

        // when
        CategoryBrandItemsDto categoryBrandItemDtos = itemService.getAllMinimumPricesByCategoryAndBrand(카테고리_브랜드별_검색_리스트);

        // then
        assertAll(
                () -> assertThat(categoryBrandItemDtos.getItemList().size()).isEqualTo(2),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(0).getCategoryName()).isEqualTo("상의"),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(0).getBrandName()).isEqualTo("A"),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(0).getPrice()).isEqualTo(Money.wons(1000L)),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(1).getCategoryName()).isEqualTo("아우터"),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(1).getBrandName()).isEqualTo("C"),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(1).getPrice()).isEqualTo(Money.wons(900L))
        );
    }

    @Test
    @DisplayName("모든 카테고리의 모든 상품을 존재하지 않는 브랜드로 조회시 예외 발생")
    void getAllMinimumPricesByCategoryAndBrand2() {
        // given
        List<CategoryBrandDto> 카테고리_브랜드별_검색_리스트 = new ArrayList<>();
        CategoryBrandDto 없는_카테고리_브랜드 = CategoryBrandDto.builder().categoryName("상의").brandName("A").build();
        카테고리_브랜드별_검색_리스트.add(없는_카테고리_브랜드);
        // when
        Throwable thrown = catchThrowable(() -> itemService.getAllMinimumPricesByCategoryAndBrand(카테고리_브랜드별_검색_리스트));
        // then
        assertThat(thrown).isInstanceOf(NotFoundItemException.class);
    }

    @Test
    @DisplayName("모든 카테고리의 상품을 일부는 존재하지 않는 카테고리, 브랜드로 선택할 때 존재하는 브랜드의 상품만 조회")
    void getAllMinimumPricesByCategoryAndBrand3() {
        // given
        Brand A브랜드 = 브랜드_생성("A");
        Item A브랜드_상의_아이템1 = 아이템_생성(A브랜드, Money.wons(1000L));
        Item A브랜드_상의_아이템2 = 아이템_생성(A브랜드, Money.wons(2000L));
        Item A브랜드_아우터_아이템1 = 아이템_생성(A브랜드, Money.wons(1800L));
        Item A브랜드_아우터_아이템2 = 아이템_생성(A브랜드, Money.wons(2100L));

        Brand B브랜드 = 브랜드_생성("B");
        Item B브랜드_상의_아이템1 = 아이템_생성(B브랜드, Money.wons(1500L));
        Item B브랜드_상의_아이템2 = 아이템_생성(B브랜드, Money.wons(2500L));
        Item B브랜드_아우터_아이템1 = 아이템_생성(B브랜드, Money.wons(1100L));
        Item B브랜드_아우터_아이템2 = 아이템_생성(B브랜드, Money.wons(3500L));

        Brand C브랜드 = 브랜드_생성("C");
        Item C브랜드_상의_아이템1 = 아이템_생성(C브랜드, Money.wons(1900L));
        Item C브랜드_상의_아이템2 = 아이템_생성(C브랜드, Money.wons(2900L));
        Item C브랜드_아우터_아이템1 = 아이템_생성(C브랜드, Money.wons(1950L));
        Item C브랜드_아우터_아이템2 = 아이템_생성(C브랜드, Money.wons(900L));

        Category 상의_카테고리 = 카테고리_생성("상의");
        Category 아우터_카테고리 = 카테고리_생성("아우터");

        아이템_카테고리_생성(상의_카테고리, A브랜드_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, A브랜드_상의_아이템2);
        아이템_카테고리_생성(상의_카테고리, B브랜드_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, B브랜드_상의_아이템2);
        아이템_카테고리_생성(상의_카테고리, C브랜드_상의_아이템1);
        아이템_카테고리_생성(상의_카테고리, C브랜드_상의_아이템2);

        아이템_카테고리_생성(아우터_카테고리, A브랜드_아우터_아이템1);
        아이템_카테고리_생성(아우터_카테고리, A브랜드_아우터_아이템2);
        아이템_카테고리_생성(아우터_카테고리, B브랜드_아우터_아이템1);
        아이템_카테고리_생성(아우터_카테고리, B브랜드_아우터_아이템2);
        아이템_카테고리_생성(아우터_카테고리, C브랜드_아우터_아이템1);
        아이템_카테고리_생성(아우터_카테고리, C브랜드_아우터_아이템2);

        List<CategoryBrandDto> 카테고리_브랜드별_검색_리스트 = new ArrayList<>();
        CategoryBrandDto A브랜드_상의 = CategoryBrandDto.builder().categoryName("상의").brandName("A").build();
        CategoryBrandDto 존재하지_않는_브랜드_카테고리 = CategoryBrandDto.builder().categoryName("존재하지 않는 카테고리").brandName("존재하지 않는 브랜드").build();
        카테고리_브랜드별_검색_리스트.add(A브랜드_상의);
        카테고리_브랜드별_검색_리스트.add(존재하지_않는_브랜드_카테고리);

        // when
        CategoryBrandItemsDto categoryBrandItemDtos = itemService.getAllMinimumPricesByCategoryAndBrand(카테고리_브랜드별_검색_리스트);

        // then
        assertAll(
                () -> assertThat(categoryBrandItemDtos.getItemList().size()).isEqualTo(1),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(0).getCategoryName()).isEqualTo("상의"),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(0).getBrandName()).isEqualTo("A"),
                () -> assertThat(categoryBrandItemDtos.getItemList().get(0).getPrice()).isEqualTo(Money.wons(1000L))
        );
    }
}