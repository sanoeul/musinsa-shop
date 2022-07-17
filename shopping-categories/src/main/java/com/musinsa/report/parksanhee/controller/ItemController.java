package com.musinsa.report.parksanhee.controller;

import com.musinsa.report.parksanhee.dto.ItemRequestDto;
import com.musinsa.report.parksanhee.dto.ItemResponse;
import com.musinsa.report.parksanhee.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @CacheEvict(value = {"brandFilter", "categoryFilter", "categoryBrandFilters"}, allEntries = true)
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequestDto itemRequestDto) {
        ItemResponse itemResponse = itemService.saveItem(itemRequestDto);
        return ResponseEntity.created(URI.create("/items/" + itemResponse.getItemId())).body(itemResponse);
    }

    @DeleteMapping("/{itemId}")
    @CacheEvict(value = {"brandFilter", "categoryFilter", "categoryBrandFilters"}, allEntries = true)
    public ResponseEntity deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}")
    @CacheEvict(value = {"brandFilter", "categoryFilter", "categoryBrandFilters"}, allEntries = true)
    public ResponseEntity updateItem(@PathVariable("itemId") Long itemId, @RequestBody ItemRequestDto itemUpdateReqeust) {
        itemService.updateItem(itemId, itemUpdateReqeust);
        return ResponseEntity.ok().build();
    }
}
