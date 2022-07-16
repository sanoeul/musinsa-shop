package com.musinsa.report.parksanhee.service;

import com.musinsa.report.parksanhee.domain.*;
import com.musinsa.report.parksanhee.repository.BrandRepository;
import com.musinsa.report.parksanhee.repository.CategoryRepository;
import com.musinsa.report.parksanhee.repository.ItemCategoryRepository;
import com.musinsa.report.parksanhee.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractServiceTest {
    @Autowired
    protected BrandRepository brandRepository;
    @Autowired
    protected ItemRepository itemRepository;
    @Autowired
    protected ItemCategoryRepository itemCategoryRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    @BeforeEach
    void tearDown() {
        itemCategoryRepository.deleteAll();
        itemRepository.deleteAll();
        brandRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    protected Category 카테고리_생성(String 이름) {
        Category newCategory = Category.builder().name(이름).build();
        return categoryRepository.save(newCategory);
    }

    protected Brand 브랜드_생성(String 이름) {
        Brand newBrand = Brand.builder().name(이름).build();
        return brandRepository.save(newBrand);
    }

    protected Item 아이템_생성(Brand 브랜드, Money 아이템_가격) {
        Item newItem = Item.builder().brand(브랜드).price(아이템_가격).build();
        return itemRepository.save(newItem);
    }

    protected Item 브랜드_아이템_생성(String 브랜드_이름, Money 아이템_가격) {
        Brand newBrand = Brand.builder().name(브랜드_이름).build();
        brandRepository.save(newBrand);
        Item newItem = Item.builder().brand(newBrand).price(아이템_가격).build();
        return itemRepository.save(newItem);
    }

    protected ItemCategory 아이템_카테고리_생성(Category 카테고리, Item 아이템) {
        ItemCategory newItemCategory = ItemCategory.builder().category(카테고리).item(아이템).build();
        return itemCategoryRepository.save(newItemCategory);
    }
}
