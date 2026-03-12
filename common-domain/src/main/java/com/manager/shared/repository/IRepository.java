package com.manager.shared.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findPaged(ISpecification<T> spec, int page, int size);
    int count();
    void save(T entity);
    void update(T entity);
    void delete(ID id);
}
