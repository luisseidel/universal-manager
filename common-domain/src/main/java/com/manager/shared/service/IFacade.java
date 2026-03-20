package com.manager.shared.service;

import com.manager.shared.domain.model.dto.PagedResponse;

public interface IFacade<T, R, ID, Q> {
    PagedResponse<R> listPaged(Q criteria);
    void create(T entity);
    void update(ID document, T entity);
    void delete(ID id);
}
