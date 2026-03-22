package com.manager.shared.service;

import com.manager.shared.domain.model.dto.PagedResponse;

public interface IFacade<T, U, R, ID, Q> {
    PagedResponse<R> listPaged(Q criteria);
    void create(T entity);
    void update(ID document, U update);
    void delete(ID id);
}
