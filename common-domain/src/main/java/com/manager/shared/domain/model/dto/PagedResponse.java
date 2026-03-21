package com.manager.shared.domain.model.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> items,
        int currentPage,
        long totalItems,
        int totalPages,
        boolean hasNext
) {

    public static <T> PagedResponse<T> of(List<T> items, int currentPage, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean hasNext = currentPage < totalPages;
        return new PagedResponse<T>(items, currentPage, totalElements, totalPages, hasNext);
    }

}
