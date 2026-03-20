package com.manager.shared.domain.model.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> items,
        int currentPage,
        long totalItems,
        int totalPages,
        boolean hasNext
) {

    public static <T> PagedResponse<T> of(List<T> items, int page, int size, long total) {
        int totalPages = (int) Math.ceil((double) total / size);
        boolean hasNext = page < totalPages;
        return new PagedResponse<T>(items, page, total, totalPages, hasNext);
    }

}
