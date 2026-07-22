package com.manager.infrastructure.gateways;

import com.manager.shared.events.IDomainEvent;
import com.manager.shared.events.IEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleEventBus implements IEventPublisher {

    private final List<Consumer<IDomainEvent>> subscribers = new ArrayList<>();

    public void subscribe(Consumer<IDomainEvent> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void publish(IDomainEvent event) {
        subscribers.forEach(subscriber -> subscriber.accept(event));
    }
}
