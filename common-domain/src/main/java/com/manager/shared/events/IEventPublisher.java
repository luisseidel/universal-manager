package com.manager.shared.events;

public interface IEventPublisher {
    void publish(IDomainEvent event);
}
