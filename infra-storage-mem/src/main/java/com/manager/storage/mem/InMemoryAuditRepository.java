package com.manager.storage.mem;

import com.manager.shared.domain.model.dto.PagedResponse;
import com.manager.shared.repository.ISpecification;
import domain.model.AuditLog;
import domain.repository.IAuditRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryAuditRepository implements IAuditRepository {

    private final Map<String, AuditLog> database = new LinkedHashMap<>();

    @Override
    public void save(AuditLog log) {
        database.put(log.getId(), log);
    }

    @Override
    public PagedResponse<AuditLog> findPaged(ISpecification<AuditLog> spec, int page, int pageSize) {
        List<AuditLog> filtered = database.values().stream()
                .filter(spec::isSatisfiedBy)
                .toList();

        long total = filtered.size();

        List<AuditLog> paged = filtered.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();

        return PagedResponse.of(paged, page, pageSize, total);
    }

    @Override
    public long count(ISpecification<AuditLog> spec) {
        return 0;
    }

    @Override
    public Optional<AuditLog> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void update(AuditLog log) {
        database.put(log.getId(), log);
    }

    @Override
    public void delete(String id) {
        database.remove(id);
    }

    @Override
    public List<AuditLog> findByEventType(String type) {
        return database.values().stream()
                .filter(log -> log.getEventType().equalsIgnoreCase(type))
                .toList();
    }
}
