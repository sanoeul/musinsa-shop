package com.musinsa.report.parksanhee.repository;

import com.musinsa.report.parksanhee.domain.Money;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.musinsa.report.parksanhee.domain.QItem.item;
import static com.musinsa.report.parksanhee.domain.QItemCategory.itemCategory;

@Repository
@RequiredArgsConstructor
public class ItemSearchRepositoryImpl implements ItemSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Money getLowestPricesOfBrandItems(String brandName) {
        List<BigDecimal> prices = jpaQueryFactory
                .select(item.price.amount.min())
                .from(item)
                .innerJoin(itemCategory)
                .on(item.id.eq(itemCategory.id))
                .where(item.brand.name.eq(brandName))
                .groupBy(itemCategory.category)
                .fetch();

        //TODO. QueryDsl에서는 FROM절에 SubQuery를 사용할 수 없어서, 애플리케이션 단에서 총합 계산 구현
        BigDecimal totalPrice = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return Money.wons(totalPrice);
    }
}
