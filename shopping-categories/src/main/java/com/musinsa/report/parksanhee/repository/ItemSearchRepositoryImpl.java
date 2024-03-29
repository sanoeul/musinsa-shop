package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.dto.BrandMinimumPriceDto;
import com.musinsa.report.parksanhee.dto.CategoryBrandItemDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.musinsa.report.parksanhee.domain.QBrand.brand;
import static com.musinsa.report.parksanhee.domain.QCategory.category;
import static com.musinsa.report.parksanhee.domain.QItem.item;
import static com.musinsa.report.parksanhee.domain.QItemCategory.itemCategory;

@Repository
@RequiredArgsConstructor
public class ItemSearchRepositoryImpl implements ItemSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Cacheable(value = "brandFilter", cacheManager = "itemSearchCacheManager")
    public List<BigDecimal> getMinimumPricesOfBrandItems(String brandName) {
        return jpaQueryFactory
                .select(item.price.amount.min())
                .from(item)
                .innerJoin(itemCategory)
                .on(item.id.eq(itemCategory.item.id))
                .where(item.brand.name.eq(brandName))
                .groupBy(itemCategory.category)
                .fetch();
    }

    @Override
    @Cacheable(value = "categoryFilter", cacheManager = "itemSearchCacheManager")
    public List<BrandMinimumPriceDto> getMinimumPricesOfCategoryItems(String categoryName) {
        return jpaQueryFactory
                .select(Projections.constructor(BrandMinimumPriceDto.class, brand.name, item.price.amount.min()))
                .from(item)
                .innerJoin(itemCategory)
                .on(item.id.eq(itemCategory.item.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .innerJoin(category)
                .on(itemCategory.category.id.eq(category.id))
                .where(category.name.eq(categoryName))
                .groupBy(brand.name)
                .orderBy(item.price.amount.min().asc())
                .fetch();
    }

    @Override
    @Cacheable(value = "categoryBrandFilter", cacheManager = "itemSearchCacheManager")
    public CategoryBrandItemDto getAllMinimumPriceByCategoryAndBrand(String categoryName, String brandName) {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryBrandItemDto.class, category.name, brand.name, item.price.amount.min()))
                .from(item)
                .innerJoin(itemCategory)
                .on(item.id.eq(itemCategory.item.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .innerJoin(category)
                .on(itemCategory.category.id.eq(category.id))
                .where(category.name.eq(categoryName).and(brand.name.eq(brandName)))
                .groupBy(category, brand)
                .orderBy(category.name.asc(), item.price.amount.min().asc())
                .fetchOne();
    }
}
