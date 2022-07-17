package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.Brand;
import com.musinsa.report.parksanhee.domain.Item;
import com.musinsa.report.parksanhee.domain.Money;
import com.musinsa.report.parksanhee.exception.NotFoundItemException;
import com.musinsa.report.parksanhee.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item saveItem(Brand brand, BigDecimal price) {
        Item newItem = Item.builder().brand(brand).price(Money.wons(price)).build();
        return itemRepository.save(newItem);
    }

    public void deleteItem(Long itemId){
        Item findItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundItemException("삭제할 상품이 존재하지 않습니다."));
        itemRepository.delete(findItem);
    }
}
