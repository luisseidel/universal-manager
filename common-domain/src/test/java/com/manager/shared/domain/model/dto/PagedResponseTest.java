package com.manager.shared.domain.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PagedResponseTest {

    @Test
    @DisplayName("Constructor should create paged response, with valid parameters")
    void constructor_shouldCreatePagedResponse_withValidParameters() {
        List<Object> list = List.of(new Object());
        int currentPage = 1;
        int totalItems = 1;
        int totalPages = 1;
        boolean hasNext = false;

        PagedResponse<Object> result = new PagedResponse<>(list, currentPage, totalItems, totalPages, hasNext);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.items().isEmpty());
        Assertions.assertEquals(1, result.currentPage());
        Assertions.assertEquals(1, result.totalItems());
        Assertions.assertEquals(1, result.totalPages());
        Assertions.assertFalse(result.hasNext());
    }

    @Test
    @DisplayName("Of should create paged response, with valid parameters")
    void of_shouldCreatePagedResponse_withValidParameters() {
        List<Object> list = List.of(new Object());
        int currentPage = 1;
        int totalItems = 1;
        int pageSize = 1;

        PagedResponse result = PagedResponse.of(list, currentPage, pageSize, totalItems);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.items().isEmpty());
        Assertions.assertEquals(1, result.currentPage());
        Assertions.assertEquals(1, result.totalItems());
        Assertions.assertEquals(1, result.totalPages());
        Assertions.assertFalse(result.hasNext());
    }

    @Test
    @DisplayName("Of should create paged response with bigger current page")
    void of_shouldCreatePagedResponse_withBiggerCurrentPage() {
        List<Object> list = List.of(new Object());
        int currentPage = 2;
        int totalItems = 5;
        int pageSize = 1;

        PagedResponse result = PagedResponse.of(list, currentPage, pageSize, totalItems);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.items().isEmpty());
        Assertions.assertEquals(2, result.currentPage());
        Assertions.assertEquals(5, result.totalItems());
        Assertions.assertEquals(5, result.totalPages());
        Assertions.assertTrue(result.hasNext());
    }

    @Test
    @DisplayName("Map should map itens from T to R preserving pagination metadata")
    void map_shouldTransformItemsAndPreservePaginationMetadata_withValidList() {
        List<Integer> initialItems = List.of(1, 2, 3);
        PagedResponse<Integer> originalPage = PagedResponse.of(initialItems, 1, 3, 10);

        PagedResponse<String> mappedPage = originalPage.map(number -> "Item " + number);

        Assertions.assertNotNull(mappedPage.items());
        Assertions.assertEquals(3, mappedPage.items().size());
        Assertions.assertEquals(List.of("Item 1", "Item 2", "Item 3"), mappedPage.items());

        Assertions.assertEquals(originalPage.currentPage(), mappedPage.currentPage());
        Assertions.assertEquals(originalPage.totalItems(), mappedPage.totalItems());
        Assertions.assertEquals(originalPage.totalPages(), mappedPage.totalPages());
        Assertions.assertEquals(originalPage.hasNext(), mappedPage.hasNext());
    }

    @Test
    @DisplayName("Map should work correctly with empty List")
    void map_shouldWork_withEmptyList() {
        List<Integer> list = List.of();

        PagedResponse<Integer> empty = PagedResponse.of(list, 1, 10, 0);
        PagedResponse<String> mappedPage = empty.map(Object::toString);

        Assertions.assertTrue(mappedPage.items().isEmpty());
        Assertions.assertEquals(1, mappedPage.currentPage());
        Assertions.assertEquals(0, mappedPage.totalItems());
        Assertions.assertEquals(0, mappedPage.totalPages());
        Assertions.assertFalse(mappedPage.hasNext());
    }

    @Test
    @DisplayName("Map should throw exception when null mapper")
    void map_shouldThrowException_whenMapperIsNull() {
        List<Integer> list = List.of(1, 2);

        PagedResponse<Integer> page = PagedResponse.of(list, 1, 2, 2);

        Assertions.assertThrows(NullPointerException.class, () -> page.map(null));
    }

}
