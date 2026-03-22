package com.manager.shared.repository;

import com.manager.shared.domain.model.dto.PagedResponse;

import java.util.Optional;

public interface IRepository<T, ID> {
    Optional<T> findById(ID id);
    PagedResponse<T> findPaged(ISpecification<T> spec, int page, int size);
    long count(ISpecification<T> spec);
    void save(T entity);
    void update(T entity);
    void delete(ID id);
}
