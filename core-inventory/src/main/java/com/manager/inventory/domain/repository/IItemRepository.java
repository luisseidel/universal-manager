package com.manager.inventory.domain.repository;

import com.manager.inventory.domain.model.Item;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface IItemRepository {
    void salvar(Item item);
    Optional<Item> buscarPorId(UUID id);
    List<Item> listarTodos();
}