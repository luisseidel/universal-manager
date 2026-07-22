package com.manager.shared.events;

import java.time.Instant;

public interface IDomainEvent {
    Instant occurredOn();
    String eventType();
}
