package com.manager.shared.domain.model;

import java.util.Collections;
import java.util.List;

public interface IMapper<T, R, Q, U> {

    R toResponse(T entity);
    T toDomain(Q request);
    default void updateEntity(T entity, U request) {
        throw new UnsupportedOperationException("Update não implementado para esse mapper");
    }
    default List<R> toResponseList(List<T> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .map(this::toResponse)
                .toList();
    }
}
