package com.manager.health.domain.events;

import com.manager.shared.events.IDomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PatientCreatedEvent(
        UUID patientId,
        String patientName,
        Instant occurredOn
) implements IDomainEvent {
    public PatientCreatedEvent(UUID id, String name) {
        this(id, name, Instant.now());
    }
    @Override public String eventType() { return "PATIENT_CREATED"; }
}
