package com.manager.shared.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findPaged(int page, int size);
    int count();
    void save(T entity);
    void update(T entity);
    void delete(ID id);
}
