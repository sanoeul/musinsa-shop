package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.*;
import com.musinsa.report.parksanhee.dto.ItemRequestDto;
import com.musinsa.report.parksanhee.dto.ItemResponse;
import com.musinsa.report.parksanhee.exception.NotFoundBrandException;
import com.musinsa.report.parksanhee.exception.NotFoundItemException;
import com.musinsa.report.parksanhee.repository.BrandRepository;
import com.musinsa.report.parksanhee.repository.CategoryRepository;
import com.musinsa.report.parksanhee.repository.ItemCategoryRepository;
import com.musinsa.report.parksanhee.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemResponse saveItem(ItemRequestDto itemRequestDto) {
        Brand findBrand = findBrandByName(itemRequestDto.getBrandName());
        Category findCategory = findCategoryByName(itemRequestDto.getCategoryName());
        Item newItem = Item.builder().brand(findBrand).price(Money.wons(itemRequestDto.getPrice())).build();
        newItem.addBrand(findBrand);

        itemRepository.save(newItem);
        ItemCategory newItemCategory = ItemCategory.builder().item(newItem).category(findCategory).build();
        itemCategoryRepository.save(newItemCategory);

        return ItemResponse.builder()
                .categoryName(itemRequestDto.getCategoryName())
                .brandName(itemRequestDto.getBrandName())
                .itemId(newItem.getId())
                .price(newItem.getPrice().getAmount())
                .build();
    }

    public void deleteItem(Long itemId) {
        Item findItem = findItemById(itemId);
        ItemCategory findItemCategory = findItemCategoryByItemId(itemId);
        itemCategoryRepository.delete(findItemCategory);
        itemRepository.delete(findItem);
    }

    public void updateItem(Long itemId, ItemRequestDto itemUpdateReqeust) {
        Item findItem = findItemById(itemId);
        Brand findBrand = findBrandByName(itemUpdateReqeust.getBrandName());
        Category findCategory = findCategoryByName(itemUpdateReqeust.getCategoryName());
        ItemCategory findItemCategory = findItemCategoryByItemId(itemId);
        findItem.update(findBrand, itemUpdateReqeust.getPrice());
        findItemCategory.updateCategory(findCategory);
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundItemException("삭제할 상품이 존재하지 않습니다."));
    }

    private Brand findBrandByName(String name) {
        return brandRepository.findByName(name).orElseThrow(() -> new NotFoundBrandException(name + "에 해당하는 브랜드를 찾을 수 없습니다."));
    }

    private Category findCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new NotFoundBrandException(name + "에 해당하는 카테고리를 찾을 수 없습니다."));
    }

    private ItemCategory findItemCategoryByItemId(Long itemId) {
        return itemCategoryRepository.findByItemId(itemId).orElseThrow(() -> new NotFoundBrandException(itemId + " 아이디에 해당하는 카테고리를 찾을 수 없습니다."));
    }

}
