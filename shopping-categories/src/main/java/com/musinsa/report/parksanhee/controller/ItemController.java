package com.musinsa.report.parksanhee.controller;

import com.musinsa.report.parksanhee.dto.ItemRequestDto;
import com.musinsa.report.parksanhee.dto.ItemResponse;
import com.musinsa.report.parksanhee.service.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "상품 생성", notes = "이미 존재하는 브랜드와 카테고리 정보가 필요합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "상품 생성 완료"),
            @ApiResponse(code = 404, message = "카테고리 또는 브랜드가 존재하지 않거나 가격을 음수로 지정할 경우 생성 실패")
    })
    @PostMapping
    @CacheEvict(value = {"brandFilter", "categoryFilter", "categoryBrandFilters"}, allEntries = true)
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequestDto itemRequestDto) {
        ItemResponse itemResponse = itemService.saveItem(itemRequestDto);
        return ResponseEntity.created(URI.create("/items/" + itemResponse.getItemId())).body(itemResponse);
    }

    @ApiOperation(value = "상품 삭제")
    @ApiResponses({
            @ApiResponse(code = 204, message = "상품 정상 삭제"),
            @ApiResponse(code = 404, message = "상품이 존재하지 않아 삭제 실패")
    })
    @DeleteMapping("/{itemId}")
    @CacheEvict(value = {"brandFilter", "categoryFilter", "categoryBrandFilters"}, allEntries = true)
    public ResponseEntity deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "상품 업데이트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "상품 갱신 완료"),
            @ApiResponse(code = 404, message = "카테고리 또는 브랜드가 존재하지 않거나 가격을 음수로 지정할 경우 갱신 실패")
    })
    @PutMapping("/{itemId}")
    @CacheEvict(value = {"brandFilter", "categoryFilter", "categoryBrandFilters"}, allEntries = true)
    public ResponseEntity updateItem(@PathVariable("itemId") Long itemId, @RequestBody ItemRequestDto itemUpdateReqeust) {
        itemService.updateItem(itemId, itemUpdateReqeust);
        return ResponseEntity.ok().build();
    }
}
