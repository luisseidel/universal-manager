package com.manager.shared.service;

import com.manager.shared.domain.model.dto.PagedResponse;

public interface IFacade<T, R, ID> {

    PagedResponse<R> listPaged(String query, int page, int size);
    void create(T entity);
    void update(ID document, T entity);
    void delete(ID id);

}
